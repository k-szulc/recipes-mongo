package xyz.itbs.recipes.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import xyz.itbs.recipes.commands.UnitOfMeasureCommand;
import xyz.itbs.recipes.domain.UnitOfMeasure;
@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        if(source == null) {
            return null;
        }

        final UnitOfMeasure unitOfMeasure = UnitOfMeasure.builder()
                .id(source.getId())
                .description(source.getDescription())
                .build();
        return unitOfMeasure;
    }
}
