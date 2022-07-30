package dungeonmania.static_entity;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Entity;
import dungeonmania.SerializableJSONObject;
import dungeonmania.util.Position;

public class ActiveBomb extends StaticEntity{
    /**
     * ActiveBomb Constructor
     * @param id
     * @param xy
     */
    public ActiveBomb(String id, Position xy) {
        super(id, "bomb", xy);
    }
    
    /**
     * Collision detection
     * @param entity
     * @return boolean of collision status
     */
    @Override
    public boolean collide(Entity entity) {
        return false;
    }
    
    /**
     * Gets type
     * @return the type, i.e. "bomb"
     */
    @Override
    public String getType() {
        return "bomb";
    }

    /**
     * Explode the bomb
     * @param entities
     * @param config
     * @return update of current entities
     */
    public List<Entity> explode(List<Entity> entities, SerializableJSONObject config) {
        int radius = config.getInt("bomb_radius");
        List<Entity> toRemove = new ArrayList<>();
        toRemove.add(this);
        List<Position> copy = new ArrayList<>();
        Position a = getPosition();
        Position Top_Left = new Position(a.getX() - radius,a.getY() + radius);
        Position Pointer = Top_Left;

        for (int y = 0; y <= 2*radius; y++) {
            for (int x = 0; x <= 2*radius; x++) {
                copy.add(new Position(Pointer.getX(),Pointer.getY()));
                Pointer = new Position(Pointer.getX() + 1,Pointer.getY());
            }
            Pointer = new Position(Top_Left.getX(),Pointer.getY() - 1);
        }

        for (Position position : copy) {
            for (Entity entity : entities) {
                if (!entity.getType().equals("player") && copy.contains(entity.getPosition())) {
                    toRemove.add(entity);
                }
            }
        }
        return toRemove;
    }
}
