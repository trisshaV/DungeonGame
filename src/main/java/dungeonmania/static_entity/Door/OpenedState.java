package dungeonmania.static_entity.Door;

import dungeonmania.Entity;

public class OpenedState implements State {
    Door door;

    public OpenedState(Door door) {
        this.door = door;
    }

    public boolean interact(Entity entity) {
        return true;
    }
}
