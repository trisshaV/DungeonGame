package dungeonmania.dynamic_entity.movement;

import dungeonmania.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.*;
import java.util.stream.Collectors;

public class ShortestPath {
    public static Position getNextPosition(Entity start, Position dest, List<Entity> entities) {
        Position source = start.getPosition();
        if (source.equals(dest) || Position.isAdjacent(source, dest)) {
            return dest;
        }
        Map<String, Integer> box = bounds(entities);

        // default to infinity (Integer.MAX_VALUE)
        Map<Position, Integer> dist = new HashMap<>();
        dist.put(source, 0);

        // default to null
        Map<Position, Position> prev = new HashMap<>();

        Map<Position, List<Entity>> map = getMap(entities);

        Set<Position> visited = new HashSet<>();
        List<Position> queue = new ArrayList<>();
        queue.add(source);
        while (!queue.isEmpty()) {
            Position u = queue.remove(0);
            List<Position> adjacentPos = adjacent(u);
            for (Position v: adjacentPos) {
                boolean isValid = valid(map, start, v);
                if (isValid && dist.get(u) + cost(map, v) < dist.getOrDefault(v, Integer.MAX_VALUE)) {
                    dist.put(v, dist.get(u)+ cost(map, v));
                    prev.put(v, u);
                }
                if (isValid && inBounds(box, v) && !queue.contains(v) && !visited.contains(v)) {
                    queue.add(v);
                }
            }
            visited.add(u);
        }
        return traceBack(prev, source, dest);
    }

    private static Position traceBack(Map<Position, Position> prev, Position source, Position dest) {
        Position curr = dest;
        Position next = prev.getOrDefault(curr, null);
        while (next != null && !next.equals(source)) {
            curr = next;
            next = prev.get(curr);
        }
        return (next == null) ? source : curr;
    }

    /**
     * Convert list of entities to hashmap of positions.
     * Multiple entities can be present on one position, so get(...) returns a list
     */
    private static Map<Position, List<Entity>> getMap(List<Entity> entities) {
        Map<Position, List<Entity>> map = new HashMap<>();
        entities.forEach((e) -> {
            Position xy = e.getPosition();
            map.putIfAbsent(xy, new ArrayList<>());
            map.get(xy).add(e);
        });
        return map;
    }

    private static boolean inBounds(Map<String, Integer> bounds, Position xy) {
        return  (xy.getX() <= bounds.get("right") && xy.getX() >= bounds.get("left")) &&
                (xy.getY() <= bounds.get("bottom") && xy.getY() >= bounds.get("top"));
    }

    /** 
     * returns true if no collision
     */
    private static boolean valid(Map<Position, List<Entity>> map, Entity entity, Position xy) {
        return map.getOrDefault(xy, new ArrayList<>())
            .stream()
            .allMatch(e -> e.collide(entity));
    }

    private static int cost(Map<Position, List<Entity>> map, Position xy) {
        return 1 + map.getOrDefault(xy, new ArrayList<>())
                    .stream()
                    .map(Entity::moveCost)
                    .reduce(0, Integer::max);
    }

    private static List<Position> adjacent(Position xy) {
        return new ArrayList<>(List.of(
            xy.translateBy(Direction.LEFT),
            xy.translateBy(Direction.RIGHT),
            xy.translateBy(Direction.UP),
            xy.translateBy(Direction.DOWN)
        ));
    }

    private static Map<String, Integer> bounds(List<Entity> entities) {
        List<Position> positions = entities.stream().map(Entity::getPosition).collect(Collectors.toList());
        Map<String, Integer> m = new HashMap<>();
        Position leftMost = positions.stream()
                .reduce(new Position(0, 0), (a, b) -> a.getX() < b.getX() ? a : b);
        Position rightMost = positions.stream()
                .reduce(new Position(0, 0), (a, b) -> a.getX() > b.getX() ? a : b);
        Position topMost = positions.stream()
                .reduce(new Position(0, 0), (a, b) -> a.getY() < b.getY() ? a : b);
        Position bottomMost = positions.stream()
                .reduce(new Position(0, 0), (a, b) -> a.getY() > b.getY() ? a : b);
        m.put("left",   leftMost.getX() - 1);
        m.put("right",  rightMost.getX() + 1);
        m.put("top",    topMost.getY() - 1);
        m.put("bottom", bottomMost.getY() + 1);
        return m;
    }
}
