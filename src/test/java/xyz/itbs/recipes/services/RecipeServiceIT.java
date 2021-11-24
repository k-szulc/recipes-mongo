package xyz.itbs.recipes.services;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.itbs.recipes.commands.RecipeCommand;
import xyz.itbs.recipes.converters.RecipeCommandToRecipe;
import xyz.itbs.recipes.converters.RecipeToRecipeCommand;
import xyz.itbs.recipes.domain.Recipe;
import xyz.itbs.recipes.repositories.RecipeRepository;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class RecipeServiceIT {

    @Autowired
    RecipeService recipeService;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Transactional
    @Test
    public void setNewDescription_bySaveRecipeCommand(){
        //given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe recipe = recipes.iterator().next();
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
        //when
        recipeCommand.setDescription("New description");
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        //then
        assertEquals("New description", savedRecipeCommand.getDescription());
        assertEquals(recipe.getId(), savedRecipeCommand.getId());
        assertEquals(recipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(recipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}