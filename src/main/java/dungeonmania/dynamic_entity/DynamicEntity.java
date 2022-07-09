package dungeonmania.dynamic_entity;

import dungeonmania.Entity;

/**
 * Entities that move in dungeon.
 */
public class DynamicEntity extends Entity {
    /**
     * TODO: Remove and make abstract
     * @return
     */
    @Override
    public String getType() {
        return "wall";
    }
}
