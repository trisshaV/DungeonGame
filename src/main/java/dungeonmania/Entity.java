package dungeonmania;

import dungeonmania.dynamic_entity.Player;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

/**
 * The collection of all entities that are present in dungeon.
 */
public abstract class Entity {
	private final String id;
	private Position position;
	private String type;

	public abstract boolean collide(Entity entity);

	/**
	 * Constructor for Entity
	 * @param id
	 * @param type
	 * @param xy
	 */
	public Entity(String id, String type, Position xy) {
		this.id = id;
		this.type = type;
		position = xy;
	} 

	/**
	 * default EntityResponse. 
	 * @return always false for isInteractable unless overridden.
	 */
	public EntityResponse getEntityResponse() {
		return new EntityResponse(id, type, position, false);
	}

	/**
	 * Gets position
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Sets position
	 * @param position the position to set
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * Gets id
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
	 * Interaction of player
	 * @param player
	 * @throws InvalidActionException
	 */
    public void interact(Player player) throws InvalidActionException {
    }
}
