package dungeonmania.collectible;

import java.util.Collection;

import dungeonmania.Entity;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.static_entity.StaticEntity;
import dungeonmania.dynamic_entity.Player;
import dungeonmania.util.Position;

/**
 * Entities that are "collected" by the Player and are stored in inventory.
 */
public abstract class Collectible extends StaticEntity {
    public Collectible(String id, String type, Position xy) {
        super(id, type, xy);
    }

    public boolean collide(Entity entity) {
        return true;
    }

    public ItemResponse toItemResponse() {
        return new ItemResponse(getId(), getType());
    }

    private Player player;
    /**
     * Constructor for CollectableEntity
     * @param id
     * @param type
     * @param pos
     */

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
