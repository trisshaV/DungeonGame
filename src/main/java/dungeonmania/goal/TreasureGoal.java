package dungeonmania.goal;

import dungeonmania.Entity;
import dungeonmania.dynamic_entity.Player;

import java.util.List;

public class TreasureGoal implements Goal {
    private final int treasure_required;

    /**
     * TreasureGoal Constructor
     * @param treasure_required
     */
    public TreasureGoal(int treasure_required) {
        this.treasure_required = treasure_required;
    }
    
    /**
     * Checks completed
     * @param entities
     * @return boolean confirmation of completion
     */
    @Override
    public boolean isComplete(List<Entity> entities) {
        return entities.stream()
                .filter(e -> e instanceof Player)
                .anyMatch(e -> {
                    Player p = (Player) e;
                    return p.totalTreasureCollected() >= treasure_required;
                });
    }

    /**
     * Gets goals
     * @return the goals, i.e. ":treasure"
     */
    @Override
    public String getGoal(List<Entity> entities) {
        return isComplete(entities) ? "" : ":treasure";
    }

    /**
     * Status of exit goal
     * @return boolean of exit goal status
     */
    @Override
    public boolean isExitGoal() {
        return false;
    }
}
