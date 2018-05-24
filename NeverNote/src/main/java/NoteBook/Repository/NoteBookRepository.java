package NoteBook.Repository;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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

    private ConcurrentHashMap<Long,Note> Notes = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long,NoteBook> NoteBooks = new ConcurrentHashMap<>();



    //Public Methods

    public Note findNoteById(Long id) {
        return Notes.get(id);
    }

    public NoteBook findNoteBookById(Long id) {
        return NoteBooks.get(id);
    }

    public Collection<Note> findNotesByNoteBookId(Long id, String tag) {
        Collection<Note> notesInNoteBook =
                Notes.entrySet().stream().filter(e -> e.getValue().getNoteBookId().equals(id))
                        .map(Map.Entry::getValue).collect(Collectors.toSet());
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

    public Collection<NoteBook> findAllNoteBooks() {
        return NoteBooks.values();
    }

    public void clear() {
        Notes.clear();
        NoteBooks.clear();
    }

    public boolean deleteNoteBook(Long id) {
        //simulating cascade delete, since we don't have FK on in memory database
        cascadeDeleteNotes(id);
        return NoteBooks.remove(id,NoteBooks.get(id));
    }

    public boolean deleteNote(Long id) {
        return Notes.remove(id,Notes.get(id));
    }

    public boolean createNoteInNoteBook(Long id, Note note) {
        NoteBook noteBook = findNoteBookById(id);
        if(noteBook!=null) {
            note.setNoteBookId(id);
            createNote(note);
            return true;
        }
        else return false;
    }

    public Note createNote(Note note) {
        note.setId(noteIdGenerator.getNextId());
        note.setCreated(LocalDateTime.now());
        Notes.put(note.getId(),note);
        return note;
    }

    public boolean updateNote(Long id, Note updated) {
        if (updated == null) {
            return false;
        } else {
            Note note = findNoteById(id);
            if(note!=null) {
                updateNoteIfExists(note, updated);
            }
            return true;
        }
    }

    public NoteBook createNoteBook() {
        NoteBook noteBook = new NoteBook();
        noteBook.setId(noteBookIdGenerator.getNextId());
        NoteBooks.put(noteBook.getId(),noteBook);
        return noteBook;
    }



    //Private Methods

    private List<Note> filterNotesByTag(String tag, Collection<Note> notes) {
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
        Collection<Note> notes = findNotesByNoteBookId(id, null);
        for(Note i:notes){
            if(i.getNoteBookId().equals(id)){
                Notes.remove(i.getId());
            }
        }
    }

}