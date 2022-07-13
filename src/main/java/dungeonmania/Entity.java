package dungeonmania;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

/**
 * The collection of all entities that are present in dungeon.
 */
public abstract class Entity {

	private final String id;
	private Position position;
	private String type;

	public Entity(String id, String type, Position xy) {
		this.id = id;
		this.type = type;
		position = xy;
	}

	public abstract boolean collide(Entity entity); 

	/**
	 * default EntityResponse. 
	 * Always returns false for isInteractable unless overridden.
	 */
	public EntityResponse getEntityResponse() {
		return new EntityResponse(id, type, position, false);
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

	public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

	@Override
    public String toString() {
        return id + type;
    }
    
}
