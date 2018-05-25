package NoteBook.Domain;
import java.time.*;
import java.util.List;
import java.util.UUID;

public class Note implements Identifiable {

	private UUID id;
	private UUID noteBookId;
	private String title;
	private String body;
	private String[] tags;
	private LocalDateTime created;
	private LocalDateTime lastModified;
	
	@Override
	public UUID getId() {
		return id;
	}
	
	@Override
	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getNoteBookId(){
		return noteBookId;
	}

	public void setNoteBookId(UUID noteBookId){
		this.noteBookId =  noteBookId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[]  tags) {
		this.tags = tags;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}

}
