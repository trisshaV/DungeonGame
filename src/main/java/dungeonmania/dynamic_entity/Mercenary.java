package dungeonmania.dynamic_entity;

import java.util.List;

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
    
    public Mercenary(String id, Position xy) {
        super(id, xy);
    }

    public void updatePos(Direction d, List<Entity> l) {
        if (status.equals("HOSTILE")) {
            // Check potion status
            /*
            if () {
                // do something
                randomHostile();
            } else {
                chaseHostile();
            }
            */
        } else if (status.equals("ALLY")) {
            moveAlly();
        }
    }
    
    private void chaseHostile() {
        // BFS
        // Ask for help
    }

    private void moveAlly() {
        // Check behind player
    }

    private void randomHostile() {

    }
}
