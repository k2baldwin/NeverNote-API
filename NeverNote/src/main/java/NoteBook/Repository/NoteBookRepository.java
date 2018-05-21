package NoteBook.Repository;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ArrayList;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import NoteBook.Domain.NoteBook;
import NoteBook.Domain.Note;

@Repository
public class NoteBookRepository {

    @Autowired
    private IdGenerator noteBookIdGenerator;
    @Autowired
    private IdGenerator noteIdGenerator;

    private List<Note> Notes = Collections.synchronizedList(new ArrayList<>());
    private List<NoteBook> NoteBooks = Collections.synchronizedList(new ArrayList<>());



    //Public Methods

    public Optional<Note> findNoteById(Long id) {return Notes.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    public Optional<NoteBook> findNoteBookById(Long id) {
        return NoteBooks.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    public List<Note> findNotesByNoteBookId(Long id, String tag) {
        List<Note> notesInNoteBook = Notes.stream().filter(e -> e.getNoteBookId().equals(id)).collect(Collectors.toList());
        if(tag==null) {
            return notesInNoteBook;
        }
        else {
            if (notesInNoteBook.size() > 0) {
                List<Note> notesWithTag = filterNotesByTag(tag, notesInNoteBook);
                return notesWithTag;
            }
            else return notesInNoteBook;
        }
    }

    public List<NoteBook> findAllNoteBooks() {
        return NoteBooks;
    }

    public void clear() {
        Notes.clear();
        NoteBooks.clear();
    }

    public boolean deleteNoteBook(Long id) {
        //simulating cascade delete, since we don't have FK on in memory database
        cascadeDeleteNotes(id);
        return NoteBooks.removeIf(element -> element.getId().equals(id));
    }

    public boolean deleteNote(Long id) {
        return Notes.removeIf(element -> element.getId().equals(id));
    }

    public boolean createNoteInNoteBook(Long id, Note note) {
        Optional<NoteBook> noteBook = findNoteBookById(id);
        if(noteBook.isPresent()) {
            note.setNoteBookId(id);
            createNote(note);
            return noteBook.isPresent();
        }
        else return false;
    }

    public Note createNote(Note note) {
        Notes.add(note);
        note.setCreated(LocalDateTime.now());
        note.setId(noteIdGenerator.getNextId());
        return note;
    }

    public boolean updateNote(Long id, Note updated) {
        if (updated == null) {
            return false;
        } else {
            Optional<Note> note = findNoteById(id);
            note.ifPresent(original -> updateNoteIfExists(original, updated));
            return note.isPresent();
        }
    }

    public NoteBook createNoteBook() {
        NoteBook noteBook = new NoteBook();
        NoteBooks.add(noteBook);
        noteBook.setId(noteBookIdGenerator.getNextId());
        return noteBook;
    }



    //Private Methods

    private List<Note> filterNotesByTag(String tag, List<Note> notes) {
        List<Note> notesToReturn = new ArrayList<Note>();
        for (Note i : notes) {
            String[] tags = i.getTags();
            boolean match = false;
            for (String j : tags) {
                if (j.equals(tag)) {
                    match = true;
                }
            }
            if (match) {
                notesToReturn.add(i);
            }
        }
        return notesToReturn;
    }

    private void updateNoteIfExists(Note original, Note desired) {
        original.setTitle(desired.getTitle());
        original.setBody(desired.getBody());
        original.setTags(desired.getTags());
        original.setLastModified(LocalDateTime.now());
    }

    private void cascadeDeleteNotes(Long id){
        Notes.removeIf(element -> element.getNoteBookId().equals(id));
    }

}