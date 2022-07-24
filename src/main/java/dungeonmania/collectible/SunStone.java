package dungeonmania.collectible;

import dungeonmania.SerializableJSONObject;
import dungeonmania.util.Position;

public class SunStone extends Collectible {

    /**
     * Sun stone Constructor
     * @param id
     * @param xy
     * @param config
     */
    public SunStone(String id, Position xy, SerializableJSONObject config) {
        super(id, "sun_stone", xy);
    }

    /**
     * Gets type
     * @return the type, i.e. "sun_stone"
     */
    @Override
    public String getType() {
        return "sun_stone";
    }
    
}
