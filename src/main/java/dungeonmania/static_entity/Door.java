package dungeonmania.static_entity;

import dungeonmania.util.Position;

/**
 * A door co-exists with a corresponding key. Has following properties:
 *      - If player reaches it and has the corresponding key in inventory, Player can use key to move through it.
 *      - Once it is opened, it remains opened.
 */
public class Door extends StaticEntity {

    public Door(String id, Position xy) {
        super(id, xy);
    }

    @Override
    public String getType() {
        return "door";
    }
}
