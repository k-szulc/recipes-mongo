package xyz.itbs.recipes.converters;


import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import xyz.itbs.recipes.commands.IngredientCommand;
import xyz.itbs.recipes.domain.Ingredient;
import xyz.itbs.recipes.domain.UnitOfMeasure;

import java.math.BigDecimal;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure) {
        this.unitOfMeasureCommandToUnitOfMeasure = unitOfMeasureCommandToUnitOfMeasure;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if(source == null ) {
            return null;
        }

        final Ingredient ingredient = Ingredient.builder()
                .id(source.getId())
                .description(source.getDescription())
                .amount(source.getAmount())
                .uom(unitOfMeasureCommandToUnitOfMeasure.convert(source.getUom()))
                .build();

        return ingredient;
    }
}
