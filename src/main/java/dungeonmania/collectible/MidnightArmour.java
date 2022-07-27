package dungeonmania.collectible;
import java.util.Arrays;
import java.util.List;

import dungeonmania.Inventory;
import dungeonmania.SerializableJSONObject;

public class MidnightArmour extends Buildable{

    private double attack;
    private double defence;
    /**
     * MidnightArmour Constructor
     * @param id
     * @param config
     */
    public MidnightArmour(String id, SerializableJSONObject config) {
        super(id, "midnight_armour");
        this.attack = config.getDouble("midnight_armour_attack");
        this.defence = config.getDouble("midnight_armour_defence");
        this.durability = (int) Double.POSITIVE_INFINITY;

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

    public double getAttack() {
        return attack;
    }

    public double getDefence() {
        return defence;
    }

    
}
