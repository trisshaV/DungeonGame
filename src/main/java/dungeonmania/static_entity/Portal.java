package dungeonmania.static_entity;

import dungeonmania.DungeonManiaController;
import dungeonmania.Entity;
import dungeonmania.dynamic_entity.Player;
import dungeonmania.util.Position;

/**
 * Each portal has a corresponding portal that allows entities to be teleported. Has following properties:
 *      - Will NOT teleport entities if ALL cardinally adjacent squares of the corresponding portal are walls.
 *      - Otherwise will teleport the entity to an avaliable cardinally adjacent square at the coresponding portal side.
 */
public class Portal extends StaticEntity {
    DungeonManiaController dungeon;
    Position linkPosition;
    String colour;

    public Portal(DungeonManiaController dungeon, String id, Position xy, String colour) {
        super(id, "portal", xy);
        this.dungeon = dungeon;
        this.colour = colour;
    }

    public boolean collide(Entity entity) {
        if (entity.getType().equals("player")) {
            Player player = (Player) entity;
            Position pos = player.getPosition();
            int changeX = pos.getX() - this.getPosition().getX();
            int changeY = pos.getY() - this.getPosition().getY();

            Entity collision = dungeon.checkStaticCollision(new Position(linkPosition.getX() - changeX, linkPosition.getY() - changeY));
            if (collision == null) {
                player.setPosition(new Position(linkPosition.getX() - changeX, linkPosition.getY() - changeY));
                return false;
            }
            Entity collisionLeft = dungeon.checkStaticCollision(new Position(linkPosition.getX() - 1, linkPosition.getY()));
            if (collisionLeft == null) {
                player.setPosition(new Position(linkPosition.getX() + changeX, linkPosition.getY() - changeY));
                return false;
            }
            Entity collisionRight = dungeon.checkStaticCollision(new Position(linkPosition.getX() + 1, linkPosition.getY()));
            if (collisionRight == null) {
                player.setPosition(new Position(linkPosition.getX() + changeX, linkPosition.getY() - changeY));
                return false;
            }
            Entity collisionUp = dungeon.checkStaticCollision(new Position(linkPosition.getX(), linkPosition.getY() - 1));
            if (collisionUp == null) {
                player.setPosition(new Position(linkPosition.getX() + changeX, linkPosition.getY() - changeY));
                return false;
            }
            Entity collisionDown = dungeon.checkStaticCollision(new Position(linkPosition.getX(), linkPosition.getY() + 1));
            if (collisionDown == null) {
                player.setPosition(new Position(linkPosition.getX() + changeX, linkPosition.getY() - changeY));
                return false;
            }
        }
        return true;
    }

    public String getColour() {
        return colour;
    }

    public void setLinkPosition(Position linkPosition) {
        this.linkPosition = linkPosition;
    }

   
    
}
