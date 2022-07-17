package dungeonmania.collectible;
import dungeonmania.util.Position;

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

    /**
     * Gets durability
     * @return the durability
     */
    public int getDurability() {
        return durability;
    }

    /**
     * Sets durability
     * @param num
     */
    public void setDurability(int num) {
        this.durability = num;
    }
}
