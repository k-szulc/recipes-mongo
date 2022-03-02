package xyz.itbs.recipes.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.itbs.recipes.commands.NotesCommand;
import xyz.itbs.recipes.domain.Notes;

import static org.junit.jupiter.api.Assertions.*;

class NotesToNotesCommandTest {

    Notes notes;
    NotesCommand notesCommand;
    NotesToNotesCommand notesToNotesCommand;


    @BeforeEach
    void setUp() {
        notesToNotesCommand = new NotesToNotesCommand();
        notes = Notes.builder()
                .id("1")
                .notesBody("test")
                .build();
    }

    @Test
    void convertNull() {
        assertNull(notesToNotesCommand.convert(null));
    }

    @Test
    void convertEmpty() {
        assertNotNull(notesToNotesCommand.convert(new Notes()));
    }

    @Test
    void convert() {
        notesCommand = notesToNotesCommand.convert(notes);
        assertNotNull(notesCommand);
        assertEquals(notes.getId(),notesCommand.getId());
        assertEquals(notes.getNotesBody(),notesCommand.getNotesBody());
    }
}