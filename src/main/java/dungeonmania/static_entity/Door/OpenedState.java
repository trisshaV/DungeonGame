package dungeonmania.static_entity.Door;

import dungeonmania.Entity;

public class OpenedState implements State {
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
