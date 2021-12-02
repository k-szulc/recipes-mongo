package xyz.itbs.recipes.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.itbs.recipes.commands.IngredientCommand;
import xyz.itbs.recipes.converters.IngredientToIngredientCommand;
import xyz.itbs.recipes.domain.Ingredient;
import xyz.itbs.recipes.domain.Recipe;
import xyz.itbs.recipes.repositories.RecipeRepository;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    RecipeRepository recipeRepository;
    IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findByIdAndRecipeId(String id, String recipeId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(Long.valueOf(recipeId));
        if(!optionalRecipe.isPresent()){
            log.error("Recipe :: " + recipeId + " :: not found !");
            return null;
        }
        Recipe recipe = optionalRecipe.get();
        Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream()
                .filter(ingr -> ingr.getId().equals(Long.valueOf(id)))
                .findFirst();
        if(!optionalIngredient.isPresent()){
            log.error("Ingredient :: " + id + " :: not found in recipe :: " + recipeId);
            return null;
        }

        return ingredientToIngredientCommand.convert(optionalIngredient.get());
    }
}
