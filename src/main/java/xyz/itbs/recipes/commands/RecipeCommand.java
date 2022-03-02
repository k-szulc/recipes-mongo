package xyz.itbs.recipes.commands;

import lombok.*;
import org.hibernate.validator.constraints.URL;
import xyz.itbs.recipes.domain.Difficulty;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RecipeCommand {

    private String id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String description;
    @Min(1)
    @Max(999)
    private Integer prepTime;
    @Min(1)
    @Max(999)
    private Integer cookTime;
    @Min(1)
    @Max(999)
    private Integer servings;
    private String source;
    @URL
    private String url;
    @NotBlank
    private String directions;
    private Difficulty difficulty;
    private NotesCommand notes;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Set<CategoryCommand> categories = new HashSet<>();
    private Byte[] image;
}
