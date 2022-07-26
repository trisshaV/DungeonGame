package dungeonmania.dynamic_entity;

import java.util.List;
import java.util.Random;

import org.json.JSONObject;

import dungeonmania.Entity;
import dungeonmania.SerializableJSONObject;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/**
 * Mob entity that will harm a player.
 *  - Moves in random directions, cannot use portals and are constricted to the same conditions as Player movement.
 *  - Possesses the chance to instead increase their health when in battle
 */
public class Hydra extends DynamicEntity {
    private double healthIncreaseRate;
    private double healthIncreaseAmnt;

    /**
     * Hydra Constructor
     * @param id
     * @param xy
     * @param config
     */
    public Hydra(String id, Position xy, SerializableJSONObject jsonConfig) {
        super(id, "hydra", xy);
        this.attack = jsonConfig.getDouble("hydra_attack");
        this.health = jsonConfig.getDouble("hydra_health");
        this.healthIncreaseRate = jsonConfig.getDouble("hydra_health_increase_rate");
        this.healthIncreaseAmnt = jsonConfig.getDouble("hydra_health_increase_amount");
    }

    /**
     * Updates Hydra Position
     * @param d
     * @param entities
     */
    public void updatePos(Direction d, List<Entity> entities) {
        RandomMovement move = new RandomMovement();
        Position nextPos = move.getNextPosition(this, entities);
        if (nextPos != null) {
            this.setPosition(nextPos);
        }
        
    }

    /**
     * Gets type
     * @return the type, i.e. "hydra"
     */
    @Override
    public String getType() {
        return "hydra";
    }
    
    /**
     * Returns the new health of the Hydra after every round of battle
     * initiated with the Player. 
     * Hydra's have a chance of increasing their health per round when in battle.
     * @param PlayerDamage
     * @return the new health
     */
    @Override
    public double newHealth(double PlayerDamage) {
        // If we hit the chance that a Hydra increases health
        double random = new Random().nextDouble();
        if (random < healthIncreaseRate) {
            return this.getHealth() + healthIncreaseAmnt;
        } else {
            System.out.println(random);
            return this.getHealth() - PlayerDamage;
        }
    }
    
}
