package dungeonmania.collectible;

import dungeonmania.SerializableJSONObject;
import dungeonmania.util.Position;

/**
 * Are among the collectible entities and is stored as Player inventory. Has following properties:
 *      - Can be collected when Player moves onto the square it is on.
 *      - Used as a melee weapon during battles.
 *      - Damage dealt by the weapon is increased by an additive factor.
 *      - Has a specific durability limit and will deteriorate gradually after each battle. Once the limit is reached, 
 *          it is no longer useable.
 */
public class Sword extends Collectible {
    private int attack;
    private int durability;
    
    /**
     * Sword Constructor
     * @param id
     * @param xy
     * @param config
     */
    public Sword(String id, Position xy, SerializableJSONObject config) {
        super(id, "sword", xy);
        attack = config.getInt("sword_attack");
        durability = config.getInt("sword_durability");
    }

    /**
     * Get attack
     * @return the attack
     */
    public int getAtack() {
        return attack;
    }
    
    /**
     * Gets durability
     * @return the durability
     */
    public int getDurability() {
        return durability;
    }

    /**
     * Sets durability
     * @param num
     */
    public void setDurability(int num) {
        this.durability = num;
    }

    /**
     * Gets type
     * @return the type, i.e. "sword"
     */
    @Override
    public String getType() {
        return "sword";
    }
}
