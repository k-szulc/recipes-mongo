package xyz.itbs.recipes.services;

import xyz.itbs.recipes.commands.RecipeCommand;
import xyz.itbs.recipes.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getAllRecipes();

    Set<Recipe> getNotNullDescriptionRecipes();

    Recipe getRecipeById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    RecipeCommand getRecipeCommandById(Long id);

    void deleteRecipeById(Long id);
}
