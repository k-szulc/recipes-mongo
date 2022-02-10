package xyz.itbs.recipes.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.itbs.recipes.commands.IngredientCommand;
import xyz.itbs.recipes.converters.IngredientCommandToIngredient;
import xyz.itbs.recipes.converters.IngredientToIngredientCommand;
import xyz.itbs.recipes.domain.Ingredient;
import xyz.itbs.recipes.domain.Recipe;
import xyz.itbs.recipes.exceptions.NotFoundException;
import xyz.itbs.recipes.repositories.RecipeRepository;
import xyz.itbs.recipes.repositories.UnitOfMeasureRepository;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        if(optionalRecipe.isEmpty()){
            log.error("Recipe :: " + recipeId + " :: not found !");
            throw new NotFoundException("Recipe not found for ID: " + recipeId);
//            return null;
        }
        Recipe recipe = optionalRecipe.get();
        Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream()
                .filter(ingr -> ingr.getId().equals(Long.valueOf(id)))
                .findFirst();
        if(optionalIngredient.isEmpty()){
            log.error("Ingredient :: " + id + " :: not found in recipe :: " + recipeId);
            throw new NotFoundException("Ingredient not found for ID: " + id);
//            return null;
        }

        return ingredientToIngredientCommand.convert(optionalIngredient.get());
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if(recipeOptional.isEmpty()){
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
                        .orElseThrow(()->new NotFoundException("UOM not found")));
            } else {
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredientCommandToIngredient.convert(command));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);
            Optional<Ingredient> savedIngredient = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();

            if(savedIngredient.isEmpty()){
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

    @Override
    @Transactional
    public void deleteIngredientById(String id, String recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(Long.valueOf(recipeId));

        if (recipeOptional.isEmpty()) {
            log.error("Recipe :: " + recipeId + " :: not found !");
            throw new NotFoundException("Recipe not found for ID: " + recipeId);

        } else {

            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingr -> ingr.getId().equals(Long.valueOf(id)))
                    .findFirst();
            if(ingredientOptional.isEmpty()) {
                log.error("Ingredient :: " + id + " :: not found !");
                throw new NotFoundException("Ingredient not found for ID: " + id);
            } else {
                log.info("Removing ingredient :: " + id);
                recipe.delIngredient(ingredientOptional.get());
                log.info("Saving recipe :: " + recipeId);
                recipeRepository.save(recipe);
            }
        }
    }
}
