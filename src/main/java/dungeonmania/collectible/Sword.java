package dungeonmania.collectible;

import org.json.JSONObject;

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
    
    public Sword(String id, Position xy, JSONObject config) {
        super(id, "sword", xy);
        attack = config.getInt("sword_attack");
        durability = config.getInt("sword_durability");
    }

    public int getAtack() {
        return attack;
    }
    
    public int getDurability() {
        return durability;
    }

    public void setDurability(int num) {
        this.durability = num;
    }

    @Override
    public void use() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getType() {
        return "sword";
    }
}
