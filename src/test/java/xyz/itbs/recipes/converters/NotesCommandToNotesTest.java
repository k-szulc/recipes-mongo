package xyz.itbs.recipes.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.itbs.recipes.commands.NotesCommand;
import xyz.itbs.recipes.domain.Notes;

import static org.junit.jupiter.api.Assertions.*;

class NotesCommandToNotesTest {

    Notes notes;
    NotesCommand notesCommand;
    NotesCommandToNotes notesCommandToNotes;

    @BeforeEach
    void setUp() {
        notesCommandToNotes = new NotesCommandToNotes();
        notesCommand = NotesCommand.builder()
                .id(1L)
                .notesBody("test")
                .build();
    }

    @Test
    void convertNull() {
        assertNull(notesCommandToNotes.convert(null));
    }

    @Test
    void convertEmpty() {
        assertNotNull(notesCommandToNotes.convert(new NotesCommand()));

    }

    @Test
    void convert() {
        notes = notesCommandToNotes.convert(notesCommand);
        assertNotNull(notes);
        assertEquals(notesCommand.getId(),notes.getId());
        assertEquals(notesCommand.getNotesBody(),notes.getNotesBody());
    }
}