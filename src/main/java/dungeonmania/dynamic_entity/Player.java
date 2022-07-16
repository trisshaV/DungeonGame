package dungeonmania.dynamic_entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.json.JSONObject;

import dungeonmania.Boulder;
import dungeonmania.Entity;
import dungeonmania.collectible.Bomb;
import dungeonmania.collectible.Collectible;
import dungeonmania.collectible.Consumable;
import dungeonmania.collectible.Key;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.static_entity.ActiveBomb;
import dungeonmania.static_entity.StaticEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import javassist.expr.NewArray;
import dungeonmania.Inventory;
/**
 * Entity that is controlled by the Player.
 */
public class Player extends DynamicEntity {
    private Inventory inventory;
    private List<String> useableItems = Arrays.asList("bomb", "invincibility_potion", "invisibility_potion", null);
    private List<Collectible> potionQueue = new ArrayList<>();
    private String status = "NONE";

    /**
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
        for (Entity entity : entities) {
            if (entity.getPosition().equals(super.getPosition()) && !entity.getType().equals("player")) {

                if (entity.getType().equals("key") && inventory.getNoItemType("key") > 0) {
                    // Entity is a key and player is already holding a key
                    // Dont pick it up
                    //this.inventory.put(entity, this);
                    //toRemove.add(entity);

                } 
                else if (entity instanceof Collectible) {
                    // Pickup the item
                    this.inventory.put(entity, this);
                    toRemove.add(entity);

                }
            }
        }
        entities.removeAll(toRemove);
    }

    public void consumePotion(Collectible Potion) {
        potionQueue.add(Potion);
    }

    public void tickPotionEffects() {
        if (potionQueue.size() == 0) {
            this.status = "NONE";
            return;
        }
        else {
            while (potionQueue.size() != 0) {
                Consumable Potion = (Consumable) potionQueue.get(0);
                if (Potion.potency()) {
                    if (((Entity) Potion).getType().equals("invincibility_potion")) {
                        this.status = "INVINCIBLE";
                        return;
                    }
                    else {
                        this.status = "INVISIBLE";
                        return; 
                    } 
                } else {
                    potionQueue.remove(0);
                }
            }
            this.status = "NONE";
            return;
        }
    }

    public String getStatus() {
        return this.status;
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

    /**
     * Given an item name, checks in the player inventory, and if exisits,
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

    public Inventory getInventory() {
        return inventory;
    }

    public void removeItem(Collectible item) {
        inventory.removeItem(item.getType());
    }

    public void removeBrokenItems() {
        inventory.removeBrokenItems();
    }
    
    public Key getKey() {
        return (Key) inventory.getItem("key");
    }

    public void removeKey() {
        inventory.removeItem("key");
    }

    public Collectible getCurrentPotion() {
        return potionQueue.get(0);
    }
}
