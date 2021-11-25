package xyz.itbs.recipes.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import xyz.itbs.recipes.commands.RecipeCommand;
import xyz.itbs.recipes.domain.Recipe;
import xyz.itbs.recipes.services.RecipeService;

import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecipeControllerTest {

    RecipeController recipeController;
    MockMvc mockMvc;
    Long id = 1L;

    @Mock
    RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeController = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

    }

    @Test
    void getRecipeById() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(id);
        when(recipeService.getRecipeById(anyLong())).thenReturn(recipe);
        mockMvc.perform(get("/recipe/" + id + "/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attribute("recipe",instanceOf(Recipe.class)));
        verify(recipeService,times(1)).getRecipeById(anyLong());
    }

    @Test
    void getNewRecipeForm() throws Exception {
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attribute("recipe",instanceOf(RecipeCommand.class)));

    }

    @Test
    void postNewRecipeForm() throws Exception {
        RecipeCommand recipeCommand = RecipeCommand.builder().id(id).build();
        when(recipeService.saveRecipeCommand(any(RecipeCommand.class)))
                .thenReturn(recipeCommand);
        mockMvc.perform(post("/recipe")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id","")
                        .param("description", "desc")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/recipe/" + recipeCommand.getId() + "/show"))
                .andExpect(view().name("redirect:/recipe/" + recipeCommand.getId() + "/show"));
        verify(recipeService,times(1)).saveRecipeCommand(any(RecipeCommand.class));
    }

    @Test
    void getUpdateForm() throws Exception{
        RecipeCommand recipeCommand = RecipeCommand.builder().id(id).build();
        when(recipeService.getRecipeCommandById(anyLong()))
                .thenReturn(recipeCommand);
        mockMvc.perform(get("/recipe/" + id + "/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attribute("recipe",instanceOf(RecipeCommand.class)));
        verify(recipeService,times(1)).getRecipeCommandById(anyLong());
    }

    @Test
    void deleteRecipeById() throws Exception {
        mockMvc.perform(get("/recipe/" + id + "/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(view().name("redirect:/"));
        verify(recipeService,times(1)).deleteRecipeById(id);
    }

}