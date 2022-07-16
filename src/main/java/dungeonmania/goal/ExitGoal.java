package dungeonmania.goal;

import java.util.List;
import java.util.Optional;

import dungeonmania.Entity;
import dungeonmania.dynamic_entity.Player;
import dungeonmania.static_entity.Exit;
import dungeonmania.util.Position;

public class ExitGoal implements Goal {

    @Override
    public boolean isComplete(List<Entity> entities) { 
        Optional<Entity> maybe_player =  entities.stream()
            .filter(p -> p instanceof Player)
            .findFirst();
        if (maybe_player.isEmpty()) {
            return false;
        }
        Position player_xy = maybe_player.get().getPosition();
        return entities.stream()
            .filter(e -> e.getPosition().equals(player_xy))
            .anyMatch(e -> e instanceof Exit);
    }

    @Override
    public String getGoal(List<Entity> entities) {
        return ":exit";
    }

    @Override
    public boolean isExitGoal() {
        return true;
    }

}
