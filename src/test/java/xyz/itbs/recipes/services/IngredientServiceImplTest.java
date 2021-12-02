package xyz.itbs.recipes.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import xyz.itbs.recipes.commands.IngredientCommand;
import xyz.itbs.recipes.converters.IngredientToIngredientCommand;
import xyz.itbs.recipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import xyz.itbs.recipes.domain.Ingredient;
import xyz.itbs.recipes.domain.Recipe;
import xyz.itbs.recipes.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    IngredientService ingredientService;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        IngredientToIngredientCommand ingredientToIngredientCommand =
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand);
    }

    @Test
    void findByIdAndRecipeId() {
        Ingredient ingr = Ingredient.builder().id(1L).build();
        Ingredient ingr2 = Ingredient.builder().id(2L).build();
        Recipe recipe = Recipe.builder().id(1L).ingredients(new HashSet<>()).build();
        recipe.addIngredient(ingr);
        recipe.addIngredient(ingr2);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        IngredientCommand ingredientCommand = ingredientService.findByIdAndRecipeId("1","1");

        assertNotNull(ingredientCommand);
        assertEquals(1L,ingredientCommand.getId());
        assertEquals(1L,ingredientCommand.getRecipeId());
        verify(recipeRepository,times(1)).findById(anyLong());

    }
}