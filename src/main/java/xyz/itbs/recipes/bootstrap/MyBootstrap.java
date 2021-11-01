package xyz.itbs.recipes.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import xyz.itbs.recipes.domain.*;
import xyz.itbs.recipes.repositories.CategoryRepository;
import xyz.itbs.recipes.repositories.RecipeRepository;
import xyz.itbs.recipes.repositories.UnitOfMeasureRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@Profile("szu")
public class MyBootstrap implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MyBootstrap.class);
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
    public void run(String... args) throws Exception {

        Optional<Category> optionalCategoryMexican = categoryRepository.findByDescription("Mexican");
        Optional<Category> optionalCategoryAmerican = categoryRepository.findByDescription("American");
        Optional<Category> optionalCategoryFastFood = categoryRepository.findByDescription("Fast Food");
        Optional<UnitOfMeasure> optionalUnitOfMeasureTeaspoon = unitOfMeasureRepository.findByDescription("Teaspoon");
        Optional<UnitOfMeasure> optionalUnitOfMeasureTablespoon = unitOfMeasureRepository.findByDescription("Tablespoon");
        Optional<UnitOfMeasure> optionalUnitOfMeasureCup = unitOfMeasureRepository.findByDescription("Cup");
        Optional<UnitOfMeasure> optionalUnitOfMeasureRipe = unitOfMeasureRepository.findByDescription("Ripe");
        Optional<UnitOfMeasure> optionalUnitOfMeasureCloves = unitOfMeasureRepository.findByDescription("Cloves");
        Optional<UnitOfMeasure> optionalUnitOfMeasureMedium = unitOfMeasureRepository.findByDescription("Medium");
        Optional<UnitOfMeasure> optionalUnitOfMeasureLarge = unitOfMeasureRepository.findByDescription("Large");

        Ingredient avocado = new Ingredient();
        avocado.setAmount(BigDecimal.valueOf(8));
        avocado.setDescription("Hass avocado");
        avocado.setUom(optionalUnitOfMeasureRipe.get());


        Ingredient garlic = new Ingredient();
        garlic.setAmount(BigDecimal.valueOf(3));
        garlic.setDescription("Peeled Garlic");
        garlic.setUom(optionalUnitOfMeasureCloves.get());

        Ingredient salt = new Ingredient();
        salt.setAmount(BigDecimal.valueOf(0.5));
        salt.setDescription("Kosher Salt");
        salt.setUom(optionalUnitOfMeasureTeaspoon.get());

        Ingredient redOnion = new Ingredient();
        redOnion.setAmount(BigDecimal.valueOf(0.5));
        redOnion.setDescription("Red Onion");
        redOnion.setUom(optionalUnitOfMeasureMedium.get());

        Ingredient jalapeno = new Ingredient();
        jalapeno.setAmount(BigDecimal.valueOf(0.5));
        jalapeno.setDescription("Jalapeno");
        jalapeno.setUom(optionalUnitOfMeasureLarge.get());

        Ingredient cilantro = new Ingredient();
        cilantro.setAmount(BigDecimal.valueOf(0.25));
        cilantro.setDescription("Chopped Cilantro");
        cilantro.setUom(optionalUnitOfMeasureCup.get());

        Ingredient limeJuice = new Ingredient();
        limeJuice.setAmount(BigDecimal.valueOf(2));
        limeJuice.setDescription("Fresh Lime Juice");
        limeJuice.setUom(optionalUnitOfMeasureTablespoon.get());

        Notes notes1 = new Notes();
        notes1.setNotesBody("Super Long Blahblahblah");


        Recipe recipe1 = new Recipe();
//        optionalCategoryMexican.ifPresent(category -> recipe1.getCategories().add(category));
        recipe1.setDescription("Perfect Guacamole");
        recipe1.setCookTime(15);
        recipe1.setPrepTime(15);
        recipe1.setServings(8);
        recipe1.setDirections("Bla bla blah");
        recipe1.setNotes(notes1);
        notes1.setRecipe(recipe1);
        recipe1.getCategories().add(optionalCategoryMexican.get());
        recipe1.getCategories().add(optionalCategoryFastFood.get());
        recipe1.setDifficulty(Difficulty.EASY);
        recipe1.getIngredients().add(avocado);
        recipe1.getIngredients().add(garlic);
        garlic.setRecipe(recipe1);
        avocado.setRecipe(recipe1);
        optionalCategoryMexican.get().getRecipes().add(recipe1);
        optionalCategoryFastFood.get().getRecipes().add(recipe1);
        optionalCategoryAmerican.get().getRecipes().add(recipe1);
        categoryRepository.save(optionalCategoryFastFood.get());
        categoryRepository.save(optionalCategoryMexican.get());
        notes1.setRecipe(recipe1);
        recipe1.setUrl("http://blablabla.com");
        recipe1.setSource("From Granny");


        recipeRepository.save(recipe1);
        logger.warn("Saved");
        logger.warn("Recipe 1 :: " + recipeRepository.findAll());



    }
}
