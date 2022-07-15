package dungeonmania.static_entity.Door;

import dungeonmania.Entity;
import dungeonmania.collectible.Key;
import dungeonmania.static_entity.StaticEntity;
import dungeonmania.util.Position;

/**
 * A door co-exists with a corresponding key. Has following properties:
 *      - If player reaches it and has the corresponding key in inventory, Player can use key to move through it.
 *      - Once it is opened, it remains opened.
 */
public class Door extends StaticEntity {
    Key key;
    State opened;
    State closed;
    State state;

    public Door(String id, Position xy) {
        // need to call super for static entity 
        super(id, "door", xy);
        this.opened = new OpenedState(this);
        this.closed = new ClosedState(this);
        this.state = closed;
    }

    // Returns true if moved to door, false otherwise
    public boolean collide(Entity entity) {
        return state.interact(entity);
    }

    public State getOpened() {
        return opened;
    }

    public State getClosed() {
        return closed;
    }

    public String getType() {
        return "door";
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public void setState(State state) {
        this.state = state;
    }  
}
