package xyz.itbs.recipes.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import xyz.itbs.recipes.commands.RecipeCommand;
import xyz.itbs.recipes.services.RecipeService;

import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class IngredientControllerTest {

    IngredientController ingredientController;
    @Mock
    RecipeService recipeService;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredientController = new IngredientController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    void getIngredientList() throws Exception {
        when(recipeService.getRecipeCommandById(anyLong()))
                .thenReturn(RecipeCommand.builder().id(1L).build());
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
                .thenReturn(RecipeCommand.builder().id(1L).build());
        when(recipeService.getRecipeCommandById(anyLong()))
                .thenReturn(RecipeCommand.builder().id(1L).build());
        mockMvc.perform(get("/recipe/null/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attribute("recipe",instanceOf(RecipeCommand.class)));
        verify(recipeService,times(1)).getRecipeCommandById(anyLong());
        verify(recipeService,times(1)).saveRecipeCommand(any(RecipeCommand.class));
    }
}