package xyz.itbs.recipes.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.itbs.recipes.domain.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {
}
