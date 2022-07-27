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

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class DoorTest {
    @Test
    @DisplayName("Test player can use a key to open and walk through a door")
    public void useKeyWalkThroughOpenDoor() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_DoorsKeysTest_useKeyWalkThroughOpenDoor", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");

        // pick up key
        res = dmc.tick(Direction.RIGHT);
        Position pos = getEntities(res, "player").get(0).getPosition();
        assertEquals(1, getInventory(res, "key").size());

        // walk through door and check key is gone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, getInventory(res, "key").size());
        assertNotEquals(pos, getEntities(res, "player").get(0).getPosition());
    }

    @Test
    @DisplayName("Test player can't walk through a door without key")
    public void noKeyWalkIntoClosedDoor() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_DoorsKeysTest_useKeyWalkThroughOpenDoor", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");

        // walk around key 
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        Position pos = getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.DOWN);
        assertEquals(0, getInventory(res, "key").size());
        assertEquals(pos, getEntities(res, "player").get(0).getPosition());
    }

    @Test
    @DisplayName("Test player can walk through opened door")
    public void openedDoorCanWalkThrough() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_DoorsKeysTest_useKeyWalkThroughOpenDoor", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        // pick up key
        res = dmc.tick(Direction.RIGHT);
        // walk through door 
        res = dmc.tick(Direction.RIGHT);
        // walk past door 
        res = dmc.tick(Direction.RIGHT);
        Position pos = getEntities(res, "player").get(0).getPosition();
        // walk back to door 
        res = dmc.tick(Direction.LEFT);
        assertNotEquals(pos, getEntities(res, "player").get(0).getPosition());
    }

    @Test
    @DisplayName("Wrong key won't work")
    public void wrongKey() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_wrongKey", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "key").size());
        res = dmc.tick(Direction.RIGHT);
        Position pos = getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.DOWN);
        assertEquals(pos, getEntities(res, "player").get(0).getPosition());
        assertEquals(1, getInventory(res, "key").size());
    }
}
