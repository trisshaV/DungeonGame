package dungeonmania.goal;

import dungeonmania.Entity;
import dungeonmania.dynamic_entity.Player;

import java.util.List;

public class TreasureGoal implements Goal {
    private int treasure_required;
    public TreasureGoal(int treasure_required) {
        this.treasure_required = treasure_required;
    }
    @Override
    public boolean isComplete(List<Entity> entities) {
        return entities.stream()
                .filter(e -> e instanceof Player)
                .anyMatch(e -> {
                    Player p = (Player) e;
                    return p.totalTreasureCollected() >= treasure_required;
                });
    }

    @Override
    public String getGoal(List<Entity> entities) {
        return isComplete(entities) ? "" : ":treasure";
    }
}
