package dungeonmania.dynamic_entity;

import java.util.ArrayList;
import java.util.List;

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

    public String getDirection() {
        return direction;
    }
    

    public void setDirection(String direction) {
        this.direction = direction;
    }

    private void changeDirection() {
        if (this.direction == "clockwise") {
            this.direction = "anticlockwise";
        } else {
            this.direction = "clockwise";
        }
    }
    
    private Position updatePos() {

        // boulder
        // call change Direction
        if (cycleStart == false) {
            // Check for boulder

            currentPosition = 0;
            return cyclePositions.get(currentPosition);
        }

        int result = checkBoulders();

        currentPosition += result;

        if (currentPosition > 7) {
            currentPosition = 0;
        } else if (currentPosition < 0) {
            currentPosition = 7;
        }
        return cyclePositions.get(currentPosition);
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

    private int checkBoulders() {

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

        // Check if boulder
        /* 
        if (check boulder (checkPos)) {

            //reverse direction
            //
        }
         */
        return 1;
    }
}
