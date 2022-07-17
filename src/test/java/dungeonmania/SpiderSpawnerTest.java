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

public class SpiderSpawnerTest {
    @Test 
    @DisplayName("Spider spawning at correct rate and correct values") 
    public void testSpiderSpawn() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_movementTest_testMovementDown", "c_spiderspawner");
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getEntities(res, "spider").size());
    }
    @Test 
    @DisplayName("Spiders don't spawn when spawn rate is 0")
    public void testZeroSpiderSpawn() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_movementTest_testMovementDown", "c_standard_movement");
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, getEntities(res, "spider").size());

    }
    @Test 
    @DisplayName("Spiders don't spawn on boulders or player")
    public void testSpiderSpawnNotPlayerOrBoulder() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderSpawnNotOnBoulder", "c_spiderspawner");
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getEntities(res, "spider").size());
        assertEquals(new Position(6, 1), getEntities(res, "spider").get(0).getPosition());
    }
}
