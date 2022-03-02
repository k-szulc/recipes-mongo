package xyz.itbs.recipes.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.itbs.recipes.commands.CategoryCommand;
import xyz.itbs.recipes.domain.Category;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {

    CategoryToCategoryCommand categoryToCategoryCommand;
    Category category;
    CategoryCommand categoryCommand;


    @BeforeEach
    void setUp() {
        category = Category.builder().id("1").description("test").build();
        categoryToCategoryCommand = new CategoryToCategoryCommand();

    }

    @Test
    void convert() {

        categoryCommand = categoryToCategoryCommand.convert(category);
        assertNotNull(categoryCommand);
        assertEquals(category.getId(), categoryCommand.getId());
        assertEquals(category.getDescription(), categoryCommand.getDescription());
    }

    @Test
    void convertNull() throws Exception{
        assertNull(categoryToCategoryCommand.convert(null));
    }

    @Test
    void convertEmpty() {
        assertNotNull(categoryToCategoryCommand.convert(new Category()));
    }
}