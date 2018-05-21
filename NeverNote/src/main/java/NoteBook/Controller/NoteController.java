package NoteBook.Controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.time.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import NoteBook.Domain.Note;
import NoteBook.Repository.NoteBookRepository;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/note", produces = "application/json")
public class NoteController {

    @Autowired
    private NoteBookRepository repository;

    //Returns a note
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Note> findNoteById(@PathVariable Long id) {
        Optional<Note> note = repository.findNoteById(id);

        if (note.isPresent()) {
            return new ResponseEntity<>(note.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Deletes a note
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        boolean wasDeleted = repository.deleteNote(id);
        HttpStatus responseStatus = wasDeleted ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(responseStatus);
    }

    //Updates a note
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note updatedNote) {
        Optional<Note> note = repository.findNoteById(id);
        if (note.isPresent()) {
            boolean wasUpdated = repository.updateNote(id, updatedNote);
                if (wasUpdated) {
                    //Return updated note with 200 if update succeeds
                    return new ResponseEntity<>(repository.findNoteById(id).get(), HttpStatus.OK);
                }
                else{
                    //Return 500 if note exists but update fails
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }
        else {
            //Return 404 if note doesn't exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
