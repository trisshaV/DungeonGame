package dungeonmania.collectible;
import org.json.JSONObject;

public class Bow extends Buildable {
    
    public Bow(String id, JSONObject config) {
        super(id, "bow");
        this.durability = config.getInt("bow_durability");
    }
}
