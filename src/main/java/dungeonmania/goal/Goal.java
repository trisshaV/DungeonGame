package dungeonmania.goal;

import java.util.List;

import dungeonmania.Entity;

public interface Goal {
    boolean isComplete(List<Entity> entities);

    /*
     * Returns the goal if it has NOT been completed, otherwise ""
     */
    String getGoal(List<Entity> entities);

    boolean isExitGoal();
}