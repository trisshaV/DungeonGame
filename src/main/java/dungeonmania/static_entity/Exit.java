package dungeonmania.static_entity;

import dungeonmania.DungeonManiaController;
import dungeonmania.Entity;
import dungeonmania.dynamic_entity.Player;
import dungeonmania.util.Position;

/**
 * A square that indicates the end of the game. Has following properties:
 *      - The puzzle is completed when the Player reaches this square.
 */
public class Exit extends StaticEntity {
    DungeonManiaController dungeon;
    boolean active;

    /**
     * Exit Constructor
     * @param id
     * @param xy
     * @param dungeon
     */
    public Exit(String id, Position xy, DungeonManiaController dungeon) {
        super(id, "exit", xy);
        this.dungeon = dungeon;
        active = false;
    }

    /**
     * Checks for collision
     * @param entity
     * @return boolean of collision status
     */
    public boolean collide(Entity entity) { 
        if (entity instanceof Player) {
            active = true;
        }
        return true;
    }
    
    /**
     * Gets type
     * @return the type, i.e. "exit"
     */
    @Override
    public String getType() {
        return "exit";
    }

    /**
     * Gets activity
     * @return active state
     */
    public boolean getActive() {
        return active;
    }
}
