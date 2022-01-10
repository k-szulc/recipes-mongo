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

import javax.persistence.criteria.CriteriaBuilder;
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
        Ingredient ingr = Ingredient.builder().id(1L).build();
        Ingredient ingr2 = Ingredient.builder().id(2L).build();
        Recipe recipe = Recipe.builder().id(1L).ingredients(new HashSet<>()).build();
        recipe.addIngredient(ingr);
        recipe.addIngredient(ingr2);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        IngredientCommand ingredientCommand = ingredientService.findByIdAndRecipeId("1","1");

        assertNotNull(ingredientCommand);
        assertEquals(1L,ingredientCommand.getId());
        assertEquals(1L,ingredientCommand.getRecipeId());
        verify(recipeRepository,times(1)).findById(anyLong());

    }

    @Test
    void saveIngredientCommand(){

        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(Recipe.builder().id(1L).ingredients(new HashSet<>()).build());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(Ingredient.builder().id(3L).build());

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository,times(1)).save(any(Recipe.class));

    }

    @Test
    void deleteIngredientById(){
        Recipe recipe = Recipe.builder().id(1L).ingredients(new HashSet<>()).build();
        Ingredient ingredient = Ingredient.builder().id(1L).build();
        Ingredient ingredient2 = Ingredient.builder().id(2L).build();
        Ingredient ingredient3 = Ingredient.builder().id(3L).build();
        recipe.addIngredient(ingredient);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(any())).thenReturn(recipe);
        ingredientService.deleteIngredientById(recipe.getId().toString(),ingredient2.getId().toString());
        assertEquals(2,recipe.getIngredients().size());
        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeRepository,times(1)).save(any());

    }
}