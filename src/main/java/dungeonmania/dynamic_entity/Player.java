package dungeonmania.dynamic_entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.json.JSONObject;

import dungeonmania.Boulder;
import dungeonmania.Entity;
import dungeonmania.collectible.Bomb;
import dungeonmania.collectible.Collectible;
import dungeonmania.collectible.Key;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.static_entity.ActiveBomb;
import dungeonmania.static_entity.StaticEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.Inventory;
/**
 * Entity that is controlled by the Player.
 */
public class Player extends DynamicEntity {
    private Inventory inventory;
    private List<String> useableItems = Arrays.asList("bomb", "health_potion", "invincibility_potion", "invisibility_potion", null);

    // Total treasure collected - not current treasure
    private int treasure_collected = 0;
    private int enemies_defeated = 0;

    /**
     * @param id
     * @param xy
     * @param config
     */
    public Player(String id, Position xy, JSONObject config) {
        super(id, "player", xy);
        this.attack = config.getInt("zombie_attack");
        this.health = config.getInt("zombie_health");
        inventory = new Inventory(this, config);
    }

    @Override
    public String getType() {
        return "player";
    }

    public List<String> getBuildables() {
        List<String> buildables = new ArrayList<>();
        if (inventory.hasEnoughMaterials("bow")) {
            buildables.add("bow");
        }
        if (inventory.hasEnoughMaterials("shield")) {
            buildables.add("shield");
        }
        return buildables;
    }

    /**
     * Updates the new position of Player given a direction
     */
    public void updatePos(Direction d, List<Entity> l) {
        Position curr = this.getPosition();
        int x = curr.getX();
        int y = curr.getY();

        switch(d) {
            case DOWN:
                y += 1;
                break;
            case UP:
                y -= 1;
                break;
            case LEFT:
                x -= 1;
                break;
            case RIGHT: 
                x += 1;
                break;
        }
        Position nextPosition = new Position(x, y);
        // Check next position for obstacles/issues
        List <Entity> collides = l.stream().filter(entity -> entity.getPosition().equals(nextPosition)).collect(Collectors.toList());
        if (collides.stream().filter(entity -> entity instanceof Boulder).anyMatch(entity -> (!entity.collide(this) && entity != null) == true)) {
            return;
        }
        if (collides.stream().filter(entity -> entity instanceof StaticEntity).anyMatch(entity -> (!entity.collide(this) && entity != null) == true)) {
            return;
        }
        if (collides.stream().filter(entity -> entity instanceof DynamicEntity).anyMatch(entity -> (!entity.collide(this) && entity != null) == true)) {
            return;
        }
        if (collides.stream().filter(entity -> entity instanceof Collectible).anyMatch(entity -> (!entity.collide(this) && entity != null) == true)) {
            return;
        }
        this.setPosition(nextPosition);
    }

    public void pickUp(List<Entity> entities) {
        List<Entity> toRemove = new ArrayList<>();
        entities.stream()
                .filter(entity -> this.getPosition().equals(entity.getPosition())) // same position
                .filter(entity -> entity instanceof Collectible)
                .forEach(collectible -> {
                    if (inventory.getNoItemType("key") > 0 && collectible instanceof Key) {
                        return;
                    }
                    if (collectible.getType().equals("treasure")) {
                        treasure_collected++;
                    }
                    this.inventory.put(collectible, this);
                    toRemove.add(collectible);
                });
        entities.removeAll(toRemove);
    }

    public List<Collectible> getInventoryList() {
        return inventory.getInven();
    }

    /**
     * Given an item name, check if the player has the 
     * item in inventory or not.
     * @param item (Collectable Entity)
     * @return True if player has item, and false otherwise.
     */
    public boolean hasItem(String item) {
        return !(inventory.getItem(item) == null);
    }

    public int getEnemiesDefeated() {
        return enemies_defeated;
    }

    /**
     * Given an item name, checks in the player inventory, and if exists,
     * return the item as a collectable entity.
     * @param item (String)
     * @return The item (Collectable Entity)
     */
    public Collectible getItem(String item) {
        return inventory.getItem(item);
    }

    public Collectible getItemById(String id) {
        return inventory.getItemById(id);
    }

    public int totalTreasureCollected() { return treasure_collected; }

    public Inventory getInventory() {
        return inventory;
    }

    public void removeItem(Collectible item) {
        inventory.removeItem(item.getType());
    }
    
    public Key getKey() {
        return (Key) inventory.getItem("key");
    }

    public void removeKey() {
        inventory.removeItem("key");
    }
}
