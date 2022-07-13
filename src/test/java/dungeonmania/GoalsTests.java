package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

public class GoalsTests {
    @Test
    @DisplayName("Test simple exit goal")
    public void testSimpleExitGoal() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse resp = dmc.newGame("d_walkExit", "c_standard_movement");
        assertTrue(resp.getGoals().contains(":exit"));
        resp = dmc.tick(Direction.RIGHT);
        assertNotEquals(true, resp.getGoals().contains(":exit"));
    }

    @Test
    @DisplayName("Test simple boulder goal")
    public void testSimpleBoulderGoal() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse resp = dmc.newGame("d_walkExit", "c_standard_movement");
        assertTrue(resp.getGoals().contains(":boulder"));
        resp = dmc.tick(Direction.RIGHT);
        assertNotEquals(true, resp.getGoals().contains(":boulder"));
    }

    @Test
    @DisplayName("Test simple enemies goal")
    public void testSimpleEnemiesGoal() {

    }

    @Test
    @DisplayName("Test simple treasure goal")
    public void testSimpleTreasureGoal() {

    }
}
