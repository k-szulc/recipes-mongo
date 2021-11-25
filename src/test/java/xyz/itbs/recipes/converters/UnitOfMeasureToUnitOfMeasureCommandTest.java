package xyz.itbs.recipes.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.itbs.recipes.commands.UnitOfMeasureCommand;
import xyz.itbs.recipes.domain.UnitOfMeasure;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureToUnitOfMeasureCommandTest {

    UnitOfMeasure unitOfMeasure;
    UnitOfMeasureCommand unitOfMeasureCommand;
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @BeforeEach
    void setUp() {
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        unitOfMeasure = UnitOfMeasure.builder()
                .id(2L)
                .description("test")
                .build();
    }

    @Test
    void convertNull() {
        assertNull(unitOfMeasureToUnitOfMeasureCommand.convert(null));
    }

    @Test
    void convertEmpty() {
        assertNotNull(unitOfMeasureToUnitOfMeasureCommand.convert(new UnitOfMeasure()));
    }

    @Test
    void convert() {
        unitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure);
        assertNotNull(unitOfMeasureCommand);
        assertEquals(unitOfMeasure.getId(),unitOfMeasureCommand.getId());
        assertEquals(unitOfMeasure.getDescription(),unitOfMeasureCommand.getDescription());
    }
}