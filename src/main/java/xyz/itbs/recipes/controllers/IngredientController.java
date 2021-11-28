package xyz.itbs.recipes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.itbs.recipes.services.RecipeService;

@Controller
public class IngredientController {

    RecipeService recipeService;

    public IngredientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String getIngredientList(@PathVariable String recipeId, Model model ){
        model.addAttribute("recipe",recipeService.getRecipeCommandById(Long.valueOf(recipeId)));
        return "recipe/ingredient/list";
    }
}
