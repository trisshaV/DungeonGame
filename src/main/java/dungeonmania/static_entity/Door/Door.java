package dungeonmania.static_entity.Door;

import dungeonmania.Entity;
import dungeonmania.collectible.Key;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.static_entity.StaticEntity;
import dungeonmania.util.Position;

/**
 * A door co-exists with a corresponding key. Has following properties:
 *      - If player reaches it and has the corresponding key in inventory, Player can use key to move through it.
 *      - Once it is opened, it remains opened.
 */
public class Door extends StaticEntity {
    private Key key;
    private State opened;
    private State closed;
    private State state;
    private int keyId;

    /**
     * Door Constructor
     * @param id
     * @param xy
     */
    public Door(String id, Position xy) {
        // need to call super for static entity 
        super(id, "door", xy);
        this.opened = new OpenedState(this);
        this.closed = new ClosedState(this);
        this.state = closed;
    }

    /**
     * Check if collision
     * @param entity
     * @return true if moved to door, false otherwise
     */
    public boolean collide(Entity entity) {
        return state.interact(entity);
    }

    @Override 
    public EntityResponse getEntityResponse() {
        return new EntityResponse(getId(), (state == closed ? "door" : "door_open"), getPosition(), false);
    }
    /**
     * Gets opened door
     * @return opened state
     */
    public State getOpened() {
        return opened;
    }

    /**
     * Gets closed door
     * @return closed state
     */
    public State getClosed() {
        return closed;
    }

    /**
     * Gets type
     * @return the type, i.e. "door"
     */
    public String getType() {
        return "door";
    }

    /**
     * Gets key
     * @return the key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Sets key
     * @param key
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * Sets state
     * @param state
     */
    public void setState(State state) {
        this.state = state;
    }

    public void setKeyId(int keyId) {
        this.keyId = keyId;
    } 

    public int getKeyId() {
        return keyId;
    }
}
