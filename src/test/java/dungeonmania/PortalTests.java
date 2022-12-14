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

public class PortalTests {
    @Test
    @DisplayName("Player can teleport right")
    public void testPlayerTeleports() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalTestSimple", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(2, 2), getEntities(res, "player").get(0).getPosition());

    }

    @Test
    @DisplayName("Player can teleport left")
    public void testPlayerTeleportsLeft() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalTestSimple2", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(0, 2), getEntities(res, "player").get(0).getPosition());

    }

    @Test
    @DisplayName("Player can teleport up")
    public void testPlayerTeleportsUp() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalTestSimple3", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(1, 1), getEntities(res, "player").get(0).getPosition());

    }

    @Test
    @DisplayName("Player can teleport down")
    public void testPlayerTeleportsDown() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalTestSimple4", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(1, 3), getEntities(res, "player").get(0).getPosition());

    }

    @Test
    @DisplayName("Player can teleport but target location is blocked")
    public void testPlayerTeleportsOneBlockedLeft() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalTestOneBlocked", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(0, 2), getEntities(res, "player").get(0).getPosition());
    }

    @Test
    @DisplayName("Player can teleport but target location is blocked")
    public void testPlayerTeleportsOneBlockedRight() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalTestTwoBlocked", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(2, 2), getEntities(res, "player").get(0).getPosition());
    }

    @Test
    @DisplayName("Player can teleport but target location is blocked")
    public void testPlayerTeleportsOneBlockedUp() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalTestThreeBlocked", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(1, 1), getEntities(res, "player").get(0).getPosition());
    }
    @Test
    @DisplayName("Player cannot teleport because all positions are blocked")
    public void testPlayerTeleportsAllBlockedDown() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalTestFourBlocked", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(1, 3), getEntities(res, "player").get(0).getPosition());
    }
    @Test
    @DisplayName("Player can teleport multiple portals")
    public void testPlayerTeleportsMultiplePortals() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalMultiple", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(2, 2), getEntities(res, "player").get(0).getPosition());
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(4, 1), getEntities(res, "player").get(0).getPosition());
    }
    @Test
    @DisplayName("Portal chaining")
    public void testPlayerTeleportsChainedPortals() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalsChained", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(7, 0), getEntities(res, "player").get(0).getPosition());
    }
    @Test
    @DisplayName("Portal chaining twice")
    public void testPlayerTeleportsChainedPortalsTwice() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalsChainedTwice", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(10, 0), getEntities(res, "player").get(0).getPosition());
    }
}
