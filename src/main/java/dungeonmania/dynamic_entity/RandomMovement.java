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
        Position nextPosition = null;
        // Repeat until a direction is successful
        /*
        while(true) {

            if (newPositions.size() == 0) {
                return null;
            }
            Random rand = new Random();
            int result = rand.nextInt(newPositions.size());
            Position nextPosition = newPositions.remove(result);
            List <Entity> temp = l.stream().filter(e -> e.getPosition().equals(nextPosition)).collect(Collectors.toList());

            temp.stream().forEach(
                x -> {
                    if (x.collide(d) || x.equals(null)) {
                        return nextPosition;
                    }
                }
            );
        */
        return null;
    }
}
