package dungeonmania.goal;

import dungeonmania.Entity;
import dungeonmania.dynamic_entity.Player;

import java.util.List;

public class EnemiesGoal implements Goal {
    private final int required;

    /**
     * EmemiesGoal Constructor
     * @param required
     */
    public EnemiesGoal(int required) {
        this.required = required;
    }

    /**
     * Checks completed
     * @param entities
     * @return boolean confirmation of completion
     */
    @Override
    public boolean isComplete(List<Entity> entities) {
        Player p = (Player) entities.stream().filter(e -> e instanceof Player).findFirst().get();
        return p.getEnemiesDefeated() >= required &&
               entities.stream().anyMatch(e -> e.getType().contains("spawner"));
    }

    /**
     * Gets goals
     * @return the goal, i.e. ":enemies"
     */
    @Override
    public String getGoal(List<Entity> entities) {
        return ":enemies";
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
