package xyz.itbs.recipes.domain;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@ToString(exclude = {"recipe"})
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Lob
    private String notesBody;

    @OneToOne
    private Recipe recipe;

}
