package dungeonmania.collectible;

import dungeonmania.static_entity.StaticEntity;

/**
 * Entities that are "collected" by the Player and are stored in inventory.
 */
public abstract class Collectible extends StaticEntity {
    /**
     * TODO: remove this method / make it abstract
     * @return "key" always
     */
    public String getType() {
        return "key";
    }
}
