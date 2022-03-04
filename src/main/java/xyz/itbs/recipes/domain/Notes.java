package xyz.itbs.recipes.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Data
@EqualsAndHashCode(exclude = {"recipe"})
@ToString(exclude = {"recipe"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Notes {

    @Id
    private String id;

    private String notesBody;
    private Recipe recipe;

}
