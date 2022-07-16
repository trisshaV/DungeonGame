package dungeonmania.goal;

import dungeonmania.Entity;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SuperGoal implements Goal {
    private String relation = "";
    private List<Goal> goals = new ArrayList<>();
    public SuperGoal(JSONObject condition, JSONObject config) {
        String type = condition.getString("goal");
        switch (type) {
            case "exit":
                goals.add(new ExitGoal());
                break;
            case "boulders":
                goals.add(new BoulderGoal());
                break;
            case "treasure":
                goals.add(new TreasureGoal(config.getInt("treasure_goal")));
                break;
            case "AND": {
                JSONObject subgoal1 = condition.getJSONArray("subgoals").getJSONObject(0);
                JSONObject subgoal2 = condition.getJSONArray("subgoals").getJSONObject(1);
                goals.add(new SuperGoal(subgoal1, config));
                goals.add(new SuperGoal(subgoal2, config));
                relation = "AND";
                break;
            }
            case "OR": {
                JSONObject subgoal1 = condition.getJSONArray("subgoals").getJSONObject(0);
                JSONObject subgoal2 = condition.getJSONArray("subgoals").getJSONObject(1);
                goals.add(new SuperGoal(subgoal1, config));
                goals.add(new SuperGoal(subgoal2, config));
                relation = "OR";
                break;
            }
            default:
                break;
        }
    }
    @Override
    public boolean isComplete(List<Entity> entities) {
        if (relation.equals("AND")) {
            return goals.stream().allMatch(x -> x.isComplete(entities));
        }
        return goals.stream().anyMatch(x -> x.isComplete(entities));
    }

    @Override
    public String getGoal(List<Entity> entities) {
        // complete, show no remaining
        if (isComplete(entities)) {
            return "";
        } // composition and none out of 2 complete
        // stupid hack
        String starter = "";
        if (isExitGoal())
            starter = ":exit";

        if (!relation.equals("") && goals.stream().noneMatch(g -> g.isComplete(entities))) {
            return starter + "(" + goals.get(0).getGoal(entities) + " " +
                    relation + " " + goals.get(1).getGoal(entities) + ")";
        }
        // one goal is not complete, show single goal
        return goals.stream()
                .filter(g -> !g.isComplete(entities))
                .map(g -> g.getGoal(entities))
                .findFirst()
                .orElse("") + starter;
    }

    @Override
    public boolean isExitGoal() {
        return goals.stream().anyMatch(Goal::isExitGoal);
    }
}
