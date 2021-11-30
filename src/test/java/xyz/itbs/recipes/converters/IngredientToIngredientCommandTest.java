package xyz.itbs.recipes.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import xyz.itbs.recipes.commands.IngredientCommand;
import xyz.itbs.recipes.commands.UnitOfMeasureCommand;
import xyz.itbs.recipes.domain.Ingredient;
import xyz.itbs.recipes.domain.Recipe;
import xyz.itbs.recipes.domain.UnitOfMeasure;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class IngredientToIngredientCommandTest {

    Ingredient ingredient;
    IngredientCommand ingredientCommand;
    IngredientToIngredientCommand ingredientToIngredientCommand;
    @Mock
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredientToIngredientCommand = new IngredientToIngredientCommand(unitOfMeasureToUnitOfMeasureCommand);
        ingredient = Ingredient.builder()
                .id(1L)
                .description("test")
                .amount(new BigDecimal(1))
                .uom(UnitOfMeasure.builder().id(1L).build())
                .recipe(Recipe.builder().id(2L).build())
                .build();
    }

    @Test
    void convertNull() {
        assertNull(ingredientToIngredientCommand.convert(null));
    }

    @Test
    void convertEmpty(){
        assertNotNull(ingredientToIngredientCommand.convert(new Ingredient()));

    }

    @Test
    void convert(){
        when(unitOfMeasureToUnitOfMeasureCommand.convert(any(UnitOfMeasure.class)))
                .thenReturn(UnitOfMeasureCommand.builder().id(1L).build());
        ingredientCommand = ingredientToIngredientCommand.convert(ingredient);
        assertNotNull(ingredientCommand);
        assertEquals(ingredient.getId(),ingredientCommand.getId());
        assertEquals(ingredient.getRecipe().getId(),ingredientCommand.getRecipeId());
        assertEquals(ingredient.getAmount(),ingredientCommand.getAmount());
        assertEquals(ingredient.getDescription(),ingredientCommand.getDescription());
        assertEquals(ingredient.getUom().getId(),ingredientCommand.getUom().getId());
        assertEquals(ingredient.getUom().getDescription(),ingredientCommand.getUom().getDescription());
        verify(unitOfMeasureToUnitOfMeasureCommand,times(1))
            .convert(any(UnitOfMeasure.class));
    }
}