package xyz.itbs.recipes.commands;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryCommand {

    private String id;
    private String description;

}
