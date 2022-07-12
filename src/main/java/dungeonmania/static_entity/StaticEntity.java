package dungeonmania.static_entity;

import dungeonmania.Entity;
import dungeonmania.util.Position;

/**
 * Entities that DO NOT move in dungeon.
 */
public abstract class StaticEntity extends Entity {
    public StaticEntity(String id, Position xy) {
        super(id, xy);
    }

    public abstract boolean collide(Entity entity);
    /**
     * TODO: Remove or make abstract
     * @return
     */
    @Override
    public String getType() {
        return "wall";
    }
}
