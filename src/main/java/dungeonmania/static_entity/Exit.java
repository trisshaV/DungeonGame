package dungeonmania.static_entity;

import dungeonmania.Entity;
import dungeonmania.util.Position;

/**
 * A square that indicates the end of the game. Has following properties:
 *      - The puzzle is completed when the Player reaches this square.
 */
public class Exit extends StaticEntity {
    public Exit(String id, Position xy) {
        super(id, xy);
    }

    @Override
    public String getType() {
        return "exit";
    }

    public boolean collide(Entity entity) {
        return false;
    }
}
