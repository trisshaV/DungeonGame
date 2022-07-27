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
        List<Position> all = allPositions(box);

        Map<Position, Integer> dist = new HashMap<>();
        all.stream().forEach(pos -> dist.put(pos, Integer.MAX_VALUE));
        dist.put(source, 0);

        Map<Position, Position> prev = new HashMap<>();
        all.stream().forEach(pos -> prev.put(pos, null));

        Map<Position, List<Entity>> map = new HashMap<>();
        for (Entity e : entities) {
            Position xy = e.getPosition();
            map.putIfAbsent(xy, new ArrayList<>());
            map.get(xy).add(e);
        }

        Set<Position> visited = new HashSet<>();
        Queue<Position> queue = new PriorityQueue<>();
        queue.add(source);

        while (!queue.isEmpty()) {
            Position u = queue.remove();
            for (Position v: adjacent(u)) {
                if (!valid(map, start) && dist.get(u) + cost(map, v) < dist.get(v)) {
                    dist.put(v, dist.get(u) + cost(map, v));
                    prev.put(v, u);
                }
                if (!queue.contains(v) && !visited.contains(v)) {
                    queue.add(v);
                }
            }
            visited.add(u);
        }

        return traceBack(prev, source, dest);
    }

    private static Position traceBack(Map<Position, Position> prev, Position source, Position dest) {
        Position next = prev.get(dest);
        while (next != null && !next.equals(source)) {
            next = prev.get(next);
        }
        return (next == null) ? source : next;
    }

    /** 
     * returns true if no collision
     */
    private static boolean valid(Map<Position, List<Entity>> map, Entity entity) {
        return map.getOrDefault(entity.getPosition(), new ArrayList<>())
            .stream()
            .noneMatch(e -> e.collide(entity));
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

    private static List<Position> allPositions(Map<String, Integer> bounds) {
        List<Position> all = new ArrayList<>();
        int lower_x = bounds.get("left");
        int upper_x = bounds.get("right");
        int lower_y = bounds.get("top");
        int upper_y = bounds.get("bottom");
        for (int x = lower_x; x < upper_x; x++) {
            for (int y = lower_y; y < upper_y; y++) {
                all.add(new Position(x, y));
            }
        }
        return all;
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
