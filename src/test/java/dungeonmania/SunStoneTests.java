package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SunStoneTests {
    @Test
    @DisplayName("Test player can use the sun stone to open doors")
    public void useSunStoneToOpenDoor() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest", "simple");
        Position pos = getEntities(res, "door").get(0).getPosition();
        // pick up sun stone
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, getInventory(res, "sun_stone").size());

        res = dmc.tick(Direction.DOWN);

        // walk through door
        res = dmc.tick(Direction.RIGHT);
        // check if the sun stone is retained after use 
        assertEquals(1, getInventory(res, "sun_stone").size());
        assertEquals(pos, getEntities(res, "player").get(0).getPosition());
    }

    @Test
    @DisplayName("Test player can use the sun stone to open multiple doors")
    public void useSunStoneToOpenMultipleDoors() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest", "simple");
        Position pos = getEntities(res, "door").get(0).getPosition();
        Position pos2 = getEntities(res, "door").get(1).getPosition();
        // pick up sun stone
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, getInventory(res, "sun_stone").size());

        res = dmc.tick(Direction.DOWN);

        // walk through door
        res = dmc.tick(Direction.RIGHT);
        // check if the sun stone is retained after use 
        assertEquals(1, getInventory(res, "sun_stone").size());
        assertEquals(pos, getEntities(res, "player").get(0).getPosition());

        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        // check if the sun stone is used up after being used twice
        assertEquals(1, getInventory(res, "sun_stone").size());
        assertEquals(pos2, getEntities(res, "player").get(0).getPosition());
    }

    @Test
    @DisplayName("player prioritises the use of the sun stone over the key when both are present in the inventory")
    public void KeyIsNotUsedUp() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest", "simple");
        Position pos = getEntities(res, "door").get(0).getPosition();

        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "key").size());
        res = dmc.tick(Direction.LEFT);

        // pick up sun stone
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, getInventory(res, "sun_stone").size());

        res = dmc.tick(Direction.DOWN);

        // walk through door
        res = dmc.tick(Direction.RIGHT);
        // check if the sun stone is retained after use 
        assertEquals(1, getInventory(res, "sun_stone").size());
        // check if the key is still in the inventory
        assertEquals(1, getInventory(res, "key").size());
        assertEquals(pos, getEntities(res, "player").get(0).getPosition());
    }
}