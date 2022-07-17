package dungeonmania.dynamic_entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

import dungeonmania.Entity;
import dungeonmania.collectible.Collectible;
import dungeonmania.collectible.InvincibilityPotion;
import dungeonmania.collectible.InvisibilityPotion;
import dungeonmania.collectible.Key;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.Inventory;
/**
 * Entity that is controlled by the Player.
 */
public class Player extends DynamicEntity {
    private Inventory inventory;
    private List<String> useableItems = Arrays.asList("bomb", "invincibility_potion", "invisibility_potion", null);
    private List<Collectible> potionQueue = new ArrayList<>();
    private String status = "NONE";

    // Total treasure collected - not current treasure
    private int treasure_collected = 0;
    private int enemies_defeated = 0;

    /**
     * Player Constructor
     * @param id
     * @param xy
     * @param config
     */
    public Player(String id, Position xy, JSONObject config) {
        super(id, "player", xy);
        this.attack = config.getDouble("player_attack");
        this.health = config.getDouble("player_health");
        inventory = new Inventory(this, config);
        this.status = "NONE";
    }

    /**
     * Gets type
     * @return the type, i.e. "player"
     */
    @Override
    public String getType() {
        return "player";
    }

    /**
     * Gets buildables
     * @return the buildables
     */
    public List<String> getBuildables() {
        List<String> buildables = new ArrayList<>();
        if (inventory.CheckMaterials("bow")) {
            buildables.add("bow");
        }
        if (inventory.CheckMaterials("shield")) {
            buildables.add("shield");
        }
        return buildables;
    }

    /**
     * Updates the new position of Player given a direction
     * @param d
     * @param l
     */
    public void updatePos(Direction d, List<Entity> l) {
        Position curr = this.getPosition();
        Position nextPosition = curr.translateBy(d);
        // Check next position for obstacles/issues
        List <Entity> collides = l.stream().filter(entity -> entity.getPosition().equals(nextPosition)).collect(Collectors.toList());
        if (collides.stream().anyMatch(entity -> !entity.collide(this))) {
            return;
        }
        this.setPosition(nextPosition);
    }

    /**
     * Picks up items
     * @param entities
     */
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

    /**
     * Consumes potions
     * @param Potion
     */
    public void consumePotion(Collectible Potion) {
        potionQueue.add(Potion);
    }

    /**
     * Ticks for potion effects
     */
    public void tickPotionEffects() {
        if (potionQueue.size() == 0) {
            this.status = "NONE";
            return;
        }
        else {
            while (potionQueue.size() != 0) {

                if (potionQueue.get(0) instanceof InvincibilityPotion) {
                    InvincibilityPotion Potion = (InvincibilityPotion) potionQueue.get(0);
                    if (Potion.potency()) {
                        this.status = "INVINCIBLE";
                        return;
                    }
                } else if (potionQueue.get(0) instanceof InvisibilityPotion){
                    InvisibilityPotion Potion = (InvisibilityPotion) potionQueue.get(0);
                    if (Potion.potency()) {
                        this.status = "INVISIBLE";
                        return;
                    }
                } 
                potionQueue.remove(0);
            }
            this.status = "NONE";
            return;
        }
    }

    /**
     * Gets status
     * @return the status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Gets inventory list
     * @return the inventory
     */
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

    /**
     * Gets enemies defeated
     * @return the number of defeated enemies
     */
    public int getEnemiesDefeated() {
        return enemies_defeated;
    }

    /**
     * Enemy count
     */
    public void addEnemiesDefeated() {
        enemies_defeated++;
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

    /**
     * Get items by Id
     * @param id
     * @return the item according to Id
     */
    public Collectible getItemById(String id) {
        return inventory.getItemById(id);
    }

    /**
     * Total treasure collected
     * @return all treasure obtained
     */
    public int totalTreasureCollected() { return treasure_collected; }

    /**
     * Gets inventory
     * @return inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Remove an item
     * @param item
     */
    public void removeItem(Collectible item) {
        inventory.removeItem(item.getType());
    }

    /**
     * Remove the broken items
     */
    public void removeBrokenItems() {
        inventory.removeBrokenItems();
    }
    
    /**
     * Gets key
     * @return the key
     */
    public Key getKey() {
        return (Key) inventory.getItem("key");
    }

    /**
     * Checks for sword
     * @return presence of sword
     */
    public boolean hasSword() {
        return  (inventory.getItem("sword") != null);
    }

    /**
     * Remove a key
     */
    public void removeKey() {
        inventory.removeItem("key");
    }

    /**
     * Gets current position
     * @return the current position
     */
    public Collectible getCurrentPotion() {
        return potionQueue.get(0);
    }
}
