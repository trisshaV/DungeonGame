package dungeonmania.goal;

import java.util.List;

import dungeonmania.Entity;

public interface Goal {
    public boolean isComplete(List<Entity> entities);

    /*
     * Returns the goal if it has NOT been completed, otherwise ""
     */
    public String getGoal(List<Entity> entities);
}
