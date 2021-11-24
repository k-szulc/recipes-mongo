package xyz.itbs.recipes.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import xyz.itbs.recipes.commands.NotesCommand;
import xyz.itbs.recipes.domain.Notes;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes source) {
        if(source == null) {
            return null;
        }
        final NotesCommand notesCommand = NotesCommand.builder()
                .id(source.getId())
                .notesBody(source.getNotesBody())
                .build();
        return notesCommand;
    }
}
