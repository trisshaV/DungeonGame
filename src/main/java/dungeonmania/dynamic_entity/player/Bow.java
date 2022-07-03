package dungeonmania.dynamic_entity.player;

/**
 * Among the buildable entities and hence can be built by Player using a recipe. Has following properties:
 *      - RECIPE: 1 wood + 3 arrows
 *      - Has a specific durability limit and will deteriorate gradually after each battle. Once the limit is reached, 
 *          it is no longer useable.
 *      - Gives the Player DOUBLE damage in a single round. 
 *          NOTE: it only simulates ranged attacks by providing more damage, however it CANNOT be used at range.
 */
public class Bow extends Inventory {
    
}
