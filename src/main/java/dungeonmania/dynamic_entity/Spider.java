package dungeonmania.dynamic_entity;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/**
 * Are among the eneny entities that WILL harm Player. Has following properties:
 *      - Spawn at RANDOM locations ANYWHERE on the map at time of dungeon creation. However, it is RECOMMENDED that we 
 *          use a four co-ordinate box to spawn spiders.
 *      - Immediately on spawn, spiders move one square upwards and then later they "circle" clockwise one square at a time.
 *      - However, if there is a boulder in the spider's movement path, it then reverses the "circling" and then moves in other direction.
 *      - Walls, doors, switches, portals, exits all have no effect on them and hence they can simply traverse through them,
 *          ONLY boulders will effect the movement path and this is noted in previous point.
 */
public class Spider extends DynamicEntity {
    private String direction;

    private boolean cycleStart = false;

    private List<Position> cyclePositions;

    private int currentPosition;

    public Spider(String id, Position xy) {
        super(id, xy);
        this.direction = "clockwise";
        cyclePositions = generatePositions(this.getPosition());
    }

    public String getType() {
        return "spider";
    }
    public String getDirection() {
        return direction;
    }
    

    public void setDirection(String direction) {
        this.direction = direction;
    }
    
    public void updatePos(Direction d, List<Entity> l) {

        // boulder
        // call change Direction
        if (cycleStart == false) {
            Position curr = this.getPosition();
            Position nextPosition = new Position(curr.getX(), curr.getY() - 1);
            /* 
             Entity e = entity.stream().filter(x -> x.getPosition().equals(nextPosition)).filter(x -> x instanceof Boulder);
             if (e == null) {
                return;
             }
             */
             
            currentPosition = 0;
            this.setPosition(cyclePositions.get(currentPosition));
        }

        int result = checkCycle();

        currentPosition += result;

        if (currentPosition > 7) {
            currentPosition = 0;
        } else if (currentPosition < 0) {
            currentPosition = 7;
        }
        this.setPosition(cyclePositions.get(currentPosition));
    }
    
    private List<Position> generatePositions(Position centre) {
        List<Position> result = new ArrayList<Position>();
        int x = centre.getX();
        int y = centre.getY();
        // Add positions in a clockwise motion starting at top 
        result.add(new Position(x  , y-1));
        result.add(new Position(x+1, y-1));
        result.add(new Position(x+1, y));
        result.add(new Position(x+1, y+1));
        result.add(new Position(x  , y+1));
        result.add(new Position(x-1, y+1));
        result.add(new Position(x-1, y));
        result.add(new Position(x-1, y-1));
        return result;
    }

    private int checkCycle() {

        Position checkClockwise = null;
        Position checkAnticlockwise = null;

        if (currentPosition == 7) {
            checkClockwise = cyclePositions.get(0);
        } else {
            checkClockwise = cyclePositions.get(currentPosition + 1);
        }

        if (currentPosition == 0) {
            checkAnticlockwise = cyclePositions.get(7);
        } else {
            checkAnticlockwise = cyclePositions.get(currentPosition - 1);
        }

        /* 
        List <Entity> listEntities1 = entity.stream().filter(x -> x.getPosition().equals(checkClockwise)).collect(Collectors.toList());
        List <Entity> listEntities2 = entity.stream().filter(x -> x.getPosition().equals(checkAnticlockwise)).collect(Collectors.toList());

        listEntities1.stream().forEach(
            x -> {
                if (!x.collide(this) && !x.equals(null)) {
                    checkClockwise = null;
                    break;
                }
            }
        )
        listEntities2.stream().forEach(
            x -> {
                if (!x.collide(this) && !x.equals(null)) {
                    checkAnticlockwise = null;
                    break;
                }
            }
        )

        if (direction.equals("clockwise")) {
            if (checkClockwise.equals(null)) {
                if (!checkAnticlockwise.equals(null)) {
                    setDirection("anticlockwise");
                    return -1;
                } 
                return 0;
            } 
            return 1;
        } 
        if (checkAnticlockwise.equals(null)) {
            if (!checkClockwise.equals(null)) {
                setDirection("clockwise");
                return 1;
            }
            return 0;
        }
        */
        return -1;
    }
}
