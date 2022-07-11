package dungeonmania.dynamic_entity.player;

import dungeonmania.collectible.Collectible;
import dungeonmania.util.Position;

/**
 * Among the buildable entities and hence can be built by Player using a recipe. Has following properties:
 *      - RECIPE: 2 wood + (1 treasure OR 1 key)
 *      - Has a specific durability limit and will deteriorate gradually after each battle. Once the limit is reached, 
 *          it is no longer useable.
 *      - Decreases the effects of enemy attacks. 
 */
public class Shield extends Collectible implements Buildable {

    public Shield(String id, Position xy) {
        super(id, xy);
    }
    
}
