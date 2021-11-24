package xyz.itbs.recipes.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import xyz.itbs.recipes.commands.CategoryCommand;
import xyz.itbs.recipes.domain.Category;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category source) {
        if(source == null) {
            return null;
        }
        CategoryCommand categoryCommand = CategoryCommand.builder()
                .id(source.getId())
                .description(source.getDescription())
                .build();
        return categoryCommand;
    }
}
