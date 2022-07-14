package dungeonmania.collectible;

import org.json.JSONObject;

public class Shield extends Buildable{
    public Shield(String id, JSONObject config) {
        super(id, "shield");
        durability = config.getInt("shield_durability");
    }
}