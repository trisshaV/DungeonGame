package dungeonmania.static_entity.Door;

import dungeonmania.Entity;
import dungeonmania.dynamic_entity.Player;

public class ClosedState implements State {
    Door door;

    public ClosedState(Door door) {
        this.door = door;
    }

    public boolean interact(Entity entity) {
        if (entity.getType().equals("player")) {
            Player player =  (Player) entity;
            if (door.getKey().equals(player.getKey())) {
                player.removeKey();
                door.setState(door.getOpened());
                return true;
            }
        }
        else if (entity.getType().equals("spider")) {
            return true;
        }
        return false;
    }
}
