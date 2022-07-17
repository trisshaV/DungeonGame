package dungeonmania.collectible;

import dungeonmania.Entity;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.static_entity.StaticEntity;
import dungeonmania.dynamic_entity.Player;
import dungeonmania.util.Position;

/**
 * Entities that are "collected" by the Player and are stored in inventory.
 */
public abstract class Collectible extends StaticEntity {
    private Player player;

    /**
     * Collectible Constructor
     * @param id
     * @param type
     * @param xy
     */
    public Collectible(String id, String type, Position xy) {
        super(id, type, xy);
    }

    /**
     * Checks for collision
     * @param entity
     * @return the collision status
     */
    public boolean collide(Entity entity) {
        return true;
    }

    /**
     * New item response
     * @return the new response
     */
    public ItemResponse toItemResponse() {
        return new ItemResponse(getId(), getType());
    }

    /**
     * Sets player
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets player
     * @return the Player
     */
    public Player getPlayer() {
        return player;
    }
}
