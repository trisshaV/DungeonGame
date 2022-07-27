package dungeonmania.static_entity.Door;

import dungeonmania.Entity;
import dungeonmania.dynamic_entity.Player;
import java.io.Serializable;

public class ClosedState implements State, Serializable {
    Door door;

    /**
     * ClosedState Constructor
     * @param door
     */
    public ClosedState(Door door) {
        this.door = door;
    }

    /**
     * Interaction of Player and Doors
     * @param entity
     * @return boolean of player interaction
     */
    public boolean interact(Entity entity) {
        if (entity.getType().equals("player")) {
            Player player =  (Player) entity;
            if (player.hasItem("sun_stone")) {
                door.setState(door.getOpened());
                return true;
            }
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
