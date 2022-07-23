package dungeonmania.collectible;

import org.json.JSONObject;

import dungeonmania.Inventory;

public abstract class Buildable {
	private final String id;
	private String type;
	public int durability;

	/**
	 * Buildable Constructor
	 * @param id
	 * @param type
	 */
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

	/**
	 * Gets type
	 * @return the type
	 */
	public String getType() {
        return this.type;
    }

	/**
	 * Sets type
	 * @param type
	 */
    public void setType(String type) {
        this.type = type;
    }

	/**
	 * Gets durability
	 * @return the durability
	 */
	public int getDurability() {
        return durability;
    }

	/**
	 * Sets durability
	 * @param num
	 */
    public void setDurability(int num) {
        this.durability = num;
    }

	static boolean checkMaterials(Inventory i) {
		return true;
	}

	static void buildItem(Inventory i) {};
}
