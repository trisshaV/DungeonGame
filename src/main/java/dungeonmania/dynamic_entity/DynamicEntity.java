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

    public DynamicEntity(String id, String type, Position xy) {
        super(id, type, xy);
    }

    public boolean collide(Entity entity) {
        return true;
    }

    

    public abstract String getType();

    public double getHealth() {
        return health;
    }

    public double getAttack() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }
}
