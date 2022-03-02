package xyz.itbs.recipes.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import xyz.itbs.recipes.commands.IngredientCommand;
import xyz.itbs.recipes.commands.RecipeCommand;
import xyz.itbs.recipes.commands.UnitOfMeasureCommand;
import xyz.itbs.recipes.services.IngredientService;
import xyz.itbs.recipes.services.RecipeService;
import xyz.itbs.recipes.services.UnitOfMeasureService;

@Controller
@Slf4j
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService,
                                IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String getIngredientList(@PathVariable String recipeId, Model model ){
        if(recipeId == null || recipeId.equals("null")) {
            RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(RecipeCommand.builder()
                    .id(String.valueOf(recipeService.getAllRecipes().size()+1))
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

    @GetMapping("recipe/{recipeId}/ingredients/{id}/edit")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String id,
                                         Model model){
        model.addAttribute("ingredient", ingredientService.findByIdAndRecipeId(id,recipeId));
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("recipe/{recipeId}/ingredients/new")
    public String newRecipe(@PathVariable String recipeId, Model model){

        RecipeCommand recipeCommand = recipeService.getRecipeCommandById(Long.valueOf(recipeId));

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);
        model.addAttribute("ingredient", ingredientCommand);
        ingredientCommand.setUom(new UnitOfMeasureCommand());
        model.addAttribute("uomList",unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredients")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command){
        IngredientCommand ingredientCommand = ingredientService.saveIngredientCommand(command);

        return "redirect:/recipe/" + ingredientCommand.getRecipeId() + "/ingredients/" + ingredientCommand.getId()
                + "/view";
    }

    @GetMapping("recipe/{recipeId}/ingredients/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String id){
        ingredientService.deleteIngredientById(id,recipeId);
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
