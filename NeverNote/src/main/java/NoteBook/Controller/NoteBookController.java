package NoteBook.Controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import NoteBook.Domain.NoteBook;
import NoteBook.Domain.Note;
import NoteBook.Repository.NoteBookRepository;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/notebook", produces = "application/json")
public class NoteBookController {

    @Autowired
    private NoteBookRepository repository;

    //Returns all notebooks
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<NoteBook>> findAllNoteBooks() {
        Collection<NoteBook> noteBooks = repository.findAllNoteBooks();
        return new ResponseEntity<>(noteBooks, HttpStatus.OK);
    }

    //Creates a notebook
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<NoteBook> createNoteBook() {
        NoteBook createdNoteBook = repository.createNoteBook();
        return new ResponseEntity<>(createdNoteBook, HttpStatus.CREATED);
    }

    //Returns a notebook
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<NoteBook> findNoteBookById(@PathVariable UUID id) {
        NoteBook noteBook = repository.findNoteBookById(id);
        if (noteBook != null) {
            return new ResponseEntity<>(noteBook, HttpStatus.OK);
        }
        //404 if notebook doesn't exist
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Deletes a notebook
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteNoteBook(@PathVariable UUID id) {
        boolean wasDeleted = repository.deleteNoteBook(id);
        HttpStatus responseStatus = wasDeleted ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(responseStatus);
    }

    //Returns all notes in a notebook
    @RequestMapping(value = "/{id}/note", method = RequestMethod.GET)
    public ResponseEntity<Collection<Note>> findNotesByNoteBookId(@PathVariable UUID id, @RequestParam(value="tag",required=false) String tag) {
        NoteBook noteBook = repository.findNoteBookById(id);
        if (noteBook != null) {
            //Empty set instead of 404 if notebook doesn't have any notes
            return new ResponseEntity<>(repository.findNotesByNoteBookId(id, tag), HttpStatus.OK);
        }
        //404 if notebook doesn't exist
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Creates a note in a notebook
    @RequestMapping(value = "/{id}/note", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<NoteBook> addNoteToNoteBook(@PathVariable UUID id, @RequestBody Note note) {
        NoteBook noteBook = repository.findNoteBookById(id);
        if (noteBook != null) {
            boolean wasUpdated = repository.createNoteInNoteBook(id, note);
                if (wasUpdated) {
                    return new ResponseEntity<>(HttpStatus.CREATED);
                } else {
                    //500 if note fails to create for some reason
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }
        //404 if notebook doesn't exist
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}