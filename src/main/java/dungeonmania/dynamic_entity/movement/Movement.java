package dungeonmania.dynamic_entity.movement;

import dungeonmania.Entity;
import dungeonmania.util.Position;

import java.util.List;

public interface Movement {
    Position getNextPosition(Entity d, List<Entity> l);
}
