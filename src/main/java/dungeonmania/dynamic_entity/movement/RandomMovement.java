package dungeonmania.dynamic_entity.movement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dungeonmania.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class RandomMovement implements Movement, Serializable {
    /**
     * Random posiiton
     * @param d - entity that wants to move
     * @param l - all entities
     * @return Valid new position. Does not return null.
     */
    public Position getNextPosition(Entity d, List<Entity> l) {
        Position middle = d.getPosition();
        List <Position> newPositions = new ArrayList<>(List.of(
            middle.translateBy(Direction.DOWN),
            middle.translateBy(Direction.UP),
            middle.translateBy(Direction.LEFT),
            middle.translateBy(Direction.RIGHT)));

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
