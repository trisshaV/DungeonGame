package dungeonmania.dynamic_entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.json.JSONObject;

import dungeonmania.Entity;
import dungeonmania.collectible.Collectible;
import dungeonmania.collectible.Key;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/**
 * Entity that is controlled by the Player.
 */
public class Player extends DynamicEntity {
    private int health;
    private int attack;
    private List<Collectible> inventory;

    public Player(String id, Position xy, JSONObject config) {
        super(id, xy);
        attack = config.getInt("player_attack");
        health = config.getInt("player_health");
        inventory = new ArrayList<>();
    }

    @Override
    public String getType() {
        return "player";
    }

    public List<ItemResponse> getInventory() {
        return inventory.stream()
            .map(Collectible::toItemResponse)
            .collect(Collectors.toList());
    }

    public List<String> getBuildables() {
        return new ArrayList<>();
    }

    /**
     * Adds a collectible to the player's inventory.
     * @return if the item was picked up.
     */
    public boolean collect(Collectible c) {
        // player cannot pick up more than one key
        if (c.getType().equals("key") && getKey().isPresent()) {
            return false;
        }

        inventory.add(c);
        return true;
    }

    /**
     * Remove the key the player is holding
     */
    public void removeKey() {
        getKey().ifPresent(key -> inventory.remove(key));
    }

    /** 
     * Returns a key if the player has one, else empty.
    */
    public Optional<Collectible> getKey() {
        return inventory.stream().filter(x -> x.getType().equals("key")).findFirst();
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

        // Check next position for obstacles/issues

        // Is there a wall
        // Is there a boulder
        // is there a portal
        // etc

        this.setPosition(new Position(x, y));
    }
}
