package dungeonmania.static_entity;

import dungeonmania.Entity;

/**
 * Entities that DO NOT move in dungeon.
 */
public abstract class StaticEntity extends Entity {
    // when passed in an entity returns true if entity is at position of the 
    // static entity
    // e.g will return true if player successfully opens a door 
   // public abstract boolean interact(Entity entity);
}
