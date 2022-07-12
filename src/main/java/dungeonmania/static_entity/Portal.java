package dungeonmania.static_entity;

import dungeonmania.Entity;
import dungeonmania.util.Position;

/**
 * Each portal has a corresponding portal that allows entities to be teleported. Has following properties:
 *      - Will NOT teleport entities if ALL cardinally adjacent squares of the corresponding portal are walls.
 *      - Otherwise will teleport the entity to an avaliable cardinally adjacent square at the coresponding portal side.
 */
public class Portal extends StaticEntity {

    public Portal(String id, Position xy) {
        super(id, xy);
    }

    public boolean collide(Entity entity) {
        return false;
    }
    
}
