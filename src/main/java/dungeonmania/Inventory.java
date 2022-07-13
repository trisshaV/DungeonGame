package dungeonmania;

import dungeonmania.dynamic_entity.Player;
import dungeonmania.response.models.ItemResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;

import dungeonmania.collectible.*;

public class Inventory {
    
    private Player player;
    private List<Collectible> entities; 

    /**
     * Constructor for Inventory
     * @param player
     */
    public Inventory(Player player) {
        this.setPlayer(player);
        entities = new ArrayList<>();
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
        return entities.stream()
                       .map(Collectible::toItemResponse)
                       .collect(Collectors.toList());
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

    /**
     * Uses the item with the given id.
     * @param id
     */
    public void useItem(String type) {
        getItem(type).use();
    }

    /**
     * Checks if the player has enough materials to build the given buildable
     * @param buildable
     * @return
     */
    // public boolean hasEnoughMaterials(String buildable) {
    //     switch (buildable) {
    //         case "bow":
    //             if (getNoItemType("wood") < 1 || getNoItemType("arrow") < 3) {
    //                 return false;
    //             }
    //             return true;    
    //         case "shield":
    //             if (getNoItemType("wood") < 2 || (getNoItemType("treasure") < 1 && getNoItemType("key") < 1 && getNoItemType("sun_stone") < 1)) {
    //             return false;
    //             }
    //             return true;
    //         case "sceptre":
    //             if ((getNoItemType("wood") < 1 && getNoItemType("arrow") < 2) || (getNoItemType("treasure") < 1 && 
    //                  getNoItemType("key") < 1 && getNoItemType("sun_stone") < 2) || getNoItemType("sun_stone") < 1) {
    //                 return false;
    //             }
    //             return true;
    //         default:
    //             return false;
    //     }
    // }

    // public void buildItem(String buildable) {
    //     switch (buildable) {
    //         case "bow":
    //             useItem("wood");
    //             for (int i = 0; i < 3; i++) {
    //                 useItem("arrow");
    //             }
    //             Bow newBow = new Bow("" + System.currentTimeMillis(), "bow", null);
    //             put(newBow, player);
    //             break;
    //         case "shield":
    //             useItem("wood");
    //             useItem("wood");
    //             if (getNoItemType("treasure") >= 1) {
    //                 useItem("treasure");
    //             } else if (getNoItemType("key") >= 1) {
    //                 useItem("key");
    //             }
    //             Shield newShield = new Shield("" + System.currentTimeMillis(), "shield", null);
    //             put(newShield, player);
    //             break;
    //     }
    // }

    @Override
    public String toString() {
        String inventory = "inventory : ";
        for (Entity entity : entities) {
            inventory += entity;
        }
        return inventory;
    }

}