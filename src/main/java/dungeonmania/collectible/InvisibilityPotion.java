package dungeonmania.collectible;

import dungeonmania.SerializableJSONObject;
import dungeonmania.util.Position;

/**
 * Are among the collectible entities and is stored as Player inventory. Has following properties:
 *      - Can be collected when Player moves onto the square it is on.
 *      - Can be consumed at any time and effects only last for a limited time.
 *      - Whilst Player has effects of potion, can move past all other entities undetected and no battles will occur. Consequently, 
 *          this also makes any mercenaries (bribed) change their movement pattern to that of a zombie toast 
 *          as they too cannot see the Player.
 */
public class InvisibilityPotion extends Collectible {
    private int duration;

    /**
     * InvisibilityPotion Constructor
     * @param id
     * @param xy
     * @param config
     */
    public InvisibilityPotion(String id, Position xy, SerializableJSONObject config) {
        super(id, "invisibility_potion", xy);
        duration = config.getInt("invisibility_potion_duration");
    }

    /**
     * Potency of potion effects
     * @return expiration  of potion
     */
    public boolean potency() {
        if (duration != 0) {
            duration = duration - 1;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Gets type
     * @return the type, i.e. "invisibility_potion"
     */
    @Override
    public String getType() {
        return "invisibility_potion";
    }
}
