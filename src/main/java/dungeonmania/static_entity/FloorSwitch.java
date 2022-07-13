package dungeonmania.static_entity;

import dungeonmania.Boulder;
import dungeonmania.Entity;
import dungeonmania.util.Position;

/**
 * Acts like empty squares and can ONLY be activated by boulders. Has following properties:
 *      - Other entities can appear on top of it but will not activate it.
 *      - Only if a boulder is on current postion will it then activate.
 *      - If boulder is then moved off it, it is then de-activated.
 */
public class FloorSwitch extends StaticEntity {
    private boolean active;

    public FloorSwitch(String id, Position xy) {
        super(id, xy);
        active = false;
    }

    public FloorSwitch(String id, Position xy, boolean active) {
        super(id, xy);
        this.active = active;
    }

    public boolean getActive() {
        return active;
    }

    public boolean collide(Entity entity) {
        if (entity.getType().equals("boulder")) {
            active = true;
        }
        else {
            active = false;
        }
        return true;
    }

    public String getType() {
        return "switch";
    }
}
