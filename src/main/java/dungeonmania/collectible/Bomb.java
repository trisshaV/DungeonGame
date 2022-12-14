package dungeonmania.collectible;

import dungeonmania.SerializableJSONObject;
import dungeonmania.util.Position;

/**
 * Are among the collectible entities and is stored as Player inventory. Has following properties:
 *      - Can be collected when Player moves onto the square it is on.
 *      - When Player uses it, it is placed in current square Player is on and removed from inventory.
 *      - Once used it CANNOT be picked up again.
 *      - Will only detonate if placed on a cardinally adjacent square to an active switch, 
 *          if switch is NOT ACTIVE it will NOT detonate unless switch is activated later.
 *      - If detonated it will destroy all entities that are DIAGONALLY and CARDINALLY adjacent cells to it and will
 *          NOT harm the Player.
 */
public class Bomb extends Collectible {
    public int radius;

    /**
     * Bomb Constructor
     * @param id
     * @param xy
     * @param config
     */
    public Bomb(String id, Position xy, SerializableJSONObject config) {
        super(id, "bomb", xy);
        radius = config.getInt("bomb_radius");
    }

    /**
     * Gets radius
     * @return the radius
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Gets type
     * @return the type, i.e. "bomb"
     */
    @Override
    public String getType() {
        return "bomb";
    }
}
