package xyz.itbs.recipes.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import xyz.itbs.recipes.commands.RecipeCommand;
import xyz.itbs.recipes.converters.RecipeCommandToRecipe;
import xyz.itbs.recipes.converters.RecipeToRecipeCommand;
import xyz.itbs.recipes.domain.Recipe;
import xyz.itbs.recipes.exceptions.NotFoundException;
import xyz.itbs.recipes.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService, CleanerService{

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

    public Set<Recipe> getNotNullDescriptionRecipes() {
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        recipeSet = recipeSet.stream()
                .filter(recipe -> recipe.getDescription()!=null)
                .collect(Collectors.toSet());
        log.info("Getting all recipes :: " + recipeSet.size());
        return recipeSet;
    }

    @Override
    public Recipe getRecipeById(Long id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if(optionalRecipe.isEmpty()){
            throw new NotFoundException("Recipe not found for ID: " + id);
        }
        Recipe recipe = optionalRecipe.get();
        log.info("Fetching Recipe :: ID :: " + recipe.getId());
        log.debug("Fetching Recipe :: " + recipe);
        return recipe;

    }

    @Override
    public RecipeCommand getRecipeCommandById(Long id){
        return recipeToRecipeCommand.convert(getRecipeById(id));
    }

    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe savedRecipe = recipeRepository.save(recipeCommandToRecipe.convert(recipeCommand));
        log.info("Saved Recipe :: ID :: " + savedRecipe.getId());
        log.debug("Saved Recipe :: " + savedRecipe);
        return recipeToRecipeCommand.convert(savedRecipe);
    }


    @Override
    public void deleteRecipeById(Long id){
        recipeRepository.deleteById(id);
        log.info("Removed Recipe :: ID :: " + id);
    }

    @Override
    @Scheduled(fixedDelay = 300000)
    public void cleanUp() {
        log.debug("CLEANUP :: Before cleanup :: " + getAllRecipes().size());
        getAllRecipes().stream()
                .filter(recipe -> recipe.getDescription()==null)
                .forEach(recipe -> deleteRecipeById(Long.valueOf(recipe.getId())));
        log.debug("CLEANUP :: After cleanup :: " + getAllRecipes().size());
    }
}
