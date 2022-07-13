package dungeonmania;

import dungeonmania.dynamic_entity.Player;
import dungeonmania.static_entity.FloorSwitch;
import dungeonmania.static_entity.Wall;
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

    private DungeonManiaController dungeon;

    public Boulder(DungeonManiaController dungeon, String id, Position xy) {
        super(id, xy);
        this.dungeon = dungeon;
    }

    public boolean collide(Entity entity) {
        if (entity.getType().equals("player")) {
            Player player = (Player) entity;
            Position pos = player.getPosition();
            int changeX = pos.getX() - this.getPosition().getX();
            int changeY = pos.getY() - this.getPosition().getY();

            Entity collision = dungeon.checkStaticCollision(new Position(this.getPosition().getX() - changeX, this.getPosition().getY() - changeY));
            if (collision instanceof FloorSwitch) {
                FloorSwitch activateSwitch = (FloorSwitch) collision;
                activateSwitch.collide(this);
                this.setPosition(new Position(this.getPosition().getX() - changeX, this.getPosition().getY() - changeY));
                return true;
            }
            else if (collision == null) {
                this.setPosition(new Position(this.getPosition().getX() - changeX, this.getPosition().getY() - changeY));
                return true;
            }
        }
        return false;
    }
    
    public String getType() {
        return "boulder";
    }
    
}
