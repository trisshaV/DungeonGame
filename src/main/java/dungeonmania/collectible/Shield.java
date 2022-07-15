package dungeonmania.collectible;

import org.json.JSONObject;

public class Shield extends Buildable{
    private int shieldDefense = 0;

    public Shield(String id, JSONObject config) {
        super(id, "shield");
        this.setDurability(config.getInt("shield_durability"));
        shieldDefense = config.getInt("shield_defence");
    }

    public int getShieldDefense() {
        return shieldDefense;
    }
}