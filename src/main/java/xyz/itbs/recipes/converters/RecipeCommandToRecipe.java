package xyz.itbs.recipes.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import xyz.itbs.recipes.commands.RecipeCommand;
import xyz.itbs.recipes.domain.Category;
import xyz.itbs.recipes.domain.Ingredient;
import xyz.itbs.recipes.domain.Recipe;

import java.util.HashSet;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryCommandToCategory;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final NotesCommandToNotes notesCommandToNotes;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryCommandToCategory,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 NotesCommandToNotes notesCommandToNotes) {
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.notesCommandToNotes = notesCommandToNotes;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if(source == null){
            return null;
        }

        final Recipe recipe = Recipe.builder()
                .id(source.getId())
                .description(source.getDescription())
                .cookTime(source.getCookTime())
                .prepTime(source.getPrepTime())
                .difficulty(source.getDifficulty())
                .directions(source.getDirections())
                .servings(source.getServings())
                .url(source.getUrl())
                .source(source.getSource())
                .notes(notesCommandToNotes.convert(source.getNotes()))
                .categories(new HashSet<Category>())
                .ingredients(new HashSet<Ingredient>())
                .build();

        if(source.getCategories() != null && source.getCategories().size() > 0){
            source.getCategories()
                    .forEach(categoryCommand -> recipe.getCategories()
                            .add(categoryCommandToCategory.convert(categoryCommand)));
        }
        if(source.getIngredients() != null && source.getIngredients().size() > 0){
            source.getIngredients()
                    .forEach(ingredientCommand -> recipe.getIngredients()
                            .add(ingredientCommandToIngredient.convert(ingredientCommand)));
        }
        return recipe;
    }
}
