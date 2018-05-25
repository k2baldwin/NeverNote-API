package NoteBook.Domain;

import java.util.UUID;

public interface Identifiable extends org.springframework.hateoas.Identifiable<UUID> {
	public void setId(UUID id);
}
