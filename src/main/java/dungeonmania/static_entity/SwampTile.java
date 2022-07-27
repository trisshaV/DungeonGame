package dungeonmania.static_entity;

import dungeonmania.Entity;
import dungeonmania.dynamic_entity.DynamicEntity;
import dungeonmania.util.Position;

public class SwampTile extends StaticEntity{
    private int movementFactor;
    private int movementTick;
    Entity stuck;

    public SwampTile(String id, Position xy) {
        super(id, "swamp_tile", xy);
        
    }

    public SwampTile(String id, Position xy, int movementFactor) {
        super(id, "swamp_tile", xy);
        this.movementFactor = movementFactor;
        movementTick = 0;
        stuck = null;
    }
    @Override
    public int moveCost() {
        return movementFactor;
    }
    
    @Override
    public boolean collide(Entity entity) {
        if (entity instanceof DynamicEntity && !entity.getType().equals("player")) {
            stuck = entity;
        }
        return true;
    }

    @Override
    public String getType() {
        return "swamp_tile";
    }

    public void tick() {
        System.out.print("hi");
        if (movementTick == movementFactor) {
            stuck = null;
        }
        else if (!stuck.getPosition().equals(getPosition())) {
            stuck.setPosition(getPosition());
            movementTick += 1;
        }
    }
    
}
