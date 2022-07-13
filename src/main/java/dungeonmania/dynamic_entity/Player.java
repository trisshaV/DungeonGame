package dungeonmania.dynamic_entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.json.JSONObject;

import dungeonmania.Boulder;
import dungeonmania.Entity;
import dungeonmania.collectible.Collectible;
import dungeonmania.collectible.Key;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.static_entity.StaticEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/**
 * Entity that is controlled by the Player.
 */
public class Player extends DynamicEntity {
    private List<Collectible> inventory;
    

    public Player(String id, Position xy, JSONObject config) {
        super(id, xy);
        this.attack = config.getInt("player_attack");
        this.health = config.getInt("player_health");
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

    
}
