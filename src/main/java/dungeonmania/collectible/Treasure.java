package dungeonmania.collectible;

import org.json.JSONObject;
import dungeonmania.util.Position;

/**
 * Are among the collectible entities and is stored as Player inventory. Has following properties:
 *      - Can be collected when Player moves onto the square it is on.
 *      - Can be used to bribe mercenaries.
 *      - Used in recipes to create buildable entities.
 */
public class Treasure extends Collectible {

    public Treasure(String id, Position xy, JSONObject config) {
        super(id, "treasure", xy);
    }
    
    public void use() {
        getPlayer().getInventoryList().remove((Collectible)this);
    }
}