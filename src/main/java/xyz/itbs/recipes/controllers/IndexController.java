package xyz.itbs.recipes.controllers;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.itbs.recipes.services.RecipeService;
import xyz.itbs.recipes.services.RecipeServiceImpl;


@Slf4j
@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    private final RecipeService recipeService;

    public IndexController(RecipeServiceImpl recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","/index","/index.html"})
    public String getIndexPage(Model model){
        log.debug("Getting index page");
        model.addAttribute("recipes", recipeService.getAllRecipes());
        return "index";
    }
}
