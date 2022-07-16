package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getGoals;
import static dungeonmania.TestUtils.countEntityOfType;
import static dungeonmania.TestUtils.getValueFromConfigFile;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.collectible.Key;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PickUpTests {
    @Test
    @DisplayName("Player can pick up an item")
    public void testPickUpKey() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_keyPickUpTest", "bomb_radius_2");
        List<ItemResponse> inventory = new ArrayList<>();
        inventory.add(new ItemResponse("2", "key"));
        res = dmc.tick(Direction.RIGHT);
        List<ItemResponse> inven = getInventory(res, "key");
        assertEquals(inven.get(0).getType(), inventory.get(0).getType());
    }

    @Test
    @DisplayName("Player can pick up multiple different items")
    public void testPickUpMultipleDifferentItems() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_keyPickUpTest", "bomb_radius_2");
        List<ItemResponse> inventory = new ArrayList<>();
        inventory.add(new ItemResponse("2", "key"));
        inventory.add(new ItemResponse("3", "arrow"));
        inventory.add(new ItemResponse("4", "wood"));
        res = dmc.tick(Direction.RIGHT);
        List<ItemResponse> inven = getInventory(res, "key");
        assertEquals(inven.get(0).getType(), inventory.get(0).getType());
        res = dmc.tick(Direction.RIGHT);
        inven = getInventory(res, "arrow");
        assertEquals(inven.get(0).getType(), inventory.get(1).getType());
        res = dmc.tick(Direction.RIGHT);
        inven = getInventory(res, "wood");
        assertEquals(inven.get(0).getType(), inventory.get(2).getType());
    }

    @Test
    @DisplayName("Player can pick up multiple identical items")
    public void testPickUpMultipleIdenticalItems() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_keyPickUpTest", "bomb_radius_2");
        List<ItemResponse> inventory = new ArrayList<>();
        inventory.add(new ItemResponse("2", "treasure"));
        inventory.add(new ItemResponse("3", "treasure"));
        res = dmc.tick(Direction.LEFT);
        List<ItemResponse> inven = getInventory(res, "treasure");
        assertEquals(inven.get(0).getType(), inventory.get(0).getType());
        res = dmc.tick(Direction.LEFT);
        inven = getInventory(res, "treasure");
        assertEquals(inven.get(1).getType(), inventory.get(1).getType());
    }
    @Test
    @DisplayName("Player can only pick up one key")
    public void testPickUpOnlyOneKey() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_keyPickUpTest", "bomb_radius_2");
        List<ItemResponse> inventory = new ArrayList<>();
        inventory.add(new ItemResponse("2", "key"));
        res = dmc.tick(Direction.RIGHT);
        List<ItemResponse> inven = getInventory(res, "key");
        assertEquals(inven.get(0).getType(), inventory.get(0).getType());
        res = dmc.tick(Direction.DOWN);
        int inventory_size = inventory.size();
        assertEquals(1, inventory_size);
    }
}
