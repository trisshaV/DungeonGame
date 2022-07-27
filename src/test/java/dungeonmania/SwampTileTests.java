package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SwampTileTests {
    @Test 
    @DisplayName("Test mercernary slowed by swamp tile factor of 2")
    public void testSwampTileMercenary2() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swampTileMerc", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        res = dmc.tick(Direction.LEFT);
        Position pos = getEntities(res, "mercenary").get(0).getPosition();
        res = dmc.tick(Direction.LEFT);
        assertEquals(pos, getEntities(res, "mercenary").get(0).getPosition());
        res = dmc.tick(Direction.LEFT);
        assertEquals(pos, getEntities(res, "mercenary").get(0).getPosition());
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(3, 1), getEntities(res, "mercenary").get(0).getPosition());
    }
    @Test 
    @DisplayName("Test mercernary slowed by swamp tile factor of 3")
    public void testSwampTileMercenary3() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swampTileMerc3", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        res = dmc.tick(Direction.LEFT);
        Position pos = getEntities(res, "mercenary").get(0).getPosition();
        res = dmc.tick(Direction.LEFT);
        assertEquals(pos, getEntities(res, "mercenary").get(0).getPosition());
        res = dmc.tick(Direction.LEFT);
        assertEquals(pos, getEntities(res, "mercenary").get(0).getPosition());
        res = dmc.tick(Direction.LEFT);
        assertEquals(pos, getEntities(res, "mercenary").get(0).getPosition());
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(3, 1), getEntities(res, "mercenary").get(0).getPosition());
    }
    @Test 
    @DisplayName("Test mercernary slowed by swamp tile factor of 0")
    public void testSwampTileMercenary0() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swampTileMerc0", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(3, 1), getEntities(res, "mercenary").get(0).getPosition());
    }

    @Test 
    @DisplayName("Test player not effected")
    public void testSwampTilePlayer() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swampTilePlayer", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        res = dmc.tick(Direction.LEFT);
        Position pos = getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.LEFT);
        assertNotEquals(pos, getEntities(res, "player").get(0).getPosition());
    }
}
