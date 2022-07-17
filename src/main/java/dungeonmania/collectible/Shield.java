package dungeonmania.collectible;

import org.json.JSONObject;

public class Shield extends Buildable{
    private int shieldDefense = 0;

    /**
     * Shield Constructor
     * @param id
     * @param config
     */
    public Shield(String id, JSONObject config) {
        super(id, "shield");
        this.setDurability(config.getInt("shield_durability"));
        shieldDefense = config.getInt("shield_defence");
    }

    /**
     * Gets shield defence
     * @return the shield defence
     */
    public int getShieldDefense() {
        return shieldDefense;
    }
}