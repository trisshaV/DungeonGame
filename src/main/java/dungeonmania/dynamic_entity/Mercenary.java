package dungeonmania.dynamic_entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

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
    /**
     * Mercenary Constrcutor
     * @param id
     * @param xy
     * @param config
     */
    public Mercenary(String id, Position xy, JSONObject config) {
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
                randomHostile(l);
            } else if (p.getStatus().equals("INVINCIBLE")) {
                runAway(l);
            } else {
                chaseHostile(l);
            }
        }
    }
    
    /**
     * Chases
     * @param l
     */
    private void chaseHostile(List <Entity> l) {
        Entity p = l.stream().filter(x -> x instanceof Player).findFirst().orElse(null);
        Position playerPos = p.getPosition();
        int x1 = playerPos.getX();
        int y1 = playerPos.getY();

        Position current = this.getPosition();
        int x2 = current.getX();
        int y2 = current.getY();

        List <Position> testPositions = new ArrayList<>();
        
        // Find positions to use
        if (x1 > x2) {
            testPositions.add(new Position(x2 + 1, y2));
        } else if (x2 > x1) {
            testPositions.add(new Position(x2 - 1, y2));
        }

        if (y1 > y2) {
            testPositions.add(new Position(x2, y2 + 1));
        } else if (y2 > y1) {
            testPositions.add(new Position(x2, y2 - 1));
        }

        testPositions.stream().forEach(
            position -> {
                List <Entity> collides = l.stream().filter(entity -> entity.getPosition().equals(position)).collect(Collectors.toList());
                if (collides.stream().allMatch(entity -> entity.collide(this))) {
                    this.setPosition(position);
                    return;
                }
            }
        );
    }


    /**
     * Random movement of hostile
     * @param l
     */
    private void randomHostile(List<Entity> l) {
        RandomMovement move = new RandomMovement();
        Position nextPosition = move.randPosition(this, l);
        if (!nextPosition.equals(null)) {
            this.setPosition(nextPosition);
        }
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

    /**
     * Runs away from player
     * @param List <Entity> 
     */
    private void runAway(List <Entity> l) {
        Entity p = l.stream().filter(x -> x instanceof Player).findFirst().orElse(null);
        Position playerPos = p.getPosition();
        int x1 = playerPos.getX();
        int y1 = playerPos.getY();

        Position current = this.getPosition();
        int x2 = current.getX();
        int y2 = current.getY();


        List <Position> testPositions = new ArrayList<>();
        
        // Find positions to use
        if (x1 > x2) {
            testPositions.add(new Position(x2 - 1, y2));
        } else if (x2 > x1) {
            testPositions.add(new Position(x2 + 1, y2));
        }

        if (y1 > y2) {
            testPositions.add(new Position(x2, y2 - 1));
        } else if (y2 > y1) {
            testPositions.add(new Position(x2, y2 + 1));
        }

        testPositions.stream().forEach(
            position -> {
                List <Entity> collides = l.stream().filter(entity -> entity.getPosition().equals(position)).collect(Collectors.toList());
                if (collides.stream().allMatch(entity -> entity.collide(this))) {
                    this.setPosition(position);
                    return;
                }
            }
        );
    }
}
