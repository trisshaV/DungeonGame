package dungeonmania.dynamic_entity;

import java.util.List;
import java.util.Random;

import dungeonmania.Entity;
import dungeonmania.SerializableJSONObject;
import dungeonmania.dynamic_entity.movement.ChaseMovement;
import dungeonmania.dynamic_entity.movement.FollowMovement;
import dungeonmania.dynamic_entity.movement.Movement;
import dungeonmania.dynamic_entity.movement.RandomMovement;
import dungeonmania.dynamic_entity.movement.RunAwayMovement;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


/**
 * Are among the enemy entities that will harm Player INITIALLY, however if bribed they will NOT. Has following properties:
 *      - Initialised at dungeon creation
 *      - Can be bribed if Player is within bribeRadius, and possesses enough coins
 *              - A bribe has a percentage (initialised at dungeon creation) of failing, where the Assassin will remain hostile, and coins
 *                will still be removed
 *      - They have two states, HOSTILE (intially) and ALLY (if bribed successfully by Player). 
 *                  - these follow the same movement details as a mercenary
 *      - Unlike a mercenary, when the player is INVISIBLE, if within the Assassins recon radius, the Assassin will continue 
 *        to chase after the player. If not within the recon radius, it will possess random movement.
 */

public class Assassin extends DynamicEntity{
    private String status = "HOSTILE";
    private int reconRadius;
    private int bribeRadius;
    private int bribeAmount;
    private double bribeFailRate;
    private Movement move;

    /**
     * Assassin Constructor
     * @param id
     * @param xy
     * @param config
     */
    public Assassin(String id, Position xy, SerializableJSONObject jsonConfig) {
        super(id, "assassin", xy);
        this.attack = jsonConfig.getDouble("assassin_attack");
        this.health = jsonConfig.getDouble("assassin_health");
        this.reconRadius = jsonConfig.getInt("assassin_recon_radius");
        this.bribeRadius = jsonConfig.getInt("bribe_radius");
        this.bribeAmount = jsonConfig.getInt("assassin_bribe_amount");
        this.bribeFailRate = jsonConfig.getDouble("assassin_bribe_fail_rate");
    }

    /**
     * Gets type
     * @return the type, i.e "mercenary"
     */
    @Override
    public String getType() {
        return "assassin";
    }

    /**
     * Gets status
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Assassin interact, allows player to bribe Assassin
     * Bribes have a certain chance of failing.
     * @param player
     */
    @Override
    public void interact (Player player) throws InvalidActionException {
        // Check the radius
        Position distance = Position.calculatePositionBetween(player.getPosition(), this.getPosition());
        double radius = Math.sqrt(Math.pow(distance.getX(), 2) + Math.pow(distance.getY(), 2));
        if (radius > bribeRadius) {
            throw new InvalidActionException("Too far from Assassin");
        }
        // check coins
        if (player.getCoins() < bribeAmount) {
            throw new InvalidActionException("Not enough coins");
        }
        // Checks probability that bribe will fail:
        double random = new Random().nextDouble();
        if (random < bribeFailRate) {
            player.removeCoins(bribeAmount);
        } else {
            player.removeCoins(bribeAmount);
            status = "FRIENDLY";
        }
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
    @Override
    public void updatePos(Direction d, List<Entity> l) {
        if (status.equals("HOSTILE")) {
            Player p = (Player)l.stream().filter(x -> x instanceof Player).findFirst().orElse(null);
            if (p.getStatus().equals("INVISIBLE")) {
                // Check for Radius
                // If player is within Recon Radius, Assassin will still chase after it
                Position distance = Position.calculatePositionBetween(p.getPosition(), this.getPosition());
                double radius = Math.sqrt(Math.pow(distance.getX(), 2) + Math.pow(distance.getY(), 2));
                if (radius > reconRadius) {
                    move = new RandomMovement();
                } else {
                    move = new ChaseMovement();
                }
            } else if (p.getStatus().equals("INVINCIBLE")) {
                move = new RunAwayMovement();
            } else {
                move = new ChaseMovement();
            }
        // If they are Allies, Assassins will follow the Player
        } else {
            move = new FollowMovement();
        }
        setPosition(move.getNextPosition(this, l));
    }

}
