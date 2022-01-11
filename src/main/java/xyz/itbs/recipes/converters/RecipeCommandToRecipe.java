package xyz.itbs.recipes.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import xyz.itbs.recipes.commands.RecipeCommand;
import xyz.itbs.recipes.domain.Recipe;
import java.util.stream.Collectors;

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
                .categories(source.getCategories() != null ? source.getCategories()
                        .stream()
                        .map(categoryCommandToCategory::convert)
                        .collect(Collectors.toSet()) : null)
                .ingredients(source.getIngredients() != null ? source.getIngredients()
                        .stream()
                        .map(ingredientCommandToIngredient::convert)
                        .collect(Collectors.toSet()) : null)
                .image(source.getImage())
                .build();
        return recipe;
    }
}
