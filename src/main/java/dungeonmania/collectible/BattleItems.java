package dungeonmania.collectible;
import dungeonmania.util.Position;
import org.json.JSONObject;

public abstract class BattleItems extends Collectible{
    private int durability;
    /**
     * Constructor for CombatItems
     * @param id
     * @param type
     * @param xy
     */
    public BattleItems(String id, String type, Position xy) {
        super(id, type, xy);
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int num) {
        this.durability = num;
    }
}
