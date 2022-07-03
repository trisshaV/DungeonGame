package dungeonmania.dynamic_entity;

/**
 * Are among the enemy entities that will harm Player INITIALLY, however if bribed they will NOT. Has following properties:
 *      - Are NOT spawned and must be initialised at time of dungeon creation.
 *      - Player can bribe them if they have enough treasure (gold) and are within certain radius of the Player and then at which 
 *          they become allies and assist Player.
 *      - They have two states, HOSTILE (intially) and ALLY (if bribed successfully by Player). 
 *      - Each states have different movement and they are:
 *          -- HOSTILE: Continously moves towards Player until they cannot move any closer and 
 *                          are constricted to same conditions as Player movement.
 *          -- ALLY: Follows the Player around and once they are next to Player they continually follow the Player around
 *                          occuping the previous square the Player was on and are constricted to same conditions as Player movement.
 */
public class Mercenary extends DynamicEntity {
    
}
