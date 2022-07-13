package dungeonmania.collectible;

import org.json.JSONObject;

public class Shield extends Buildable{
    private int durability;
    public Shield(String id, JSONObject config) {
        super(id, "shield");
        durability = config.getInt("shield_durability");
    }
    public int getDurability() {
        return durability;
    }

    public void setDurability(int num) {
        this.durability = num;
    }
}