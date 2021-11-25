package xyz.itbs.recipes.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.itbs.recipes.commands.RecipeCommand;
import xyz.itbs.recipes.converters.RecipeCommandToRecipe;
import xyz.itbs.recipes.converters.RecipeToRecipeCommand;
import xyz.itbs.recipes.domain.Recipe;
import xyz.itbs.recipes.repositories.RecipeRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final RecipeCommandToRecipe recipeCommandToRecipe;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             RecipeToRecipeCommand recipeToRecipeCommand,
                             RecipeCommandToRecipe recipeCommandToRecipe) {
        this.recipeRepository = recipeRepository;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
    }

    public Set<Recipe> getAllRecipes() {
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        log.info("Getting all recipes :: " + recipeSet.size());
        return recipeSet;
    }

    @Override
    public Recipe getRecipeById(Long id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if(!optionalRecipe.isPresent()){
            return null;
        }
        Recipe recipe = optionalRecipe.get();
        log.info("Fetching Recipe :: ID :: " + recipe.getId());
        log.debug("Fetching Recipe :: " + recipe);
        return recipe;

    }

    @Transactional
    @Override
    public RecipeCommand getRecipeCommandById(Long id){
        return recipeToRecipeCommand.convert(getRecipeById(id));
    }

    @Transactional
    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe savedRecipe = recipeRepository.save(recipeCommandToRecipe.convert(recipeCommand));
        log.info("Saved Recipe :: ID :: " + savedRecipe.getId());
        log.debug("Saved Recipe :: " + savedRecipe);
        return recipeToRecipeCommand.convert(savedRecipe);
    }
}
