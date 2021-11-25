package xyz.itbs.recipes.commands;

import lombok.*;
import xyz.itbs.recipes.domain.Category;
import xyz.itbs.recipes.domain.Difficulty;
import xyz.itbs.recipes.domain.Ingredient;
import xyz.itbs.recipes.domain.Notes;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RecipeCommand {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Difficulty difficulty;
    private NotesCommand notes;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Set<CategoryCommand> categories = new HashSet<>();
}
