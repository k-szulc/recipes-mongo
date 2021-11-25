package xyz.itbs.recipes.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import xyz.itbs.recipes.commands.UnitOfMeasureCommand;
import xyz.itbs.recipes.domain.UnitOfMeasure;
@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure source) {
        if(source == null){
            return null;
        }
        final UnitOfMeasureCommand unitOfMeasureCommand = UnitOfMeasureCommand.builder()
                .id(source.getId())
                .description(source.getDescription())
                .build();
        return unitOfMeasureCommand;
    }
}
