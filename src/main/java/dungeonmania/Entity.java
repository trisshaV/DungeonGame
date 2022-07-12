package dungeonmania;

import dungeonmania.dynamic_entity.Player;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

/**
 * The collection of all entities that are present in dungeon.
 */
public abstract class Entity {

	private final String id;
	private Position position;

	public Entity(String id, Position xy) {
		this.id = id;
		position = xy;
	}

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

    public boolean collide(Player player) {
        return false;
    }
}
