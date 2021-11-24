package xyz.itbs.recipes.commands;

import lombok.*;
import xyz.itbs.recipes.domain.UnitOfMeasure;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientCommand {
    private Long id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand uom;


}
