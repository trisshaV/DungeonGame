package dungeonmania.static_entity;

import dungeonmania.Entity;

/**
 * Entities that DO NOT move in dungeon.
 */
public abstract class StaticEntity extends Entity {
    /**
     * TODO: Remove or make abstract
     * @return
     */
    @Override
    public String getType() {
        return "wall";
    }
}
