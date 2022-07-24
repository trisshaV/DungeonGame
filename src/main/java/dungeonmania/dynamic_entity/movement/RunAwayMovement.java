package dungeonmania.dynamic_entity.movement;

import dungeonmania.Entity;
import dungeonmania.dynamic_entity.Player;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RunAwayMovement implements Movement {
    @Override
    public Position getNextPosition(Entity d, List<Entity> l) {
        Entity p = l.stream().filter(x -> x instanceof Player).findFirst().orElse(null);
        Position playerPos = p.getPosition();
        int x1 = playerPos.getX();
        int y1 = playerPos.getY();

        Position current = d.getPosition();
        int x2 = current.getX();
        int y2 = current.getY();

        List<Position> testPositions = new ArrayList<>();

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
        while (testPositions.size() > 0) {
            Position possible = testPositions.remove(0);
            if (l.stream()
                .filter(entity -> entity.getPosition().equals(possible))
                .allMatch(e -> e.collide(d))) {
                return possible;
            }
        }
        return current;
    }
}
