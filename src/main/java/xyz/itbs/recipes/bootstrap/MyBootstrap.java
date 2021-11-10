package xyz.itbs.recipes.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import xyz.itbs.recipes.domain.*;
import xyz.itbs.recipes.repositories.CategoryRepository;
import xyz.itbs.recipes.repositories.RecipeRepository;
import xyz.itbs.recipes.repositories.UnitOfMeasureRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Component
@Profile("szu")
public class MyBootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private RecipeRepository recipeRepository;

    public MyBootstrap(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository,
                     RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Optional<Category> optionalCategoryMexican = categoryRepository.findByDescription("Mexican");
        if(!optionalCategoryMexican.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> optionalCategoryAmerican = categoryRepository.findByDescription("American");
        if(!optionalCategoryAmerican.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }
        Optional<Category> optionalCategoryFastFood = categoryRepository.findByDescription("Fast Food");
        if(!optionalCategoryFastFood.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<UnitOfMeasure> optionalUnitOfMeasureTeaspoon = unitOfMeasureRepository.findByDescription("Teaspoon");
        if(!optionalUnitOfMeasureTeaspoon.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }
        Optional<UnitOfMeasure> optionalUnitOfMeasureTablespoon = unitOfMeasureRepository.findByDescription("Tablespoon");
        if(!optionalUnitOfMeasureTablespoon.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }
        Optional<UnitOfMeasure> optionalUnitOfMeasureCup = unitOfMeasureRepository.findByDescription("Cup");
        if(!optionalUnitOfMeasureCup.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }
        Optional<UnitOfMeasure> optionalUnitOfMeasureRipe = unitOfMeasureRepository.findByDescription("Ripe");
        if(!optionalUnitOfMeasureRipe.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }
        Optional<UnitOfMeasure> optionalUnitOfMeasureCloves = unitOfMeasureRepository.findByDescription("Clove");
        if(!optionalUnitOfMeasureCloves.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }
        Optional<UnitOfMeasure> optionalUnitOfMeasureMedium = unitOfMeasureRepository.findByDescription("Medium");
        if(!optionalUnitOfMeasureMedium.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }
        Optional<UnitOfMeasure> optionalUnitOfMeasureLarge = unitOfMeasureRepository.findByDescription("Large");
        if(!optionalUnitOfMeasureLarge.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Notes guacNotes = new Notes();
        guacNotes.setNotesBody("Notes");


        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setCookTime(15);
        guacRecipe.setPrepTime(15);
        guacRecipe.setServings(8);
        guacRecipe.setDirections("Directions");
        guacRecipe.setNotes(guacNotes);
        guacRecipe.getCategories().add(optionalCategoryMexican.get());
        guacRecipe.getCategories().add(optionalCategoryFastFood.get());
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.addIngredient(new Ingredient("Sugar",BigDecimal.valueOf(5),
                optionalUnitOfMeasureTablespoon.get()));
        guacRecipe.addIngredient(new Ingredient("Avocado",BigDecimal.valueOf(1),
                optionalUnitOfMeasureLarge.get()));
        guacRecipe.setUrl("http://blablabla.com");
        guacRecipe.setSource("From Granny");


        recipeRepository.save(guacRecipe);
        log.info("Saved");
        log.info("Recipe 1 :: " + recipeRepository.findAll());



    }
}
