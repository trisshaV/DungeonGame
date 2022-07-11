package dungeonmania.dynamic_entity;

import dungeonmania.Entity;
import dungeonmania.util.Position;

/**
 * Entities that move in dungeon.
 */
public class DynamicEntity extends Entity {
    public DynamicEntity(String id, Position xy) {
        super(id, xy);
    }

    /**
     * TODO: Remove and make abstract
     * @return
     */
    @Override
    public String getType() {
        return "wall";
    }
}
