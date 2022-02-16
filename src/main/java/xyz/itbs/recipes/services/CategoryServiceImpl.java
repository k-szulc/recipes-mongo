package xyz.itbs.recipes.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.itbs.recipes.commands.CategoryCommand;
import xyz.itbs.recipes.converters.CategoryToCategoryCommand;
import xyz.itbs.recipes.domain.Category;
import xyz.itbs.recipes.exceptions.NotFoundException;
import xyz.itbs.recipes.repositories.CategoryRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;
    CategoryToCategoryCommand categoryToCategoryCommand;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryToCategoryCommand categoryToCategoryCommand) {
        this.categoryRepository = categoryRepository;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Override
    public Set<Category> getAllCategories() {
        Set<Category> categorySet = new HashSet<>();
        categoryRepository.findAll().iterator().forEachRemaining(categorySet::add);
        log.info("Fetching all categories :: " + categorySet.size());
        return categorySet;
    }

    @Override
    public Set<CategoryCommand> getAllCategoryCommands() {
        return getAllCategories().stream()
                .map(cat -> categoryToCategoryCommand.convert(cat))
                .collect(Collectors.toSet());
    }

    public CategoryCommand getCategoryCommandById(String id){
        Optional<Category> optionalCategory = categoryRepository.findById(Long.valueOf(id));
        if(optionalCategory.isPresent()){
            Category category = optionalCategory.get();
            return categoryToCategoryCommand.convert(category);
        } else {
            throw new NotFoundException("Category not found");
        }

    }
}
