package dungeonmania.static_entity;

import dungeonmania.Entity;
import dungeonmania.util.Position;

/**
 * Entities that DO NOT move in dungeon.
 */
public abstract class StaticEntity extends Entity {
<<<<<<< HEAD
    // when passed in an entity returns true if entity is at position of the 
    // static entity
    // e.g will return true if player successfully opens a door 
   // public abstract boolean interact(Entity entity);
=======
    public StaticEntity(String id, String type, Position xy) {
        super(id, type, xy);
    }

    public abstract boolean collide(Entity entity);

>>>>>>> origin
}
