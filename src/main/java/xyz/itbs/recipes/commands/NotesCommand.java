package xyz.itbs.recipes.commands;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotesCommand {
    private String id;
    private String notesBody;

}
