package NoteBook.Test.Controller;


import NoteBook.Repository.NoteBookRepository;
import NoteBook.Test.Controller.Helpers.GenericRequestBuilder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import NoteBook.Domain.*;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;


@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteBookControllerTest extends GenericRequestBuilder{

    private static final String VALID_NOTE = "{\"title\": \"test title\", \"body\": \"test body\", \"tags\":[\"test\",\"tags\"]}";
    private static final String VALID_NOTEBOOK = "{}";
    private static Long Nonexistent_ID = 1000L;

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

    private NoteBook injectNoteBook(){
        NoteBook noteBook = new NoteBook();
        return repository.createNoteBook(noteBook);
    }

    private ResultActions createNoteBook() throws Exception{
        return post("/notebook",VALID_NOTEBOOK);
    }

    private ResultActions getNoteBook() throws Exception {
        return get("/notebook");
    }

    private ResultActions getNoteBookById(long id) throws Exception {
        return get("/notebook/{id}", id);
    }

    private ResultActions deleteNoteBook(long id) throws Exception {
        return delete("/notebook/{id}", id);
    }

    private ResultActions createNoteInNoteBook(long id) throws Exception {
        return post("/notebook/{id}/note",VALID_NOTE, id);
    }

    @Test
    public void CreateNoteBook_ShouldReturn201_IfRequestBodyIsValid() throws Exception{
        createNoteBook()
                .andExpect(status().isCreated());
    }

    @Test
    public void GetNoteBookById_ShouldReturnCorrectNoteBook_IfNoteBookExists() throws Exception {
        NoteBook injectedNoteBook = injectNoteBook();
        getNoteBookById(injectedNoteBook.getId())
                .andExpect(status().isOk());
    }

    @Test
    public void GetNoteBook_ShouldReturn404_WhenNoteBookIsNonexistent() throws Exception{
        NoteBook injectedNoteBook = injectNoteBook();
        getNoteBookById(Nonexistent_ID)
                .andExpect(status().isNotFound());
    }

    @Test
    public void GetAllNoteBooks_ShouldReturn200_WhenNoteBooksAreNonexistent() throws Exception{
        getNoteBook()
                .andExpect(status().isOk()).
                andExpect(content().string(equalTo("[]")));
    }

    @Test
    public void GetAllNoteBooks_ShouldReturn200_WhenNoteBooksExist() throws Exception{
        NoteBook injectedNoteBook = injectNoteBook();
        getNoteBook()
                .andExpect(status().isOk());
    }

    @Test
    public void DeleteNotebook_ShouldReturn404_WhenNoteBookIsNonexistent() throws Exception{
        deleteNoteBook(Nonexistent_ID)
                .andExpect(status().isNotFound());
    }

    @Test
    public void DeleteNotebook_ShouldReturn204_WhenNoteBookExists() throws Exception{
        NoteBook injectedNoteBook = injectNoteBook();
        deleteNoteBook(injectedNoteBook.getId())
                .andExpect(status().isNoContent());
    }

    @Test
    public void CreateNoteInNoteBook_ShouldReturn404_IfNoteBookIsNonexistent() throws Exception{
        createNoteInNoteBook(Nonexistent_ID).andExpect(status().isNotFound());
    }

    @Test
    public void CreateNoteInNoteBook_ShouldReturn201_IfNoteBookExists() throws Exception{
        NoteBook injectedNoteBook = injectNoteBook();
        createNoteInNoteBook(injectedNoteBook.getId()).andExpect(status().isCreated());
    }
}