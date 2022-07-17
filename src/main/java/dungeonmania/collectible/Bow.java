package dungeonmania.collectible;
import org.json.JSONObject;

public class Bow extends Buildable {
    
    /**
     * Bow Constructor
     * @param id
     * @param config
     */
    public Bow(String id, JSONObject config) {
        super(id, "bow");
        this.durability = config.getInt("bow_durability");
    }
}
