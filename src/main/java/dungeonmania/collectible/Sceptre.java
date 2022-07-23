package dungeonmania.collectible;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import dungeonmania.Inventory;

public class Sceptre extends Buildable {
    /**
     * Bow Constructor
     * @param id
     * @param config
     */
    public Sceptre(String id, JSONObject config) {
        super(id, "sceptre");
    }
    
    public static boolean checkMaterials(Inventory i) {
        if (i.getNoItemType("sun_stone") < 1 || (i.getNoItemType("wood") < 1 && i.getNoItemType("arrow") < 2) || (i.getNoItemType("key") < 1 && i.getNoItemType("treasure") < 1)) {
            return false;
        }
        return true;
    }
    public static List<String> requirements(Inventory i) {
        List<String> list = Arrays.asList("sun_stone");
        if (i.getNoItemType("treasure") >= 1) {
            list = Arrays.asList("sun_stone", "treasure");
            if (i.getNoItemType("wood") >= 1) {
                list = Arrays.asList("sun_stone", "treasure", "wood");
            } else if (i.getNoItemType("arrow") >= 2) {
                list = Arrays.asList("sun_stone", "treasure", "arrow", "arrow");
            }
        } else if (i.getNoItemType("key") >= 1) {
            list = Arrays.asList("sun_stone", "key");
            if (i.getNoItemType("wood") >= 1) {
                list = Arrays.asList("sun_stone", "key", "wood");
            } else if (i.getNoItemType("arrow") >= 2) {
                list = Arrays.asList("sun_stone", "key", "arrow", "arrow");
            }
            }
        return list;
    }
}
