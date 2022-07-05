package dungeonmania;

import dungeonmania.util.Position;

/**
 * The collection of all entities that are present in dungeon.
 */
public class Entity {
    
    /**
	 * ID
	 */
	private String id;
    
	/**
	 * position
	 */
	private Position position;

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
}
