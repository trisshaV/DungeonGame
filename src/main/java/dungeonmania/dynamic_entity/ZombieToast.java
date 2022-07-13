package dungeonmania.dynamic_entity;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/**
 * Are among the enemy entities that WILL harm Player. Has following properties:
 *      - Spawn only at Zombie Toast spawners.
 *      - Move in RANDOM directions and are constricted to same conditions as Player movement, however CANNOT use portals.
 */
public class ZombieToast extends DynamicEntity {
    @Override
    public String getType() {
        return "zombie_toast";
    }

    public ZombieToast(String id, Position xy, JSONObject config) {
        super(id, xy);
        this.attack = config.getInt("player_attack");
        this.health = config.getInt("player_health");
    }

    public ZombieToast(String id, Position xy, int attack, int health) {
        super(id, xy);
        this.attack = attack;
        this.health = health;
    }
    
    public void updatePos(Direction d, List<Entity> l) {
        
        RandomMovement move = new RandomMovement();
        Position nextPosition = move.randPosition(this, l);
        if (nextPosition != null) {
            this.setPosition(nextPosition);
        }
    }

    
}
