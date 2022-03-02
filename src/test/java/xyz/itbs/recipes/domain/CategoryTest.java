package xyz.itbs.recipes.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    Category category;

    @BeforeEach
    void setUp() {
        category = new Category();

    }

    @Test
    void getId() {
        String value = "4";
        category.setId(value);
        String test = category.getId();
        assertEquals(value,test);
    }

    @Test
    void getDescription() {
        String desc = "description";
        category.setDescription(desc);
        String test = category.getDescription();
        assertEquals(desc,test);
    }

}