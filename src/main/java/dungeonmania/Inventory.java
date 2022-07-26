package dungeonmania;

import dungeonmania.dynamic_entity.Player;
import dungeonmania.response.models.ItemResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.collectible.*;
import java.io.Serializable;

public class Inventory implements Serializable{
    
    private Player player;
    private List<Collectible> entities; 
    private List<String> buildable = Arrays.asList("bow", "shield", "sceptre", "midnight_armour");
    private List<Buildable> builtItems;
    private SerializableJSONObject config;

    /**
     * Constructor for Inventory
     * @param player
     */
    public Inventory(Player player, SerializableJSONObject config) {
        this.setPlayer(player);
        entities = new ArrayList<>();
        builtItems = new ArrayList<>();
        this.config = config;
    }

    /**
     * Puts collectibles in Player
     * @param entity
     * @param player
     */
    public void put(Entity entity, Player player){
        if (entity instanceof Collectible) {
            Collectible ent = (Collectible) entity;
            ent.setPlayer(player);
            this.entities.add(ent);
        }
    }

    /**
     * Sets Player
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets item responses
     * @return responses of items
     */
    public List<ItemResponse> getItemResponses() {
        List<ItemResponse> itemResponses = entities.stream()
                                                   .map(Collectible::toItemResponse)
                                                   .collect(Collectors.toList());
        for (Buildable buildable : builtItems) {
            itemResponses.add(new ItemResponse(buildable.getId(), buildable.getType()));
        }

        return  itemResponses;
    }

    public int getNoItemType(String type) {
        return (int)entities.stream()
                   .filter(e -> e.getType().equals(type))
                   .count();
    }

    public void removeNoItemType(String type, int number) {
        for (int i = 0; i < number; i++) {
            removeItem(type);
        }
    }

    /**
     * Gets items
     * @param type
     * @return the items
     */
    public Collectible getItem(String type) {
        return entities.stream()
                   .filter(e -> e.getType().equals(type))
                   .findFirst()
                   .orElse(null);
    }

    /**
     * Get collectibles by id
     * @param id
     * @return collectibles according to id
     */
    public Collectible getItemById(String id) {
        return entities.stream()
                   .filter(e -> e.getId().equals(id))
                   .findFirst()
                   .orElse(null);
    }

    public boolean CheckMaterials(String buildable) {
        switch (buildable) {
            case "bow":
                return Bow.checkMaterials(this);
            case "shield":
                return Shield.checkMaterials(this);
            case "sceptre":
                return Sceptre.checkMaterials(this);
            case "midnight_armour":
                return MidnightArmour.checkMaterials(this);
            default:
                return false;
        }
    }

    /**
     * Remove an item
     * @param itemToRemove
     */
    public void removeItem(String itemToRemove) {
        for (Collectible item : entities) {
            if (item.getType().equals(itemToRemove)) {
                entities.remove(item);
                break;
            }
        }
    }

    public void addBuiltItem(Buildable item) {
        builtItems.add(item);
    }

    /**
     * Build an item
     * @param buildable
     * @param id
     * @return item built
     */
    public void buildItem(String buildable, String id) {
        switch (buildable) {
            case "bow":
                removeItemComponents(Bow.requirements());
                builtItems.add(new Bow(id, config));
                break;
            case "shield":
                removeItemComponents(Shield.requirements(this));
                builtItems.add(new Shield(id, config));
                break;
            case "midnight_armour":
                removeItemComponents(MidnightArmour.requirements());
                builtItems.add(new MidnightArmour(id, config));
                break;
            case "sceptre":
                removeItemComponents(Sceptre.requirements(this));
                builtItems.add(new Sceptre(id, config));
                break;
            default:
                return;
        }
    }

    /**
     * Gets collectable items
     * @return items that are collectable
     */
    public List<Collectible> getCollectableItems() {
        return entities;
    }

    /**
     * Gets buildable items
     * @return items that are buildable
     */
    public List<Buildable> getBuildableItems() {
        return builtItems;
    }

    public void reduceDurability(String type, String id) {
        if (buildable.contains(type)) {
            // Buildable item
            Buildable item = builtItems.stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList()).get(0);
            int currentDurability = item.getDurability();
            item.setDurability(currentDurability - 1);
        } else {
            // Collectible item
            Collectible item = entities.stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList()).get(0);
            Sword itemSword = ((Sword)item);
            int currentDurability = itemSword.getDurability();
            itemSword.setDurability(currentDurability - 1);
        }
    }

    /**
     * Remove items that have been broken
     */
    public void removeBrokenItems() {
        // Deleting broken shields and bows
        builtItems = builtItems.stream().filter(item -> item.getDurability() != 0).collect(Collectors.toList());
        entities = entities.stream().filter(item -> (item instanceof Sword) && (((Sword)item).getDurability() != 0)).collect(Collectors.toList());
    }
    
    public List<String> getBuildables() {
        return buildable.stream()
                        .filter(b -> CheckMaterials(b))
                        .collect(Collectors.toList());
    }

    void removeItemComponents(List<String> toRemove) {
        for (String r : toRemove) {
            removeItem(r);
        }
    }
}