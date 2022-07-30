package dungeonmania.dynamic_entity;

import java.util.List;

import dungeonmania.SerializableJSONObject;
import dungeonmania.dynamic_entity.movement.*;
import dungeonmania.Entity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/**
 * Are among the enemy entities that will harm Player INITIALLY, however if bribed they will NOT. Has following properties:
 *      - Are NOT spawned and must be initialised at time of dungeon creation.
 *      - Player can bribe them if they have enough treasure (gold) and are within certain radius of the Player and then at which 
 *          they become allies and assist Player.
 *      - They have two states, HOSTILE (intially) and ALLY (if bribed successfully by Player). 
 *      - Each states have different movement and they are:
 *          -- HOSTILE: Continously moves towards Player until they cannot move any closer and 
 *                          are constricted to same conditions as Player movement.
 *          -- ALLY: Follows the Player around and once they are next to Player they continually follow the Player around
 *                          occuping the previous square the Player was on and are constricted to same conditions as Player movement.
 */
public class Mercenary extends DynamicEntity {

    private String status = "HOSTILE";
    private int bribeRadius;
    private int bribeAmount;
    private Movement move;
    /**
     * Mercenary Constrcutor
     * @param id
     * @param xy
     * @param config
     */
    public Mercenary(String id, Position xy, SerializableJSONObject config) {
        super(id, "mercenary", xy);
        this.attack = config.getDouble("mercenary_attack");
        this.health = config.getDouble("mercenary_health");
        this.bribeRadius = config.getInt("bribe_radius");
        this.bribeAmount = config.getInt("bribe_amount");
    }

    /**
     * Mercenary interact, allows player to bribe mercenary
     * @param player
     */
    @Override
    public void interact(Player player) throws InvalidActionException {
        // check radius
        Position distance = Position.calculatePositionBetween(player.getPosition(), this.getPosition());
        double radius = Math.sqrt(Math.pow(distance.getX(), 2) + Math.pow(distance.getY(), 2));
        if (radius > bribeRadius) {
            throw new InvalidActionException("Too far from mercenary");
        }
        // check coins
        if (player.getCoins() < bribeAmount) {
            throw new InvalidActionException("Not enough coins");
        }
        // set status to friendly 
        status = "FRIENDLY";
        // remove players coins
        player.removeCoins(bribeAmount);
    }

    @Override
    public EntityResponse getEntityResponse() {
        if (status.equals("FRIENDLY")) {
            return new EntityResponse(getId(), getType(), getPosition(), false);
        }
        return new EntityResponse(getId(), getType(), getPosition(), true);
    }
    /**
     * Updates position
     * @param d
     * @param l
     */
    public void updatePos(Direction d, List<Entity> l) {
        if (status.equals("HOSTILE")) {
            Player p = (Player)l.stream().filter(x -> x instanceof Player).findFirst().orElse(null);
            if (p.getStatus().equals("INVISIBLE")) {
                move = new RandomMovement();
            } else if (p.getStatus().equals("INVINCIBLE")) {
                move = new RunAwayMovement();
            } else {
                move = new ChaseMovement();
            }
        } else {
            move = new FollowMovement();
        }
        setPosition(move.getNextPosition(this, l));
    }

    /**
     * Gets status
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Gets type
     * @return the type, i.e "mercenary"
     */
    @Override
    public String getType() {
        return "mercenary";
    }
}
