package dungeonmania.collectible;
import org.json.JSONObject;

public class Bow extends Buildable {
    public Bow(String id, JSONObject config) {
        super(id, "bow");
        this.setDurability(config.getInt("bow_durability"));
    }

}
