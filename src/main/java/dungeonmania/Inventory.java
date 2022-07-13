package dungeonmania;

import dungeonmania.dynamic_entity.Player;
import dungeonmania.response.models.ItemResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.collectible.*;

public class Inventory {
    
    private Player player;
    private List<Collectible> entities; 
    private List<String> buildable;
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


    public List<Collectible> getInven() {
        return entities;
    }

    /**
     * Gets the number of items in inventory that are the given type
     * @param type
     * @return
     */
    public int getNoItemType(String type) {
        int count = 0;
        for (Collectible item : entities) {
            if (item.getType().equals(type)) {
                count++;
            }
        }
        return count;
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

    /**
     * Returns the key with the given keyId (separate from getItem since keys
     * have unique integer id's)
     * @param keyId
     * @return
     */
    public Key getKey(int keyId) {
        for (Collectible item : entities) {
            if (item.getType().equals("key")) {
                Key itm = (Key) item;
                if (itm.getKeyId() == keyId) {
                    return itm;
                }
            }
        }
        return null;
    }


    public boolean hasEnoughMaterials(String buildable) {
        switch (buildable) {
            case "bow":
                if (getNoItemType("wood") < 1 || getNoItemType("arrow") < 3) {
                    return false;
                }
                return true;    
            case "shield":
                if (getNoItemType("wood") < 2 || (getNoItemType("treasure") < 1 && getNoItemType("key") < 1 && getNoItemType("sun_stone") < 1)) {
                return false;
                }
                return true;
            default:
                return false;
        }
    }

    private void removeItem(String itemToRemove) {
        for (Collectible item : entities) {
            if (item.getType().equals(itemToRemove)) {
                entities.remove(item);
                break;
            }
        }
    }

    public boolean buildItem(String buildable, String id) {
        if (hasEnoughMaterials(buildable) && buildable.equals("bow")) {
            //make bow
            builtItems.add(new Bow(id, config));
            removeItem("wood");
            removeItem("arrow");
            removeItem("arrow");
            removeItem("arrow");
            return true;
        }
        if (hasEnoughMaterials(buildable) && buildable.equals("shield")) {
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
}