package xyz.itbs.recipes.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import xyz.itbs.recipes.commands.IngredientCommand;
import xyz.itbs.recipes.commands.UnitOfMeasureCommand;
import xyz.itbs.recipes.domain.Ingredient;
import xyz.itbs.recipes.domain.UnitOfMeasure;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class IngredientCommandToIngredientTest {

    Ingredient ingredient;
    IngredientCommand ingredientCommand;
    IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredientCommandToIngredient = new IngredientCommandToIngredient(unitOfMeasureCommandToUnitOfMeasure);
        ingredientCommand = IngredientCommand.builder()
                                .id("1")
                                .description("test")
                                .uom(UnitOfMeasureCommand.builder().id("1").build())
                                .amount(BigDecimal.valueOf(1))
                                .build();
}

    @Test
    void convertNull() {
        assertNull(ingredientCommandToIngredient.convert(null));
    }

    @Test
    void convertEmpty(){
        assertNotNull(ingredientCommandToIngredient.convert(new IngredientCommand()));
    }

    @Test
    void convert(){
        when(unitOfMeasureCommandToUnitOfMeasure.convert(any(UnitOfMeasureCommand.class)))
                .thenReturn(UnitOfMeasure.builder().id("1").build());
        ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
        assertNotNull(ingredient);
        assertEquals(ingredientCommand.getId(),ingredient.getId());
        assertEquals(ingredientCommand.getDescription(),ingredient.getDescription());
        assertEquals(ingredientCommand.getAmount(), ingredient.getAmount());
        assertEquals(ingredientCommand.getUom(), ingredientCommand.getUom());
        verify(unitOfMeasureCommandToUnitOfMeasure,times(1))
                .convert(any(UnitOfMeasureCommand.class));
    }
}