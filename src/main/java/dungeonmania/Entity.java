package dungeonmania;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

/**
 * The collection of all entities that are present in dungeon.
 */
public abstract class Entity {

	private String id;
	private Position position;

	public abstract String getType();

	/**
	 * default EntityResponse. 
	 * Always returns false for isInteractable unless overridden.
	 */
	public EntityResponse getEntityResponse() {
		return new EntityResponse(id, getType(), position, false);
	}

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
