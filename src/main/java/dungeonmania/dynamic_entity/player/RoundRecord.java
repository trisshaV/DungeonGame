package dungeonmania.dynamic_entity.player;

import java.util.List;

import dungeonmania.collectible.Collectible;

public class RoundRecord {
    private double changePlayerHealth;
    private double changeEnemyHealth;
    List <Object> itemsUsed;

    
    public RoundRecord(double changePlayerHealth, double changeEnemyHealth, List<Object> itemsUsed) {
        this.changePlayerHealth = changePlayerHealth;
        this.changeEnemyHealth = changeEnemyHealth;
        this.itemsUsed = itemsUsed;
    }


    public double getChangePlayerHealth() {
        return changePlayerHealth;
    }


    public double getChangeEnemyHealth() {
        return changeEnemyHealth;
    }


    public List<Object> getItemsUsed() {
        return itemsUsed;
    }
    
}