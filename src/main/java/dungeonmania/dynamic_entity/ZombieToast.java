package dungeonmania.dynamic_entity;

import java.util.List;

import dungeonmania.dynamic_entity.movement.Movement;
import dungeonmania.dynamic_entity.movement.RandomMovement;
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
    
    /**
     * ZombieToast Constructor
     * @param id
     * @param xy
     * @param config
     */
    private static Movement move = new RandomMovement();
    public ZombieToast(String id, Position xy, JSONObject config) {
        super(id, "zombie_toast", xy);
        this.attack = config.getDouble("zombie_attack");
        this.health = config.getDouble("zombie_health");
    }

    /**
     * ZombieToast Constructor
     * @param id
     * @param xy
     * @param attack
     * @param health
     */
    public ZombieToast(String id, Position xy, int attack, int health) {
        super(id,"zombie_toast", xy);
        this.attack = attack;
        this.health = health;
    }
    
    /**
     * Updates Position
     * @param d
     * @param l
     */
    public void updatePos(Direction d, List<Entity> l) {
        Position nextPosition = move.getNextPosition(this, l);
        this.setPosition(nextPosition);
    }

    /**
     * Gets type
     * @return the type, i.e. "zombie_toast"
     */
    @Override
    public String getType() {
        return "zombie_toast";
    }
}
