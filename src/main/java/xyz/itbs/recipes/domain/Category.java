package xyz.itbs.recipes.domain;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"recipes"})
@ToString(exclude = {"recipes"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    private String id;
    private String description;

    private Set<Recipe> recipes = new HashSet<>();

}
