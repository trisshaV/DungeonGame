package dungeonmania.goal;

import dungeonmania.Entity;
import dungeonmania.dynamic_entity.Player;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class EnemiesGoal implements Goal, Serializable {
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
        Optional<Player> maybePlayer = entities.stream()
            .filter(e -> e instanceof Player)
            .map(p -> (Player) p)
            .findFirst();
        if (maybePlayer.isEmpty())
            return false;
        return maybePlayer.get().getEnemiesDefeated() >= required &&
               entities.stream().noneMatch(e -> e.getType().contains("spawner"));
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
