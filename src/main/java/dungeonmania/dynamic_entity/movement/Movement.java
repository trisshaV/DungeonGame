package dungeonmania.dynamic_entity.movement;

import dungeonmania.Entity;
import dungeonmania.util.Position;

import java.util.List;

public interface Movement {
    /**
     * Given an entity `d` and a reference to all entities, determine the next
     * position that `d` should occupy.
     * @param d - The subject entity. Never modified
     * @param l - All entities. Never modified.
     * @return a non-null position
     */
    Position getNextPosition(Entity d, List<Entity> l);
}
