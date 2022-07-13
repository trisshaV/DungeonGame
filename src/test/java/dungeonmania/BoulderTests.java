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

public class BoulderTests {
    @Test
    @DisplayName("Player can push boulder") 
    public void testPushBoulder() {
        // check all four directions work
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_boulderPushTest", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        Position initBoulder = getEntities(res, "boulder").get(0).getPosition();
        res = dmc.tick(Direction.DOWN);
        Position pos = getEntities(res, "player").get(0).getPosition();
        assertEquals(initBoulder, pos);
        Position currBoulder = getEntities(res, "boulder").get(0).getPosition();
        assertEquals(new Position(1, 3), currBoulder);
    }
    @Test
    @DisplayName("Player can't push more than 1 boulder")
    public void testPushTwoBoulders() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_boulderPushMultiple", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        Position initBoulder = getEntities(res, "boulder").get(0).getPosition();
        Position pos = getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.DOWN);
        assertEquals(pos, getEntities(res, "player").get(0).getPosition());
        assertEquals(initBoulder, getEntities(res, "boulder").get(0).getPosition());
    }
    @Test 
    @DisplayName("Player can't push boulder into wall")
    public void testPushBoulderIntoWall() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_boulderPushIntoWall", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        Position initBoulder = getEntities(res, "boulder").get(0).getPosition();
        Position pos = getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.DOWN);
        assertEquals(pos, getEntities(res, "player").get(0).getPosition());
        assertEquals(initBoulder, getEntities(res, "boulder").get(0).getPosition());
    }
}
