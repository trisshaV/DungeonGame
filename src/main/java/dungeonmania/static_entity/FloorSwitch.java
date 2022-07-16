package dungeonmania.static_entity;

import dungeonmania.Boulder;
import dungeonmania.Entity;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
/**
 * Acts like empty squares and can ONLY be activated by boulders. Has following properties:
 *      - Other entities can appear on top of it but will not activate it.
 *      - Only if a boulder is on current postion will it then activate.
 *      - If boulder is then moved off it, it is then de-activated.
 */
public class FloorSwitch extends StaticEntity {
    private boolean active;

    public FloorSwitch(String id, Position xy) {
        super(id,"switch", xy);
        active = false;
    }

    public FloorSwitch(String id, Position xy, boolean active) {
        super(id,"switch", xy);
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

    public List<Entity> activateNearby(List<Entity> entities, JSONObject config) {
        for (Entity entity : entities) {
            if (entity instanceof ActiveBomb && Position.isAdjacent(getPosition(), entity.getPosition())) {
                ActiveBomb activeBomb = (ActiveBomb) entity;
                return activeBomb.explode(entities, config);
            }
        }
        return new ArrayList<>();
    }

    @Override
    public String getType() {
        return "switch";
    }

}
