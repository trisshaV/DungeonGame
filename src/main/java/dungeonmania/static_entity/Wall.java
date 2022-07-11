package dungeonmania.static_entity;

/**
 * Acts like barriers and will prohibit movement of certian entities. Has following properties: 
 *      - Blocks the movement of Player.
 *      - Blocks the movement of Enemies.
 *      - Blocks the movement of Boulders.
 */
public class Wall extends StaticEntity {

    @Override
    public String getType() {
        return "wall";
    }
}
