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
    public int health;
    public int attack;

    public boolean collide(Entity entity) {
        return true;
    }

    public DynamicEntity(String id, Position xy) {
        super(id, xy);
    }

    public abstract String getType();

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    
}
