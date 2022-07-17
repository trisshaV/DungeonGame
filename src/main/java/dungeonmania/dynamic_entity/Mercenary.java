package dungeonmania.dynamic_entity;

import java.util.List;
import java.util.stream.Collectors;

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

        // Two possible positions for Mercenary to move to
        if (x1 > x2) {
            x2 += 1;
        } else if (x2 > x1) {
            x2 -= 1;
        }

        if (y1 > y2) {
            y2 += 1;
        } else if (y2 > y1) {
            y2 -= 1;
        }

        Position nextPosition1 = new Position(x2, current.getY());
        Position nextPosition2 = new Position(current.getX(), y2);

        boolean checkPos2 = true;
        if (x2 != current.getX()) {
            List <Entity> collides = l.stream().filter(entity -> entity.getPosition().equals(nextPosition1)).collect(Collectors.toList());

            if (collides.size() == 0) {
                this.setPosition(nextPosition1);
                checkPos2 = false;
            } else if (collides.stream().filter(entity -> (entity.equals(null) || entity.collide(this)) == true).collect(Collectors.toList()).size() != 0) {
                this.setPosition(nextPosition1);
                checkPos2 = false;
            }
        }
        if (checkPos2 == true && y2 != current.getY()) {
            List <Entity> collides = l.stream().filter(entity -> entity.getPosition().equals(nextPosition2)).collect(Collectors.toList());
            if (collides.size() == 0) {
                this.setPosition(nextPosition2);
                return;
            } else if (collides.stream().filter(entity -> (entity.equals(null) || entity.collide(this)) == true).collect(Collectors.toList()).size() != 0) {
                this.setPosition(nextPosition2);
                return;
            }
        }
        return;
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

        // Two possible positions for Mercenary to move to
        if (x1 > x2) {
            x2 -= 1;
        } else if (x2 > x1) {
            x2 += 1;
        }

        if (y1 > y2) {
            y2 -= 1;
        } else if (y2 > y1) {
            y2 += 1;
        }

        Position nextPosition1 = new Position(x2, current.getY());
        Position nextPosition2 = new Position(current.getX(), y2);

        boolean checkPos2 = true;
        if (x2 != current.getX()) {
            List <Entity> collides = l.stream().filter(entity -> entity.getPosition().equals(nextPosition1)).collect(Collectors.toList());

            if (collides.size() == 0) {
                this.setPosition(nextPosition1);
                checkPos2 = false;
            } else if (collides.stream().filter(entity -> (entity.equals(null) || entity.collide(this)) == true).collect(Collectors.toList()).size() != 0) {
                this.setPosition(nextPosition1);
                checkPos2 = false;
            }
        }
        if (checkPos2 == true && y2 != current.getY()) {
            List <Entity> collides = l.stream().filter(entity -> entity.getPosition().equals(nextPosition2)).collect(Collectors.toList());
            if (collides.size() == 0) {
                this.setPosition(nextPosition2);
                return;
            } else if (collides.stream().filter(entity -> (entity.equals(null) || entity.collide(this)) == true).collect(Collectors.toList()).size() != 0) {
                this.setPosition(nextPosition2);
                return;
            }
        }
        return;
    }
}
