package dungeonmania.static_entity;

/**
 * Acts like empty squares and can ONLY be activated by boulders. Has following properties:
 *      - Other entities can appear on top of it but will not activate it.
 *      - Only if a boulder is on current postion will it then activate.
 *      - If boulder is then moved off it, it is then de-activated.
 */
public class FloorSwitch extends StaticEntity {
    
}
