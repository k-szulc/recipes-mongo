package xyz.itbs.recipes.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import xyz.itbs.recipes.commands.CategoryCommand;
import xyz.itbs.recipes.commands.IngredientCommand;
import xyz.itbs.recipes.commands.RecipeCommand;
import xyz.itbs.recipes.domain.Category;
import xyz.itbs.recipes.domain.Ingredient;
import xyz.itbs.recipes.domain.Recipe;

import java.util.HashSet;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final CategoryToCategoryCommand categoryToCategoryCommand;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final NotesToNotesCommand notesToNotesCommand;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryToCategoryCommand,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 NotesToNotesCommand notesToNotesCommand) {
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.notesToNotesCommand = notesToNotesCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {

        if(source == null){
            return null;
        }

        final RecipeCommand recipeCommand = RecipeCommand.builder()
                .id(source.getId())
                .description(source.getDescription())
                .cookTime(source.getCookTime())
                .prepTime(source.getPrepTime())
                .difficulty(source.getDifficulty())
                .directions(source.getDirections())
                .servings(source.getServings())
                .url(source.getUrl())
                .source(source.getSource())
                .notes(notesToNotesCommand.convert(source.getNotes()))
                .categories(new HashSet<CategoryCommand>())
                .ingredients(new HashSet<IngredientCommand>())
                .build();

        if(source.getCategories() != null && source.getCategories().size() > 0){
            source.getCategories()
                    .forEach(category -> recipeCommand.getCategories()
                            .add(categoryToCategoryCommand.convert(category)));
        }
        if(source.getIngredients() != null && source.getIngredients().size() > 0){
            source.getIngredients()
                    .forEach(ingredient -> recipeCommand.getIngredients()
                            .add(ingredientToIngredientCommand.convert(ingredient)));
        }
        return recipeCommand;
    }
}
