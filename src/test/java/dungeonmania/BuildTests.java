package dungeonmania;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.countEntityOfType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;


public class BuildTests {
    @Test
    @DisplayName("Player can build a bow")
    public void testBuildBow() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("build_bow", "simple");
        List<ItemResponse> inventory = new ArrayList<>();
        inventory.add(new ItemResponse("0", "bow"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.build("bow");
        List<ItemResponse> inven = getInventory(res, "bow");
        assertEquals(inventory.get(0).getType(), inven.get(0).getType());
    }

    @Test
    @DisplayName("Player can build a shield with a key")
    public void testBuildShieldRecipe1() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("build_shield", "simple");
        List<ItemResponse> inventory = new ArrayList<>();
        inventory.add(new ItemResponse("0", "shield"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.build("shield");
        List<ItemResponse> inven = getInventory(res, "shield");
        assertEquals(inventory.get(0).getType(), inven.get(0).getType());
    }

    @Test
    @DisplayName("Player can build a shield with a treasure")
    public void testBuildShieldRecipe2() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("build_shield2", "simple");
        List<ItemResponse> inventory = new ArrayList<>();
        inventory.add(new ItemResponse("0", "shield"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.build("shield");
        List<ItemResponse> inven = getInventory(res, "shield");
        assertEquals(inventory.get(0).getType(), inven.get(0).getType());
    }

    @Test
    @DisplayName("Player discards the materials used to build the item")
    public void testDiscardsMaterial() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("build_shield2", "simple");
        List<ItemResponse> inventory = new ArrayList<>();
        inventory.add(new ItemResponse("0", "shield"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.build("shield");
        List<ItemResponse> inven = getInventory(res, "shield");
        assertEquals(1, inven.size());
    }

    @Test
    @DisplayName("Player can build multiple times")
    public void testManyBuilds() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("build_shield2", "simple");
        List<ItemResponse> inventory = new ArrayList<>();
        inventory.add(new ItemResponse("0", "shield"));
        inventory.add(new ItemResponse("1", "bow"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        res = dmc.build("shield");
        res = dmc.build("bow");
        List<ItemResponse> inven = getInventory(res, "shield");
        assertEquals(inventory.get(0).getType(), inven.get(0).getType());
        inven = getInventory(res, "bow");
        assertEquals(inventory.get(1).getType(), inven.get(0).getType());
    }

    @Test
    @DisplayName("Player can build the midnight armour")
    public void testBuildArmour() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("test_new", "M3_config");
        List<ItemResponse> inventory = new ArrayList<>();
        inventory.add(new ItemResponse("0", "midnight_armour"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.build("midnight_armour");
        List<ItemResponse> inven = getInventory(res, "midnight_armour");
        assertEquals(1, inven.size());
        assertEquals(inventory.get(0).getType(), inven.get(0).getType());
    }

    @Test
    @DisplayName("Player can build the sceptre (with wood + key + sun stone)")
    public void testBuildSceptreRecipe1() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("test_new", "simple");
        List<ItemResponse> inventory = new ArrayList<>();
        inventory.add(new ItemResponse("0", "sceptre"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.build("sceptre");
        List<ItemResponse> inven = getInventory(res, "sceptre");
        assertEquals(1, inven.size());
        assertEquals(inventory.get(0).getType(), inven.get(0).getType());
    }

    @Test
    @DisplayName("Player can build the sceptre (with wood + treasure + sun stone)")
    public void testBuildSceptreRecipe2() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("test_new", "simple");
        List<ItemResponse> inventory = new ArrayList<>();
        inventory.add(new ItemResponse("0", "sceptre"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        res = dmc.build("sceptre");
        List<ItemResponse> inven = getInventory(res, "sceptre");
        assertEquals(1, inven.size());
        assertEquals(inventory.get(0).getType(), inven.get(0).getType());
    }

    @Test
    @DisplayName("Player can build the sceptre (with 2 arrows + key + sun stone)")
    public void testBuildSceptreRecipe3() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_buildSceptre", "simple");
        List<ItemResponse> inventory = new ArrayList<>();
        inventory.add(new ItemResponse("0", "sceptre"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        res = dmc.build("sceptre");
        List<ItemResponse> inven = getInventory(res, "sceptre");
        assertEquals(1, inven.size());
        assertEquals(inventory.get(0).getType(), inven.get(0).getType());
    }

    @Test
    @DisplayName("Player can build the sceptre (with 2 arrows + treasure + sun stone)")
    public void testBuildSceptreRecipe4() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_buildSceptre2", "simple");
        List<ItemResponse> inventory = new ArrayList<>();
        inventory.add(new ItemResponse("0", "sceptre"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        res = dmc.build("sceptre");
        List<ItemResponse> inven = getInventory(res, "sceptre");
        assertEquals(1, inven.size());
        assertEquals(inventory.get(0).getType(), inven.get(0).getType());
    }
}
