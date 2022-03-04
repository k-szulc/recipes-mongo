package xyz.itbs.recipes.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import xyz.itbs.recipes.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, String> {
}
