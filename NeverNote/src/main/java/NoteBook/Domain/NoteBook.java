package NoteBook.Domain;

public class NoteBook implements Identifiable {

	private Long id;
	
	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}
}
