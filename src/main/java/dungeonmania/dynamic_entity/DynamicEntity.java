package dungeonmania.dynamic_entity;

import java.util.List;

import dungeonmania.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/**
 * Entities that move in dungeon.
 */
public abstract class DynamicEntity extends Entity {
    public abstract void updatePos(Direction d, List<Entity> l);
    public double health;
    public double attack;

    public abstract String getType();

    /**
     * DynamicEntity Constructor
     * @param id
     * @param type
     * @param xy
     */
    public DynamicEntity(String id, String type, Position xy) {
        super(id, type, xy);
    }

    /**
     * Check for collision
     * @param entity
     * @return boolean of collision status
     */
    public boolean collide(Entity entity) {
        return true;
    }

    /**
     * Gets health
     * @return the health
     */
    public double getHealth() {
        return health;
    }

    /**
     * Gets attack
     * @return the attack
     */
    public double getAttack() {
        return attack;
    }

    /**
     * Sets Health
     * @param health
     */
    public void setHealth(double health) {
        this.health = health;
    }
}
