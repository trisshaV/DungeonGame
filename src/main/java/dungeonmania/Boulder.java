package dungeonmania;

import dungeonmania.util.Position;

/**
 * Acts like a wall in most cases that is it prohibits the movement of Player, enemies and other boulders. 
 *  At other times, it can be moved by the Player. Has following properties:
 *      - Player can only move boulders into cardinally adjacent squares.
 *      - Player is only able to move ONE boulder at a time.
 *      - When moved by Player, previous position of boulder is the new current position of Player.
 *      - Can be moved onto collectible entities.
 */
public class Boulder extends Entity {

    public Boulder(String id, Position xy) {
        super(id, xy);
    }

    public boolean collide(Entity entity) {
        return false;
    }

    public String getType() {
        return "boulder";
    }
    
}
