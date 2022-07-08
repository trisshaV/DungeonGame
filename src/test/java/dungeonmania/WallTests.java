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

public class WallTests {
    @Test 
    @DisplayName("Player can't walk through wall")
    public void testWalkIntoWall() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_basicMovement", "c_spiderTest_basicMovement");
        Position pos = getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.UP);
        assertEquals(pos, getEntities(res, "player").get(0).getPosition());
    }
    @Test
    @DisplayName("Can't push boulder through wall")
    public void testBoulderIntoWall() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_boulderIntoWall", "c_spiderTest_basicMovement");
        Position pos = getEntities(res, "boulder").get(0).getPosition();
        res = dmc.tick(Direction.DOWN);
        assertEquals(pos, getEntities(res, "boulder").get(0).getPosition());
    }
    @Test
    @DisplayName("Enemies can't walk through wall")
    public void testEnemiesIntoWall() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_enemyIntoWall", "c_spiderTest_basicMovement");
        Position posMerc = getEntities(res, "mercenary").get(0).getPosition();
        Position posWall = getEntities(res, "wall").get(0).getPosition();
        res = dmc.tick(Direction.UP);
        assertNotEquals(posMerc, posWall);
        
    }
    @Test
    @DisplayName("Spider can walk through wall")
    public void testSpiderIntoWall() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_wallSpiderTest", "c_spiderTest_basicMovement");
        Position pos = getEntities(res, "spider").get(0).getPosition();
        res = dmc.tick(Direction.UP);
        assertNotEquals(pos, getEntities(res, "spider").get(0).getPosition());
    }
}
