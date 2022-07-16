package dungeonmania.goal;

import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.Boulder;
import dungeonmania.Entity;
import dungeonmania.static_entity.FloorSwitch;

public class BoulderGoal implements Goal {

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

    @Override
    public String getGoal(List<Entity> entities) {
        return ":boulders";
    }

    @Override
    public boolean isExitGoal() {
        return false;
    }

}
