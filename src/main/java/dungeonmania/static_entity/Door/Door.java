package dungeonmania.static_entity.Door;

import dungeonmania.Entity;
import dungeonmania.collectible.Key;
import dungeonmania.static_entity.StaticEntity;

/**
 * A door co-exists with a corresponding key. Has following properties:
 *      - If player reaches it and has the corresponding key in inventory, Player can use key to move through it.
 *      - Once it is opened, it remains opened.
 */
public class Door extends StaticEntity {
    Key key;
    State opened;
    State closed;
    State state = closed;

    public Door(Key key) {
        // need to call super for static entity 
        this.key = key;
        this.opened = new OpenedState(this);
        this.closed = new ClosedState(this);
    }

    // Returns true if moved to door, false otherwise
    public boolean interact(Entity entity) {
        return state.interact(entity);
    }

    public State getOpened() {
        return opened;
    }

    public State getClosed() {
        return closed;
    }

    public String getName() {
        return "door";
    }

    public Key getKey() {
        return key;
    }

    public void setState(State state) {
        this.state = state;
    }

    
    
}
