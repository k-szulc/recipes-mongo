package xyz.itbs.recipes.services;

import xyz.itbs.recipes.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByIdAndRecipeId(String id, String recipeId);
    IngredientCommand saveIngredientCommand(IngredientCommand command);

}
