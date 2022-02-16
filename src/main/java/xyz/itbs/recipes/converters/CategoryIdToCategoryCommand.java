package xyz.itbs.recipes.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import xyz.itbs.recipes.commands.CategoryCommand;
import xyz.itbs.recipes.services.CategoryService;

@Component
public class CategoryIdToCategoryCommand implements Converter<String, CategoryCommand> {

    private final CategoryService categoryService;

    public CategoryIdToCategoryCommand(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(String source) {
        return categoryService.getCategoryCommandById(source);
    }
}
