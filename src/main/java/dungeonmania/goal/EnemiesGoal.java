package dungeonmania.goal;

import dungeonmania.Entity;

import java.util.List;

public class EnemiesGoal implements Goal{
    @Override
    public boolean isComplete(List<Entity> entities) {
        return false;
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
