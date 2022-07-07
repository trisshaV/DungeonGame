package dungeonmania.static_entity.Door;

import dungeonmania.Entity;
import dungeonmania.dynamic_entity.Player;

public class ClosedState implements State {
    Door door;

    public ClosedState(Door door) {
        this.door = door;
    }

    public boolean interact(Entity entity) {
        if (entity.getName().equals("Player")) {
            Player player =  (Player) entity;
            if (door.getKey().equals(player.getKey())) {
                player.removeKey();
                door.setState(door.getOpened());
                return true;
            }
        }
        return false;
    }
}
