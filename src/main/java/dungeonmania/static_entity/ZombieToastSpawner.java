package dungeonmania.static_entity;

import dungeonmania.Entity;
import dungeonmania.util.Position;

/**
 * Acts like spawn points for zombie toasts. Has following properties:
 *      - Spawns zombie toasts onto available cardinally adjacent squares to the spawner's location.
 *      - Spawners can be destroyed by a Player (that has a weapon) only if they are on a cardinally adjacent square to it.
 */
public class ZombieToastSpawner extends StaticEntity {

    public ZombieToastSpawner(String id, Position xy) {
        super(id, xy);
    }

    public boolean collide(Entity entity) {
        return false;
    }
    
}
