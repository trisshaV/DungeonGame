package dungeonmania.collectible;

public abstract class Buildable {
    
	private final String id;
	private String type;

	public Buildable(String id, String type) {
		this.id = id;
		this.type = type;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
