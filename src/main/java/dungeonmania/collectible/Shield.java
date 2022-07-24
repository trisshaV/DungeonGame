package dungeonmania.collectible;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import dungeonmania.Inventory;

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

    public static boolean checkMaterials(Inventory i) {
        if (i.getNoItemType("wood") < 2 || (i.getNoItemType("treasure") < 1 && i.getNoItemType("key") < 1)) {
            return false;
        }
        return true;
    }

    public static List<String> requirements(Inventory i) {
        List<String> list = Arrays.asList("wood", "wood");
        if (i.getNoItemType("treasure") >= 1) {
            list = Arrays.asList("wood", "wood", "treasure");
        } else if (i.getNoItemType("key") >= 1) {
            list = Arrays.asList("wood", "wood", "key");
        }
        return list;
    }

    /**
     * Gets shield defence
     * @return the shield defence
     */
    public int getShieldDefense() {
        return shieldDefense;
    }
}