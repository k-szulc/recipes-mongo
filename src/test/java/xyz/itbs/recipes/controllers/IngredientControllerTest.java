package xyz.itbs.recipes.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import xyz.itbs.recipes.commands.IngredientCommand;
import xyz.itbs.recipes.commands.RecipeCommand;
import xyz.itbs.recipes.exceptions.NotFoundException;
import xyz.itbs.recipes.services.IngredientService;
import xyz.itbs.recipes.services.RecipeService;
import xyz.itbs.recipes.services.UnitOfMeasureService;

import java.util.HashSet;

import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class IngredientControllerTest {

    IngredientController ingredientController;
    @Mock
    RecipeService recipeService;
    @Mock
    IngredientService ingredientService;
    @Mock
    UnitOfMeasureService unitOfMeasureService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredientController = new IngredientController(recipeService,ingredientService,unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController)
                .setControllerAdvice(new ExceptionHandlerController())
                .build();
    }

    @Test
    void getIngredientList() throws Exception {
        when(recipeService.getRecipeCommandById(anyLong()))
                .thenReturn(RecipeCommand.builder().id("1").build());
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attribute("recipe",instanceOf(RecipeCommand.class)));
        verify(recipeService,times(1)).getRecipeCommandById(anyLong());
    }

    @Test
    void getIngredientListNullRecipeId() throws Exception {
        when(recipeService.saveRecipeCommand(any(RecipeCommand.class)))
                .thenReturn(RecipeCommand.builder().id("1").build());
        when(recipeService.getRecipeCommandById(anyLong()))
                .thenReturn(RecipeCommand.builder().id("1").build());
        mockMvc.perform(get("/recipe/null/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attribute("recipe",instanceOf(RecipeCommand.class)));
        verify(recipeService,times(1)).saveRecipeCommand(any(RecipeCommand.class));
        verify(recipeService,times(1)).getRecipeCommandById(anyLong());
    }

    @Test
    void getIngredientByIdAndRecipeId() throws Exception {
        when(ingredientService.findByIdAndRecipeId(anyString(),anyString()))
                .thenReturn(IngredientCommand.builder().id("1").recipeId("1").build());
        mockMvc.perform(get("/recipe/1/ingredients/1/view"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/view"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attribute("ingredient",instanceOf(IngredientCommand.class)));
        verify(ingredientService,times(1)).findByIdAndRecipeId(anyString(),anyString());
    }

    @Test
    void newIngredientForm() throws Exception {
        when(recipeService.getRecipeCommandById(anyLong()))
                .thenReturn(RecipeCommand.builder().id("2").build());
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());
        mockMvc.perform(get("/recipe/2/ingredients/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

        verify(recipeService, times(1)).getRecipeCommandById(anyLong());
    }

    @Test
    void updateRecipeIngredient() throws Exception {
        when(ingredientService.findByIdAndRecipeId(anyString(),anyString()))
                .thenReturn(IngredientCommand.builder().id("2").recipeId("1").build());
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());
        mockMvc.perform(get("/recipe/1/ingredients/2/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    void saveOrUpdate() throws Exception {
        when(ingredientService.saveIngredientCommand(any()))
                .thenReturn(IngredientCommand.builder().id("5").recipeId("7").build());

        mockMvc.perform(post("/recipe/7/ingredients")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "")
                        .param("description","whatever"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/7/ingredients/5/view"));
    }

    @Test
    void deleteIngredient() throws Exception {
        mockMvc.perform(get("/recipe/1/ingredients/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/recipe/1/ingredients"))
                .andExpect(view().name("redirect:/recipe/1/ingredients"));
        verify(ingredientService,times(1)).deleteIngredientById(anyString(),anyString());
    }

    @Test
    public void getIngredientByIdAndRecipeIdRecipeNotFound() throws Exception {

        when(ingredientService.findByIdAndRecipeId(anyString(),anyString())).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/recipe/123/ingredients/1/view"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));

    }

    @Test
    public void getIngredientByIdAndRecipeIdIngredientNotFound() throws Exception {

        when(ingredientService.findByIdAndRecipeId(anyString(),anyString())).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/recipe/1/ingredients/123/view"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));

    }


    @Test
    public void getIngredientByIdAndRecipeIdNumberFormatException() throws Exception {

        when(ingredientService.findByIdAndRecipeId(anyString(),anyString())).thenThrow(NumberFormatException.class);
        mockMvc.perform(get("/recipe/1/ingredients/asd/view"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
        mockMvc.perform(get("/recipe/asd/ingredients/1/view"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));

    }
}