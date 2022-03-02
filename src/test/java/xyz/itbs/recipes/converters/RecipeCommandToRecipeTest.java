package xyz.itbs.recipes.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import xyz.itbs.recipes.commands.CategoryCommand;
import xyz.itbs.recipes.commands.IngredientCommand;
import xyz.itbs.recipes.commands.NotesCommand;
import xyz.itbs.recipes.commands.RecipeCommand;
import xyz.itbs.recipes.domain.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RecipeCommandToRecipeTest {

    Recipe recipe;
    RecipeCommand recipeCommand;
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    CategoryCommandToCategory categoryCommandToCategory;
    @Mock
    IngredientCommandToIngredient ingredientCommandToIngredient;
    @Mock
    NotesCommandToNotes notesCommandToNotes;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Set<CategoryCommand> categoryCommandsSet = new HashSet<>();
        Set<IngredientCommand> ingredientCommandsSet = new HashSet<>();
        categoryCommandsSet.add(CategoryCommand.builder().id("1").build());
        categoryCommandsSet.add(CategoryCommand.builder().id("2").build());
        ingredientCommandsSet.add(IngredientCommand.builder().id("1").build());
        ingredientCommandsSet.add(IngredientCommand.builder().id("2").build());
        recipeCommandToRecipe = new RecipeCommandToRecipe(categoryCommandToCategory,
                ingredientCommandToIngredient,notesCommandToNotes);
        recipeCommand = RecipeCommand.builder()
                .id("1")
                .description("test")
                .cookTime(1)
                .difficulty(Difficulty.EASY)
                .categories(categoryCommandsSet)
                .ingredients(ingredientCommandsSet)
                .notes(NotesCommand.builder().id("1").build())
                .directions("test")
                .prepTime(1)
                .servings(1)
                .source("test")
                .url("test")
                .build();

    }

    @Test
    void convertNull() {
        assertNull(recipeCommandToRecipe.convert(null));
    }

    @Test
    void convertEmpty() {
        assertNotNull(recipeCommandToRecipe.convert(new RecipeCommand()));
    }

    @Test
    void convert() {
        when(categoryCommandToCategory.convert(any(CategoryCommand.class)))
                .thenReturn(Category.builder().id("1").build())
                .thenReturn(Category.builder().id("2").build());
        when(ingredientCommandToIngredient.convert(any(IngredientCommand.class)))
                .thenReturn(Ingredient.builder().id("1").build())
                .thenReturn(Ingredient.builder().id("2").build());
        when(notesCommandToNotes.convert(any(NotesCommand.class)))
                .thenReturn(Notes.builder().id("1").build());
        recipe = recipeCommandToRecipe.convert(recipeCommand);
        assertNotNull(recipe);
        assertEquals(recipeCommand.getId(),recipe.getId());
        assertEquals(recipeCommand.getCategories().size(),recipe.getCategories().size());
        assertEquals(recipeCommand.getDescription(),recipe.getDescription());
        assertEquals(recipeCommand.getCookTime(),recipe.getCookTime());
        assertEquals(recipeCommand.getDifficulty(),recipe.getDifficulty());
        assertEquals(recipeCommand.getDirections(),recipe.getDirections());
        assertEquals(recipeCommand.getIngredients().size(),recipe.getIngredients().size());
        assertEquals(recipeCommand.getNotes().getId(),recipe.getNotes().getId());
        assertEquals(recipeCommand.getSource(),recipe.getSource());
        assertEquals(recipeCommand.getUrl(),recipe.getUrl());
        assertEquals(recipeCommand.getPrepTime(),recipe.getPrepTime());
        assertEquals(recipeCommand.getServings(),recipe.getServings());
        verify(categoryCommandToCategory,times(recipeCommand.getCategories().size()))
                .convert(any(CategoryCommand.class));
        verify(ingredientCommandToIngredient,times(recipeCommand.getIngredients().size()))
                .convert(any(IngredientCommand.class));
        verify(notesCommandToNotes,times(1)).convert(any(NotesCommand.class));
    }
}