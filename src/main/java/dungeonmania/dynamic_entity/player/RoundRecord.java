package dungeonmania.dynamic_entity.player;

import java.util.List;

import dungeonmania.collectible.Collectible;

public class RoundRecord {
    private double changePlayerHealth;
    private double changeEnemyHealth;
    List <Collectible> itemsUsed;

    
    public RoundRecord(double changePlayerHealth, double changeEnemyHealth, List<Collectible> itemsUsed) {
        this.changePlayerHealth = changePlayerHealth;
        this.changeEnemyHealth = changeEnemyHealth;
        this.itemsUsed = itemsUsed;
    }

    

    
}
