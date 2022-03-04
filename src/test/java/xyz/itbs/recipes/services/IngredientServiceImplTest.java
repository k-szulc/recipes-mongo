package xyz.itbs.recipes.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import xyz.itbs.recipes.commands.IngredientCommand;
import xyz.itbs.recipes.converters.IngredientCommandToIngredient;
import xyz.itbs.recipes.converters.IngredientToIngredientCommand;
import xyz.itbs.recipes.converters.UnitOfMeasureCommandToUnitOfMeasure;
import xyz.itbs.recipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import xyz.itbs.recipes.domain.Ingredient;
import xyz.itbs.recipes.domain.Recipe;
import xyz.itbs.recipes.repositories.RecipeRepository;
import xyz.itbs.recipes.repositories.UnitOfMeasureRepository;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    IngredientService ingredientService;



    @Mock
    RecipeRepository recipeRepository;
//    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        IngredientToIngredientCommand ingredientToIngredientCommand =
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        IngredientCommandToIngredient ingredientCommandToIngredient =
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        ingredientService = new IngredientServiceImpl(recipeRepository,
                unitOfMeasureRepository,
                ingredientToIngredientCommand,
                ingredientCommandToIngredient);
    }

    @Test
    void findByIdAndRecipeId() {
        Ingredient ingr = Ingredient.builder().id("1").build();
        Ingredient ingr2 = Ingredient.builder().id("2").build();
        Recipe recipe = Recipe.builder().id("1").ingredients(new HashSet<>()).build();
        recipe.addIngredient(ingr);
        recipe.addIngredient(ingr2);

        when(recipeRepository.findById(anyString())).thenReturn(Optional.of(recipe));
        IngredientCommand ingredientCommand = ingredientService.findByIdAndRecipeId("1","1");

        assertNotNull(ingredientCommand);
        assertEquals("1",ingredientCommand.getId());
        assertEquals("1",ingredientCommand.getRecipeId());
        verify(recipeRepository,times(1)).findById(anyString());

    }

    @Test
    void saveIngredientCommand(){

        IngredientCommand command = new IngredientCommand();
        command.setId("3");
        command.setRecipeId("2");

        Optional<Recipe> recipeOptional = Optional.of(Recipe.builder().id("1").ingredients(new HashSet<>()).build());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(Ingredient.builder().id("3").build());

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        assertEquals("3", savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository,times(1)).save(any(Recipe.class));

    }

    @Test
    void deleteIngredientById(){
        Recipe recipe = Recipe.builder().id("1").ingredients(new HashSet<>()).build();
        Ingredient ingredient = Ingredient.builder().id("1").build();
        Ingredient ingredient2 = Ingredient.builder().id("2").build();
        Ingredient ingredient3 = Ingredient.builder().id("3").build();
        recipe.addIngredient(ingredient);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        when(recipeRepository.findById(anyString())).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(any())).thenReturn(recipe);
        ingredientService.deleteIngredientById(recipe.getId(),ingredient2.getId());
        assertEquals(2,recipe.getIngredients().size());
        verify(recipeRepository,times(1)).findById(anyString());
        verify(recipeRepository,times(1)).save(any());

    }
}