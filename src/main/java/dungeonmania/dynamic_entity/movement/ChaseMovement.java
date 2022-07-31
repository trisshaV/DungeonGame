package dungeonmania.dynamic_entity.movement;

import dungeonmania.Entity;
import dungeonmania.dynamic_entity.Player;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChaseMovement implements Movement {
    @Override
    public Position getNextPosition(Entity d, List<Entity> l) {
        Position playerPos = l.stream().filter(x -> x instanceof Player)
                .map(Entity::getPosition)
                .findFirst().orElse(null);
        return ShortestPath.getNextPosition(d, playerPos, l);
    }
}
