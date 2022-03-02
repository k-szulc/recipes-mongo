package xyz.itbs.recipes.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.itbs.recipes.commands.CategoryCommand;
import xyz.itbs.recipes.domain.Category;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {

    Category category;
    CategoryCommand categoryCommand;
    CategoryCommandToCategory categoryCommandToCategory;

    @BeforeEach
    void setUp(){
        categoryCommandToCategory = new CategoryCommandToCategory();
        categoryCommand = CategoryCommand.builder().id("1").description("test").build();
    }

    @Test
    void convertNull(){
        assertNull(categoryCommandToCategory.convert(null));
    }

    @Test
    void convertEmpty(){
        assertNotNull(categoryCommandToCategory.convert(new CategoryCommand()));
    }

    @Test
    void convert(){
        category = categoryCommandToCategory.convert(categoryCommand);
        assertNotNull(category);
        assertEquals(categoryCommand.getId(),category.getId());
        assertEquals(categoryCommand.getDescription(),category.getDescription());
    }

}