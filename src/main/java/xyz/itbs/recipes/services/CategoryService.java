package xyz.itbs.recipes.services;

import xyz.itbs.recipes.commands.CategoryCommand;
import xyz.itbs.recipes.domain.Category;

import java.util.Set;

public interface CategoryService {

    Set<Category> getAllCategories();

    Set<CategoryCommand> getAllCategoryCommands();

    CategoryCommand getCategoryCommandById(String id);
}
