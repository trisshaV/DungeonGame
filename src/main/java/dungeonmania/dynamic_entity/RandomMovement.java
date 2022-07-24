package dungeonmania.dynamic_entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class RandomMovement {
    /**
     * Random posiiton
     * @param d - entity that wants to move
     * @param l - all entities
     * @return Valid new position. Does not return null.
     */
    public Position randPosition(Entity d, List<Entity> l) {
        Position middle = d.getPosition();
        List <Position> newPositions = Arrays.asList(
            middle.translateBy(Direction.DOWN),
            middle.translateBy(Direction.UP),
            middle.translateBy(Direction.LEFT),
            middle.translateBy(Direction.RIGHT));

        // Repeat until all directions are exhausted
        while (newPositions.size() > 0) {
            Random rand = new Random();
            int result = rand.nextInt(newPositions.size());
            Position nextPosition = newPositions.remove(result);
            if (l.stream()
                 .filter(e -> e.getPosition().equals(nextPosition))
                 .noneMatch(entity -> !entity.collide(d))) 
            {
                return nextPosition;   
            }
        }
        // otherwise no change
        return middle;
    }
}
