package xyz.itbs.recipes.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

class RecipeToRecipeCommandTest {

    Recipe recipe;
    RecipeCommand recipeCommand;
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    CategoryToCategoryCommand categoryToCategoryCommand;
    @Mock
    IngredientToIngredientCommand ingredientToIngredientCommand;
    @Mock
    NotesToNotesCommand notesToNotesCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Set<Category> categorySet = new HashSet<>();
        Set<Ingredient> ingredientSet = new HashSet<>();
        categorySet.add(Category.builder().id("1").build());
        categorySet.add(Category.builder().id("2").build());
        ingredientSet.add(Ingredient.builder().id("1").build());
        ingredientSet.add(Ingredient.builder().id("2").build());
        recipeToRecipeCommand = new RecipeToRecipeCommand(categoryToCategoryCommand,
                ingredientToIngredientCommand, notesToNotesCommand);
        recipe = Recipe.builder()
                .id("1")
                .description("test")
                .cookTime(1)
                .difficulty(Difficulty.EASY)
                .categories(categorySet)
                .ingredients(ingredientSet)
                .notes(Notes.builder().id("1").build())
                .directions("test")
                .prepTime(1)
                .servings(1)
                .source("test")
                .url("test")
                .build();

    }

    @Test
    void convertNull() {
        assertNull(recipeToRecipeCommand.convert(null));
    }

    @Test
    void convertEmpty() {
        assertNotNull(recipeToRecipeCommand.convert(new Recipe()));
    }

    @Test
    void convert() {
        when(categoryToCategoryCommand.convert(any(Category.class)))
                .thenReturn(CategoryCommand.builder().id("1").build())
                .thenReturn(CategoryCommand.builder().id("2").build());
        when(ingredientToIngredientCommand.convert(any(Ingredient.class)))
                .thenReturn(IngredientCommand.builder().id("1").build())
                .thenReturn(IngredientCommand.builder().id("2").build());
        when(notesToNotesCommand.convert(any(Notes.class)))
                .thenReturn(NotesCommand.builder().id("1").build());
        recipeCommand = recipeToRecipeCommand.convert(recipe);
        assertNotNull(recipeCommand);
        assertEquals(recipe.getId(),recipe.getId());
        assertEquals(recipe.getCategories().size(),recipeCommand.getCategories().size());
        assertEquals(recipe.getDescription(),recipeCommand.getDescription());
        assertEquals(recipe.getCookTime(),recipeCommand.getCookTime());
        assertEquals(recipe.getDifficulty(),recipeCommand.getDifficulty());
        assertEquals(recipe.getDirections(),recipeCommand.getDirections());
        assertEquals(recipe.getIngredients().size(),recipeCommand.getIngredients().size());
        assertEquals(recipe.getNotes().getId(),recipeCommand.getNotes().getId());
        assertEquals(recipe.getSource(),recipeCommand.getSource());
        assertEquals(recipe.getUrl(),recipeCommand.getUrl());
        assertEquals(recipe.getPrepTime(),recipeCommand.getPrepTime());
        assertEquals(recipe.getServings(),recipeCommand.getServings());
        verify(categoryToCategoryCommand,times(recipe.getCategories().size()))
                .convert(any(Category.class));
        verify(ingredientToIngredientCommand,times(recipe.getIngredients().size()))
                .convert(any(Ingredient.class));
        verify(notesToNotesCommand,times(1)).convert(any(Notes.class));
    }
}