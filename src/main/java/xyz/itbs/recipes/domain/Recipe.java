package xyz.itbs.recipes.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Recipe {

    @Id
    private String id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;


    private String directions;
    private Difficulty difficulty;
    private Byte[] image;

    private Notes notes;

    private Set<Ingredient> ingredients = new HashSet<>();
    @DBRef
    private Set<Category> categories = new HashSet<>();


    public void setNotes(Notes notes) {

//        this.notes = notes;
        notes.setRecipe(this);
    }

    public Recipe addIngredient(Ingredient ingredient){
//        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }

    public Recipe delIngredient(Ingredient ingredient){
//        ingredient.setRecipe(null);
        this.ingredients.remove(ingredient);
        return this;
    }
}
