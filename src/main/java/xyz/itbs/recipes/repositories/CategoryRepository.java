package xyz.itbs.recipes.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.itbs.recipes.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
