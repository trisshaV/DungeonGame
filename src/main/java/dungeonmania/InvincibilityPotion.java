package dungeonmania;

/**
 * Are among the collectible entities and is stored as Player inventory. Has following properties:
 *      - Can be collected when Player moves onto the square it is on.
 *      - Can be consumed at any time and effects only last for a limited time.
 *      - Any battles that occur whilst the Player has the effects of the potion is immediately won as soon as the end of the first round.
 *      - Whilst Player has effects of potion, mercenaries (NOT bribed) and zombie toasts will run away from Player, 
 *          HOWEVER spiders and mercenaries (bribed) will remain UNAFFECTED.
 */
public class InvincibilityPotion extends Collectible {
    
}
