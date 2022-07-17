package dungeonmania;

import dungeonmania.dynamic_entity.Player;
import dungeonmania.response.models.ItemResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.collectible.*;

public class Inventory {
    
    private Player player;
    private List<Collectible> entities; 
    private List<String> buildable = Arrays.asList("bow", "shield");
    private List<Buildable> builtItems;
    private JSONObject config;

    /**
     * Constructor for Inventory
     * @param player
     */
    public Inventory(Player player, JSONObject config) {
        this.setPlayer(player);
        entities = new ArrayList<>();
        builtItems = new ArrayList<>();
        this.config = config;
    }

    public void put(Entity entity, Player player){
        if (entity instanceof Collectible) {
            Collectible ent = (Collectible) entity;
            ent.setPlayer(player);
            this.entities.add(ent);
        }
    }

    // Getters and Setters
    public void setPlayer(Player player) {
        this.player = player;
    }

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
        int number = 0;
        for (Collectible item : entities) {
            if (item.getType().equals(type)) {
                number++;
            }
        }
        return number;
    }

    public Collectible getItem(String type) {
        for (Collectible item : entities) {
            if (item.getType().equals(type)) {
                return item;
            }
        }
        return null;
    }

    public Collectible getItemById(String id) {
        for (Collectible item : entities) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public boolean CheckMaterials(String buildable) {
        switch (buildable) {
            case "bow":
                if (getNoItemType("wood") < 1 || getNoItemType("arrow") < 3) {
                    return false;
                }
                return true;    
            case "shield":
                if (getNoItemType("wood") < 2 || (getNoItemType("treasure") < 1 && getNoItemType("key") < 1)) {
                return false;
                }
                return true;
            default:
                return false;
        }
    }

    public void removeItem(String itemToRemove) {
        for (Collectible item : entities) {
            if (item.getType().equals(itemToRemove)) {
                entities.remove(item);
                break;
            }
        }
    }

    public boolean buildItem(String buildable, String id) {
        if (CheckMaterials(buildable) && buildable.equals("bow")) {
            //make bow
            builtItems.add(new Bow(id, config));
            removeItem("wood");
            removeItem("arrow");
            removeItem("arrow");
            removeItem("arrow");
            return true;
        }
        if (CheckMaterials(buildable) && buildable.equals("shield")) {
            //make shield
            builtItems.add(new Shield(id, config));
            removeItem("wood");
            removeItem("wood");
            if (getNoItemType("treasure") >= 1) {
                removeItem("treasure");
            } else if (getNoItemType("key") >= 1) {
                removeItem("key");
            return true;
            }
        }
        return false;
    }

    public List<Collectible> getCollectableItems() {
        return entities;
    }

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

    public void removeBrokenItems() {
        // Deleting broken shields and bows
        builtItems = builtItems.stream().filter(item -> item.getDurability() != 0).collect(Collectors.toList());
        entities = entities.stream().filter(item -> (item instanceof Sword) && (((Sword)item).getDurability() != 0)).collect(Collectors.toList());
    }
}