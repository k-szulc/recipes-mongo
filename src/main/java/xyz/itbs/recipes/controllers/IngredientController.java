package xyz.itbs.recipes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.itbs.recipes.commands.IngredientCommand;
import xyz.itbs.recipes.commands.RecipeCommand;
import xyz.itbs.recipes.services.IngredientService;
import xyz.itbs.recipes.services.RecipeService;

@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String getIngredientList(@PathVariable String recipeId, Model model ){
        if(recipeId == null || recipeId.equals("null")) {
            RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(RecipeCommand.builder()
                    .id(Long.valueOf(recipeService.getAllRecipes().size()+1))
                    .build());
            recipeId = String.valueOf(savedRecipeCommand.getId());
        }
        model.addAttribute("recipe",recipeService.getRecipeCommandById(Long.valueOf(recipeId)));
        return "recipe/ingredient/list";
    }

    @GetMapping("recipe/{recipeId}/ingredients/{id}/view")
    public String getIngredientByIdAndRecipeId(@PathVariable String id, @PathVariable String recipeId, Model model){
        model.addAttribute("ingredient", ingredientService.findByIdAndRecipeId(id,recipeId));
        return "recipe/ingredient/view";
    }
}
