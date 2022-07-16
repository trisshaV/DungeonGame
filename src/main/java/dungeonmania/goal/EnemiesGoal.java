package dungeonmania.goal;

import dungeonmania.Entity;
import dungeonmania.dynamic_entity.Player;

import java.util.List;

public class EnemiesGoal implements Goal {
    private final int required;

    public EnemiesGoal(int required) {
        this.required = required;
    }
    @Override
    public boolean isComplete(List<Entity> entities) {
        Player p = (Player) entities.stream().filter(e -> e instanceof Player).findFirst().get();
        return p.getEnemiesDefeated() >= required &&
               entities.stream().anyMatch(e -> e.getType().contains("spawner"));
    }

    @Override
    public String getGoal(List<Entity> entities) {
        return ":enemies";
    }

    @Override
    public boolean isExitGoal() {
        return false;
    }
}
