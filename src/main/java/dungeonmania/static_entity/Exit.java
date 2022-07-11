package dungeonmania.static_entity;

/**
 * A square that indicates the end of the game. Has following properties:
 *      - The puzzle is completed when the Player reaches this square.
 */
public class Exit extends StaticEntity {
    @Override
    public String getType() {
        return "exit";
    }
}
