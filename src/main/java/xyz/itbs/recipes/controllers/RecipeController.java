package xyz.itbs.recipes.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import xyz.itbs.recipes.commands.RecipeCommand;
import xyz.itbs.recipes.domain.Recipe;
import xyz.itbs.recipes.exceptions.NotFoundException;
import xyz.itbs.recipes.services.RecipeService;

@Slf4j
@Controller
public class RecipeController {

    RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/{id}/show")
    public String getRecipeById(@PathVariable String id, Model model){
        Recipe recipe = recipeService.getRecipeById(Long.valueOf(id));
        model.addAttribute("recipe",recipe);
        return "recipe/show";
    }

    @RequestMapping({"/recipe/new", "/recipe/add"})
    public String newRecipe(Model model){
        model.addAttribute("recipe",new RecipeCommand());
        return "recipe/recipeform";
    }

    @RequestMapping({"recipe/{id}/update", "recipe/{id}/edit"})
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe",recipeService.getRecipeCommandById(Long.valueOf(id)));
        return "recipe/recipeform";
    }

    @PostMapping("/recipe")
    public String saveOrUpdateRecipe(RecipeCommand command){
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";
    }

    @RequestMapping({"/recipe/{id}/delete", "/recipe/{id}/remove"})
    public String deleteRecipeById(@PathVariable String id){
        recipeService.deleteRecipeById(Long.valueOf(id));
        return "redirect:/";
    }

}
