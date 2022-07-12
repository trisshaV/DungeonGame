package dungeonmania.static_entity;

<<<<<<< HEAD
import dungeonmania.Entity;
=======
import dungeonmania.util.Position;
>>>>>>> origin

/**
 * Acts like barriers and will prohibit movement of certian entities. Has following properties: 
 *      - Blocks the movement of Player.
 *      - Blocks the movement of Enemies.
 *      - Blocks the movement of Boulders.
 */
public class Wall extends StaticEntity {
    public Wall(String id, Position xy) {
        super(id, xy);
    }
    public boolean interact(Entity entity) {
        if (entity.getType().equals("spider")) {
            return true;
        }
        return false;
    }

    public String getType() {
        return "wall";
    }
    
}
