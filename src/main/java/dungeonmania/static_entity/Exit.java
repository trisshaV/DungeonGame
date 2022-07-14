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

    public Exit(String id, Position xy, DungeonManiaController dungeon) {
        super(id, "exit", xy);
        this.dungeon = dungeon;
        active = false;
    }

    public boolean collide(Entity entity) {
        // TODO: add and condition that every OTHER goal has been achieved 
        if (entity instanceof Player) {
            active = true;
        }
        return true;
    }
    @Override
    public String getType() {
        return "exit";
    }

    public boolean getActive() {
        return active;
    }
}
