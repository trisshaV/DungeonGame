package dungeonmania.static_entity;

import dungeonmania.Entity;
import dungeonmania.util.Position;

/**
 * Acts like barriers and will prohibit movement of certian entities. Has following properties: 
 *      - Blocks the movement of Player.
 *      - Blocks the movement of Enemies.
 *      - Blocks the movement of Boulders.
 */
public class Wall extends StaticEntity {
    /**
     * Wall Constructor
     * @param id
     * @param xy
     */
    public Wall(String id, Position xy) {
        super(id, "wall", xy);
    }
    
    /**
     * Checks for collision
     * @param entity
     * @return boolean of collision status
     */
    public boolean collide(Entity entity) {
        if (entity.getType().equals("spider")) {
            return true;
        }
        return false;
    }

    /**
     * Gets type
     * @return the type, i.e. "wall"
     */
    public String getType() {
        return "wall";
    }
    
}
