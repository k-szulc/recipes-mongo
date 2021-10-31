package xyz.itbs.recipes.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.itbs.recipes.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String description);

}
