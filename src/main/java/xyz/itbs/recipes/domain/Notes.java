package xyz.itbs.recipes.domain;

import lombok.*;


@Data
@EqualsAndHashCode(exclude = {"recipe"})
@ToString(exclude = {"recipe"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notes {

    private String id;

    private String notesBody;
    private Recipe recipe;

}
