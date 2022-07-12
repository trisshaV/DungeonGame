package dungeonmania.collectible;

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

    public InvisibilityPotion(String id, Position xy) {
        super(id, xy);
    }
    
}
