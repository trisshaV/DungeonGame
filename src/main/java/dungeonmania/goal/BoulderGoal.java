package dungeonmania.goal;

import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.Boulder;
import dungeonmania.Entity;
import dungeonmania.static_entity.FloorSwitch;

public class BoulderGoal implements Goal {

    /**
     * Checks completed
     * @param entities
     * @return boolean confirmation of completion
     */
    @Override
    public boolean isComplete(List<Entity> entities) {
        List<Entity> boulders = entities.stream()
            .filter(e -> e instanceof Boulder)
            .collect(Collectors.toList());
        List<Entity> switches = entities.stream()
            .filter(e -> e instanceof FloorSwitch)
            .collect(Collectors.toList());
        
        // all switches have a boulder 
        return switches.stream()
            .map(Entity::getPosition)
            .allMatch((sw_xy) -> {
                return boulders.stream()
                    .map(Entity::getPosition)
                    .anyMatch(sw_xy::equals);
            });
    }

    /**
     * Gets goals
     * @return the goals, i.e. ":boulders"
     */
    @Override
    public String getGoal(List<Entity> entities) {
        return ":boulders";
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
