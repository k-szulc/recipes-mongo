package xyz.itbs.recipes.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import xyz.itbs.recipes.commands.IngredientCommand;
import xyz.itbs.recipes.commands.UnitOfMeasureCommand;
import xyz.itbs.recipes.domain.Ingredient;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient source) {
        if(source == null) {
            return null;
        }
        final IngredientCommand ingredientCommand = IngredientCommand.builder()
                                                    .id(source.getId())
                                                    .description(source.getDescription())
                                                    .amount(source.getAmount())
                                                    .uom(unitOfMeasureToUnitOfMeasureCommand.convert(source.getUom()))
                                                    .build();
        return ingredientCommand;
    }
}
