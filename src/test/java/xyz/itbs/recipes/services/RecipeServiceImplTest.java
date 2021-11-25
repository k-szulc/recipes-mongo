package xyz.itbs.recipes.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import xyz.itbs.recipes.commands.RecipeCommand;
import xyz.itbs.recipes.converters.RecipeCommandToRecipe;
import xyz.itbs.recipes.converters.RecipeToRecipeCommand;
import xyz.itbs.recipes.domain.Recipe;
import xyz.itbs.recipes.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository,recipeToRecipeCommand,recipeCommandToRecipe);
    }

    @Test
    void getAllRecipes() {
        Recipe recipe = new Recipe();
        HashSet<Recipe> recipeHashSet = new HashSet<>();
        recipeHashSet.add(recipe);
        when(recipeRepository.findAll()).thenReturn(recipeHashSet);

        Set<Recipe> recipeSet = recipeService.getAllRecipes();
        assertEquals(recipeSet.size(),1);
        verify(recipeRepository,times(1)).findAll();
    }

    @Test
    void getRecipeById() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        Recipe savedRecipe = recipeService.getRecipeById(1L);
        assertNotNull(savedRecipe);
        assertEquals(recipe.getId(),savedRecipe.getId());
        verify(recipeRepository,times(1)).findById(anyLong());

    }

    @Test
    void saveRecipeCommand() {
        when(recipeToRecipeCommand.convert(any(Recipe.class)))
                .thenReturn(RecipeCommand.builder().build());
        when(recipeCommandToRecipe.convert(any(RecipeCommand.class)))
                .thenReturn(Recipe.builder().build());
        when(recipeRepository.save(any(Recipe.class)))
                .thenReturn(Recipe.builder().build());
        RecipeCommand savedRecipe = recipeService.saveRecipeCommand(RecipeCommand.builder().build());
        verify(recipeRepository,times(1)).save(any(Recipe.class));
        verify(recipeCommandToRecipe,times(1)).convert(any(RecipeCommand.class));
        verify(recipeToRecipeCommand,times(1)).convert(any(Recipe.class));
    }

    @Test
    void getRecipeCommandById() {
        RecipeCommand recipe = new RecipeCommand();
        recipe.setId(1L);
        when(recipeToRecipeCommand.convert(any(Recipe.class))).thenReturn(recipe);
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(Recipe.builder().id(1L).build()));

        RecipeCommand savedRecipe = recipeService.getRecipeCommandById(1L);
        assertNotNull(savedRecipe);
        assertEquals(recipe.getId(),savedRecipe.getId());
        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeToRecipeCommand,times(1)).convert(any(Recipe.class));

    }
}