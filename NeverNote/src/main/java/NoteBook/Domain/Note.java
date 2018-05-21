package NoteBook.Domain;
import java.time.*;
import java.util.List;

public class Note implements Identifiable {

	private Long id;
	private Long noteBookId;
	private String title;
	private String body;
	private String[] tags;
	private LocalDateTime created;
	private LocalDateTime lastModified;
	
	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getNoteBookId(){
		return noteBookId;
	}

	public void setNoteBookId(Long noteBookId){
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
