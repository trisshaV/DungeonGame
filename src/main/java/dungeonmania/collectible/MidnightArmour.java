package dungeonmania.collectible;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import dungeonmania.Inventory;

public class MidnightArmour extends Buildable{
    /**
     * MidnightArmour Constructor
     * @param id
     * @param config
     */
    public MidnightArmour(String id, JSONObject config) {
        super(id, "midnight_armour");
    }
    
    public static boolean checkMaterials(Inventory i) {
        if (i.getNoItemType("sword") < 1 || (i.getNoItemType("sun_stone") < 1)) {
            return false;
        }
        return true;
    }

    public static List<String> requirements() {
        return Arrays.asList("sword", "sun_stone");
    }
}
