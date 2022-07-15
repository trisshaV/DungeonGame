package dungeonmania.dynamic_entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.Entity;
import dungeonmania.util.Position;

public class RandomMovement {

    public RandomMovement() {
    }
    
    public Position randPosition(Entity d, List<Entity> l) {
        Position middle = d.getPosition();
        int x = middle.getX();
        int y = middle.getY();
        List <Position> newPositions = new ArrayList<>();
        newPositions.add(new Position(x, y - 1));
        newPositions.add(new Position(x, y + 1));
        newPositions.add(new Position(x + 1, y));
        newPositions.add(new Position(x - 1, y));
        // Repeat until a direction is successful
        while(true) {
            if (newPositions.size() == 0) {
                break;
            } 
            Random rand = new Random();
            int result = rand.nextInt(newPositions.size());

            List <Entity> temp = l.stream().filter(e -> e.getPosition().equals(newPositions.get(result))).collect(Collectors.toList());
            Position nextPosition = newPositions.remove(result);
            if (temp.size() == 0) {
                return nextPosition;
            } else if (temp.stream().anyMatch(entity -> !entity.collide(d) && !entity.equals(null) == true) == false) {
                return nextPosition;
            }
        }
        return null;
    }
}
