package xyz.itbs.recipes.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.itbs.recipes.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
