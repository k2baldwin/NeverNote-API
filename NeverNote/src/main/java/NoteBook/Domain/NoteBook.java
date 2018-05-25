package NoteBook.Domain;

import java.util.UUID;

public class NoteBook implements Identifiable {

	private UUID id;
	
	@Override
	public UUID getId() {
		return id;
	}
	
	@Override
	public void setId(UUID id) {
		this.id = id;
	}
}
