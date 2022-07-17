package dungeonmania.dynamic_entity.player;

import java.util.List;

public class RoundRecord {
    private double changePlayerHealth;
    private double changeEnemyHealth;
    List <ItemRecord> itemsUsed;

    /**
     * RoundRecord Constructor
     * @param changePlayerHealth
     * @param changeEnemyHealth
     * @param itemsUsed
     */
    public RoundRecord(double changePlayerHealth, double changeEnemyHealth, List<ItemRecord> itemsUsed) {
        this.changePlayerHealth = changePlayerHealth;
        this.changeEnemyHealth = changeEnemyHealth;
        this.itemsUsed = itemsUsed;
    }

    /**
     * Gets changed player health
     * @return change in player health
     */
    public double getChangePlayerHealth() {
        return changePlayerHealth;
    }


    /**
     * Gets changed enemy health
     * @return change in enemy health
     */
    public double getChangeEnemyHealth() {
        return changeEnemyHealth;
    }


    /**
     * Gets items used
     * @return the used items
     */
    public List<ItemRecord> getItemsUsed() {
        return itemsUsed;
    }
}