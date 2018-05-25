package NoteBook.Test.Controller;

import NoteBook.Repository.NoteBookRepository;
import NoteBook.Domain.*;
import NoteBook.Test.Controller.Helpers.GenericRequestBuilder;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteControllerTest extends GenericRequestBuilder{

    private static UUID Nonexistent_ID = UUID.randomUUID();
    private static final String NOTE_UPDATE = "{\"title\": \"updated test title\", \"body\": \"updated test body\", \"tags\":[\"updated\",\"tags\"]}";

    @Autowired
    private NoteBookRepository repository;

    @Before
    public void setUp() {
        repository.clear();
    }


    private Note injectNote() {
        String[] tags = {"test", "tags"};

        Note note = new Note();
        note.setTitle("Test title");
        note.setBody("Test body");
        note.setTags(tags);

        return repository.createNote(note);
    }

    private Note generateUpdatedNote(Note original) {
        String[] updatedTags = {"test", "tags", "updated"};

        Note updated = new Note();
        updated.setTitle(original.getTitle() + " updated");
        updated.setBody(original.getBody() + " updated");
        updated.setTags(updatedTags);
        return updated;
    }

    private NoteBook injectNoteBook(){
        return repository.createNoteBook();
    }

    private ResultActions getNote(UUID id) throws Exception {
        return get("/note/{id}", id);
    }

    private ResultActions deleteNote(UUID id) throws Exception {
        return delete("/note/{id}", id);
    }

    private ResultActions updateNote(UUID id, Note updatedNote) throws Exception {
            return put("/note/{id}", updatedNote, id);
    }

    @Test
    public void GetNoteById_ShouldReturnCorrectNote_IfNoteExists() throws Exception {
        Note injectedNote = injectNote();
        //assertNoteCountIs(1);
        getNote(injectedNote.getId())
                .andExpect(status().isOk());
    }

    @Test
    public void GetNoteById_ShouldReturn404_IfNoteIsNonexistent() throws Exception{
        Note injectedNote = injectNote();
        getNote(Nonexistent_ID)
                .andExpect(status().isNotFound());
    }

    @Test
    public void DeleteNote_ShouldReturn204_IfNoteExists() throws Exception{
        Note injectedNote = injectNote();
        deleteNote(injectedNote.getId())
                .andExpect(status().isNoContent());
    }

    @Test
    public void DeleteNote_ShouldReturn404_IfNoteIsNonexistent() throws Exception{
        Note injectedNote = injectNote();
        deleteNote(Nonexistent_ID)
                .andExpect(status().isNotFound());
    }

    @Test
    public void UpdateNote_ShouldReturn200_OnSuccessfulUpdate() throws Exception{
        Note injectedNote = injectNote();
        Note updatedNote = generateUpdatedNote(injectedNote);
        updateNote(injectedNote.getId(), updatedNote)
                .andExpect(status().isOk());

    }

    @Test
    public void UpdateNote_ShouldReturn404_IfNoteIsNonexistent() throws Exception{
        Note injectedNote = injectNote();
        Note updatedNote = generateUpdatedNote(injectedNote);
        updateNote(Nonexistent_ID, updatedNote)
                .andExpect(status().isNotFound());
    }

}