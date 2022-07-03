package dungeonmania.collectible;

/**
 * An interface for the consumables (invincibility and invisibility potions). Has following properties:
 *      - Player can consume potions of other types whilst under the influence of a differing potion. If this occurs,
 *          potions are put into a QUEUE.
 *      - The QUEUE, will take into consideration the amount of TICKS each potion lasts for and will then administer 
 *          the QUEUED potions to the Player accordingly.
 */
public interface Consumable {
    
}
