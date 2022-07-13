package dungeonmania.collectible;
import org.json.JSONObject;
import dungeonmania.util.Position;

/**
 * Are among the collectible entities and is stored as Player inventory. Has following properties:
 *      - Can be collected when Player moves onto the square it is on.
 *      - Used in recipes to create buildable entities.
 */
public class Arrow extends Collectible {

    public Arrow(String id, Position xy, JSONObject config) {
        super(id, "arrow", xy);
    }
    /**
     * Uses arrows to build bows.
     */
    public void use() {
        getPlayer().getInventoryList().remove((Collectible)this);
    }
}