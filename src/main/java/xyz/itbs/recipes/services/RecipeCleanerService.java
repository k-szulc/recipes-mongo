package xyz.itbs.recipes.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Slf4j
@Service
public class RecipeCleanerService {

    private final RecipeService recipeService;

    public RecipeCleanerService(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Scheduled(fixedDelay = 300000)
    @Transactional
    public void cleanUp(){
        log.debug("CLEANUP :: Before cleanup :: " + recipeService.getAllRecipes().size());
        recipeService.getAllRecipes().stream()
                .filter(recipe -> recipe.getDescription()==null)
                .forEach(recipe -> recipeService.deleteRecipeById(recipe.getId()));
        log.debug("CLEANUP :: After cleanup :: " + recipeService.getAllRecipes().size());

    }
}
