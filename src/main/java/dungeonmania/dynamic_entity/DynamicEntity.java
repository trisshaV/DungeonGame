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

    public boolean collide(Entity entity) {
        return false;
    }

    public DynamicEntity(String id, Position xy) {
        super(id, xy);
    }

    public abstract String getType();
}
