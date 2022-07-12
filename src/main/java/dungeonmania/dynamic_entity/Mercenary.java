package dungeonmania.dynamic_entity;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;

import dungeonmania.Entity;
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
    private boolean behindPlayer = false;

    @Override
    public String getType() {
        return "mercenary";
    }
    
    public Mercenary(String id, Position xy, JSONObject config) {
        super(id, xy);
        this.attack = config.getInt("mercenary_attack");
        this.health = config.getInt("mercenary_health");

    }

    public void updatePos(Direction d, List<Entity> l) {
        if (status.equals("HOSTILE")) {
            // Check potion status
            /*
            if (Potion status is invisible) {
                randomHostile(l);
            } else if (Potion status is invunerable) {
                runHostile(l);
            } else {
                chaseHostile(l);
            }
            */
        }
    }
    
    private void chaseHostile(List <Entity> l) {
        Entity p = l.stream().filter(x -> x instanceof Player).findFirst().orElse(null);
        if (p == null) {
            // Player has deceased
            return;
        }
        Position playerPos = p.getPosition();
        int x1 = playerPos.getX();
        int y1 = playerPos.getY();

        Position current = this.getPosition();
        int x2 = current.getX();
        int y2 = current.getY();

        if (x1 > x2) {
            l.stream().filter(e -> e.getPosition().equals(new Position(x2 + 1, y2))).forEach(
                e -> {
                    /*
                    if (!e.collide(this) && !e.equals(null)) {
                        break;
                    }
                     */
                }
            );
        }
    }


    private void randomHostile(List<Entity> l) {
        RandomMovement move = new RandomMovement();
        Position nextPosition = move.randPosition(this, l);
        if (!nextPosition.equals(null)) {
            this.setPosition(nextPosition);
        }
    }
}
