package dungeonmania.dynamic_entity;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Hydra extends DynamicEntity {
    private double healthIncreaseRate;
    private double healthIncreaseAmnt;

    public Hydra(String id, Position xy, JSONObject config) {
        super(id, "hydra", xy);
        this.attack = config.getDouble("hydra_attack");
        this.health = config.getDouble("hydra_health");
        this.healthIncreaseRate = config.getDouble("hydra_health_increase_rate");
        this.healthIncreaseAmnt = config.getDouble("hydra_health_increase_amount");
    }

    public void updatePos(Direction d, List<Entity> entities) {
        RandomMovement move = new RandomMovement();
        Position nextPos = move.randPosition(this, entities);
        if (nextPos != null) {
            this.setPosition(nextPos);
        }
        
    }

    @Override
    public String getType() {
        return "hydra";
    }
    
    
}
