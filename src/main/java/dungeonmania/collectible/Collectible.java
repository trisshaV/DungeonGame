package dungeonmania.collectible;

import dungeonmania.response.models.ItemResponse;
import dungeonmania.static_entity.StaticEntity;
import dungeonmania.util.Position;

/**
 * Entities that are "collected" by the Player and are stored in inventory.
 */
public abstract class Collectible extends StaticEntity {
    public Collectible(String id, Position xy) {
        super(id, xy);
    }

    /**
     * TODO: remove this method / make it abstract
     * @return "key" always
     */
    public String getType() {
        return "key";
    }

    public ItemResponse toItemResponse() {
        return new ItemResponse(getId(), getType());
    }
}
