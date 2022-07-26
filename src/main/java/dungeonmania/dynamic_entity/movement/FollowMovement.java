package dungeonmania.dynamic_entity.movement;

import dungeonmania.Entity;
import dungeonmania.util.Position;

import java.util.List;

public class FollowMovement implements Movement {
    // TODO
    @Override
    public Position getNextPosition(Entity d, List<Entity> l) {
        return d.getPosition();
    }
}
