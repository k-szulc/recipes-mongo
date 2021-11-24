package xyz.itbs.recipes.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import xyz.itbs.recipes.commands.CategoryCommand;
import xyz.itbs.recipes.domain.Category;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand source) {
        if(source == null) {
            return null;
        }
        final Category category = Category.builder()
                                    .id(source.getId())
                                    .description(source.getDescription())
                                    .build();
        return category;
    }

}
