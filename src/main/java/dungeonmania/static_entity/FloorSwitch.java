package dungeonmania.static_entity;

import dungeonmania.Entity;
import dungeonmania.SerializableJSONObject;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Acts like empty squares and can ONLY be activated by boulders. Has following properties:
 *      - Other entities can appear on top of it but will not activate it.
 *      - Only if a boulder is on current postion will it then activate.
 *      - If boulder is then moved off it, it is then de-activated.
 */
public class FloorSwitch extends StaticEntity {
    private boolean active;

    /**
     * FloorSwitch Constructor
     * @param id
     * @param xy
     */
    public FloorSwitch(String id, Position xy) {
        super(id,"switch", xy);
        active = false;
    }

    /**
     * FloorSwitch Constructor
     * @param id
     * @param xy
     * @param active
     */
    public FloorSwitch(String id, Position xy, boolean active) {
        super(id,"switch", xy);
        this.active = active;
    }

    /**
     * Get activity
     * @return active state
     */
    public boolean getActive() {
        return active;
    }

    /**
     * Check for collision
     * @param entity
     * @return boolean of collision status
     */
    public boolean collide(Entity entity) {
        if (entity.getType().equals("boulder")) {
            active = true;
        }
        else {
            active = false;
        }
        return true;
    }

    /**
     * Trigger bomb explosion
     * @param entities
     * @param config
     * @return array of entities
     */
    public List<Entity> activateNearby(List<Entity> entities, SerializableJSONObject config) {
        for (Entity entity : entities) {
            if (entity instanceof ActiveBomb && Position.isAdjacent(getPosition(), entity.getPosition())) {
                ActiveBomb activeBomb = (ActiveBomb) entity;
                return activeBomb.explode(entities, config);
            }
        }
        return new ArrayList<>();
    }

    /**
     * Gets type
     * @return the type, i.e. "switch"
     */
    @Override
    public String getType() {
        return "switch";
    }
}
