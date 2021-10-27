package xyz.itbs.recipes.domain;

import javax.persistence.*;

@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Lob
    private String notesBody;

    @OneToOne
    private Recipe recipe;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotesBody() {
        return notesBody;
    }

    public void setNotesBody(String notesBody) {
        this.notesBody = notesBody;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
