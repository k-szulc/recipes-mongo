package xyz.itbs.recipes.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import xyz.itbs.recipes.converters.CategoryToCategoryCommand;
import xyz.itbs.recipes.repositories.CategoryRepository;

import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;
    @Mock
    CategoryToCategoryCommand categoryToCategoryCommand;
    CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryServiceImpl(categoryRepository,categoryToCategoryCommand);
    }

    @Test
    void getAllCategories() {
    }

    @Test
    void getAllCategoryCommands() {
    }

    @Test
    void getCategoryCommandById() {
    }
}