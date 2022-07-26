package dungeonmania.static_entity.Door;

import dungeonmania.Entity;
import java.io.Serializable;

public class OpenedState implements State, Serializable {
    private Door door;

    /**
     * OpenedState Constructor
     * @param door
     */
    public OpenedState(Door door) {
        this.door = door;
    }

    /**
     * Interactivity
     * @param entity
     * @return boolean of interaction
     */
    public boolean interact(Entity entity) {
        return true;
    }
}
