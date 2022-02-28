package xyz.itbs.recipes.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.itbs.recipes.commands.CategoryCommand;
import xyz.itbs.recipes.commands.RecipeCommand;
import xyz.itbs.recipes.domain.Recipe;
import xyz.itbs.recipes.services.CategoryService;
import xyz.itbs.recipes.services.RecipeService;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@Controller
public class RecipeController {

    RecipeService recipeService;
    CategoryService categoryService;

    public RecipeController(RecipeService recipeService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @ModelAttribute("allCategories")
    public Collection<CategoryCommand> getCategories(){
        return categoryService.getAllCategoryCommands();
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
        RecipeCommand recipeCommand = recipeService.getRecipeCommandById(Long.valueOf(id));
        model.addAttribute("recipe",recipeCommand);
        return "recipe/recipeform";
    }

    @PostMapping("/recipe")
    public String saveOrUpdateRecipe(@Valid @ModelAttribute("recipe") RecipeCommand command,
                                     BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(error -> log.error(error.toString()));
            model.addAttribute("recipeCategories", command.getCategories());
            return "recipe/recipeform";
        }
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";
    }

    @RequestMapping({"/recipe/{id}/delete", "/recipe/{id}/remove"})
    public String deleteRecipeById(@PathVariable String id){
        recipeService.deleteRecipeById(Long.valueOf(id));
        return "redirect:/";
    }

}
