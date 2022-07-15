package dungeonmania.collectible;

import org.json.JSONObject;
import dungeonmania.util.Position;

/**
 * Are among the collectible entities and is stored as Player inventory. Has following properties:
 *      - Can be collected when Player moves onto the square it is on.
 *      - Used in recipes to create buildable entities.
 */
public class Wood extends Collectible {

    public Wood(String id, Position xy, JSONObject config) {
        super(id, "wood", xy);
    }

    /**
     * Uses wood to build bows and shields. 
     */
    public void use() {
        getPlayer().getInventoryList().remove((Collectible)this);
    }

    @Override
    public String getType() {
        return "wood";
    }
}