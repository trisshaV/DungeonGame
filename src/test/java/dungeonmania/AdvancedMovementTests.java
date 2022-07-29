package dungeonmania;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dungeonmania.TestUtils.getEntitiesStream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdvancedMovementTests {
    @Test
    @DisplayName("Test that the mercenary's is not greedy")
    public void testMercenaryDoesNotTakeGreedyPath() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse resp = dmc.newGame("d_dijkstrasMaze_simple", "c_standard_movement");
        Position prev = getEntitiesStream(resp, "mercenary").findFirst().get().getPosition();
        resp = dmc.tick(Direction.DOWN);
        Position pos = getEntitiesStream(resp, "mercenary").findFirst().get().getPosition();
        assertFalse(prev.translateBy(Direction.DOWN).equals(pos));
        // mercenary has two possible places since the distance is the same
        assertTrue(pos.equals(prev.translateBy(Direction.UP)) || pos.equals(prev.translateBy(Direction.RIGHT)));
    }

    @Test
    @DisplayName("Test mercenary takes shortest path A")
    public void testMercenaryTakesShortestA() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse resp = dmc.newGame("d_dijkstrasMaze_simple", "c_standard_movement");
        Position prev = getEntitiesStream(resp, "mercenary").findFirst().get().getPosition();
        resp = dmc.tick(Direction.LEFT);
        Position pos = getEntitiesStream(resp, "mercenary").findFirst().get().getPosition();
        assertTrue(pos.equals(prev.translateBy(Direction.UP)));
    }

    @Test
    @DisplayName("Test mercenary takes shortest path B")
    public void testMercenaryTakesShortestB() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse resp = dmc.newGame("d_dijkstrasMaze_simple", "c_standard_movement");
        Position prev = getEntitiesStream(resp, "mercenary").findFirst().get().getPosition();
        resp = dmc.tick(Direction.RIGHT);
        Position pos = getEntitiesStream(resp, "mercenary").findFirst().get().getPosition();
        assertTrue(pos.equals(prev.translateBy(Direction.RIGHT)));
    }

    @Test
    @DisplayName("Test that the mercenary takes the only available path")
    public void testMercenaryTakesOnlyPath() {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_dijkstrasMaze_simple", "c_standard_movement");
        DungeonResponse resp = dmc.tick(Direction.RIGHT);
        Position pos = getEntitiesStream(resp, "mercenary").findFirst().get().getPosition();
        assertTrue(pos.equals(new Position(3, 1)));
        resp = dmc.tick(Direction.UP);
        pos = getEntitiesStream(resp, "mercenary").findFirst().get().getPosition();
        assertTrue(pos.equals(new Position(2, 1)));
        resp = dmc.tick(Direction.RIGHT);
        pos = getEntitiesStream(resp, "mercenary").findFirst().get().getPosition();
        assertTrue(pos.equals(new Position(1, 1)));
        resp = dmc.tick(Direction.LEFT);
        pos = getEntitiesStream(resp, "mercenary").findFirst().get().getPosition();
        assertTrue(pos.equals(new Position(1, 2)));
        resp = dmc.tick(Direction.RIGHT);
        pos = getEntitiesStream(resp, "mercenary").findFirst().get().getPosition();
        assertTrue(pos.equals(new Position(1, 3)));
    }
}
