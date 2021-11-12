package xyz.itbs.recipes.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    Notes notes;
    Ingredient ingredient;
    Recipe recipe;

    @BeforeEach
    void setUp() {
        notes = new Notes();
        ingredient = new Ingredient();
        recipe = new Recipe();
    }

    @Test
    void setNotes() {
        recipe.setNotes(notes);
        assertEquals(recipe.getNotes(), notes);
        assertEquals(notes.getRecipe(), recipe);
    }

    @Test
    void addIngredient() {

        recipe.addIngredient(ingredient);
        assertEquals(ingredient.getRecipe(), recipe);
        assertTrue(recipe.getIngredients().size()>0);
    }
}