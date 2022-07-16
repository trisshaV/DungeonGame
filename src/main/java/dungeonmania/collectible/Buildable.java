package dungeonmania.collectible;

public abstract class Buildable {
    
	private final String id;
	private String type;
	public int durability;

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

	public int getDurability() {
        return durability;
    }

    public void setDurability(int num) {
        this.durability = num;
    }
    
}
