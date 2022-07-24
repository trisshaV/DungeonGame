package dungeonmania.collectible;

import dungeonmania.SerializableJSONObject;
import dungeonmania.util.Position;

/**
 * Are among the collectible entities and is stored as Player inventory. Has following properties:
 *      - Can be collected when Player moves onto the square it is on.
 *      - Used in recipes to create buildable entities.
 */
public class Arrow extends Collectible {

    /**
     * Arrow Constructor
     * @param id
     * @param xy
     * @param config
     */
    public Arrow(String id, Position xy, SerializableJSONObject config) {
        super(id, "arrow", xy);
    }

    /**
     * Gets type
     * @return the type, i.e. "arrow"
     */
    @Override
    public String getType() {
        return "arrow";
    }
}