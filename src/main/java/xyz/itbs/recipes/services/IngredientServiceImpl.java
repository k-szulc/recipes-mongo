package xyz.itbs.recipes.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.itbs.recipes.commands.IngredientCommand;
import xyz.itbs.recipes.converters.IngredientCommandToIngredient;
import xyz.itbs.recipes.converters.IngredientToIngredientCommand;
import xyz.itbs.recipes.domain.Ingredient;
import xyz.itbs.recipes.domain.Recipe;
import xyz.itbs.recipes.repositories.RecipeRepository;
import xyz.itbs.recipes.repositories.UnitOfMeasureRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    RecipeRepository recipeRepository;
    UnitOfMeasureRepository unitOfMeasureRepository;
    IngredientToIngredientCommand ingredientToIngredientCommand;
    IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
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

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if(!recipeOptional.isPresent()){
            log.error("Recipe :: " + command.getRecipeId() + " :: not found !");
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(command.getUom().getId())
                        .orElseThrow(()->new RuntimeException("UOM NOT FOUND")));
            } else {
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredientCommandToIngredient.convert(command));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);
            Optional<Ingredient> savedIngredient = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();

            if(!savedIngredient.isPresent()){
                savedIngredient = savedRecipe.getIngredients().stream()
                        .filter
                            (recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter
                            (recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
                        .findFirst();
            }

            return ingredientToIngredientCommand.convert(savedIngredient.get());
        }
    }
}
