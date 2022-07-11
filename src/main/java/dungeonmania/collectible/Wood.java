package dungeonmania.collectible;

import dungeonmania.util.Position;

/**
 * Are among the collectible entities and is stored as Player inventory. Has following properties:
 *      - Can be collected when Player moves onto the square it is on.
 *      - Used in recipes to create buildable entities.
 */
public class Wood extends Collectible {

    public Wood(String id, Position xy) {
        super(id, xy);
    }
    
}
