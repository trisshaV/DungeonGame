package dungeonmania.static_entity;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.Entity;
import dungeonmania.util.Position;

public class ActiveBomb extends StaticEntity{

    public ActiveBomb(String id, Position xy) {
        super(id, "bomb", xy);
    }

    @Override
    public boolean collide(Entity entity) {
        return false;
    }
    
}
