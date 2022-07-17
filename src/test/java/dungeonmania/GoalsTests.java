package dungeonmania;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

import static org.junit.jupiter.api.Assertions.*;

public class GoalsTests {
    @Test
    @DisplayName("Test simple exit goal")
    public void testSimpleExitGoal() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse resp = dmc.newGame("d_walkExit", "c_standard_movement");
        assertTrue(resp.getGoals().contains(":exit"));
        resp = dmc.tick(Direction.RIGHT);
        assertFalse(resp.getGoals().contains(":exit"));
    }

    @Test
    @DisplayName("Test simple boulder goal")
    public void testSimpleBoulderGoal() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse resp = dmc.newGame("d_boulderGoal", "c_standard_movement");
        assertTrue(resp.getGoals().contains(":boulders"));
        resp = dmc.tick(Direction.RIGHT);
        assertFalse(resp.getGoals().contains(":boulders"));
    }

    @Test
    @DisplayName("Test simple enemies goal")
    public void testSimpleEnemiesGoal() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse resp = dmc.newGame("d_battleTest_enemiesGoal", "c_battleTests_basicMercenaryMercenaryDies");
        assertTrue(resp.getGoals().contains(":enemies"));
        resp = dmc.tick(Direction.RIGHT);
        assertFalse(resp.getGoals().contains(":enemies"));
    }

    @Test
    @DisplayName("Test simple treasure goal")
    public void testSimpleTreasureGoal() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse resp = dmc.newGame("d_treasureGoal", "c_bombTest_placeBombRadius2");
        assertTrue(resp.getGoals().contains(":treasure"));
        resp = dmc.tick(Direction.RIGHT);
        assertFalse(resp.getGoals().contains(":treasure"));
    }

    @Test
    @DisplayName("Test composite goals AND")
    public void testCompositeGoals() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse resp = dmc.newGame("d_complexGoals_exitLast", "c_bombTest_placeBombRadius2");
        assertTrue(resp.getGoals().contains(":treasure"));
        assertTrue(resp.getGoals().contains(":exit"));
        assertTrue(resp.getGoals().contains(":boulders"));
        resp = dmc.tick(Direction.DOWN);
        // should have achieved boulders
        assertTrue(resp.getGoals().contains(":treasure"));
        assertTrue(resp.getGoals().contains(":exit"));
        assertFalse(resp.getGoals().contains(":boulders"));
        // go to exit, cannot achieve exit
        resp = dmc.tick(Direction.LEFT);
        assertTrue(resp.getGoals().contains(":treasure"));
        assertTrue(resp.getGoals().contains(":exit"));
        assertFalse(resp.getGoals().contains(":boulders"));
        dmc.tick(Direction.RIGHT);
        // collect treasure, now treasure goal is achieved
        resp = dmc.tick(Direction.RIGHT);
        assertFalse(resp.getGoals().contains(":treasure"));
        assertTrue(resp.getGoals().contains(":exit"));
        assertFalse(resp.getGoals().contains(":boulders"));
        dmc.tick(Direction.LEFT);
        // now going to exit finishes the game
        resp = dmc.tick(Direction.LEFT);
        assertFalse(resp.getGoals().contains(":treasure"));
        assertFalse(resp.getGoals().contains(":exit"));
        assertFalse(resp.getGoals().contains(":boulders"));
    }

    @Test
    @DisplayName("Test complex goals OR")
    void testCompositeGoalsOR() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse resp = dmc.newGame("d_complexGoals_OR", "c_bombTest_placeBombRadius2");
        assertTrue(resp.getGoals().contains(":treasure"));
        assertTrue(resp.getGoals().contains(":exit"));
        assertTrue(resp.getGoals().contains(":boulders"));
        resp = dmc.tick(Direction.DOWN);
        // should have achieved boulders
        assertTrue(resp.getGoals().contains(":treasure"));
        assertTrue(resp.getGoals().contains(":exit"));
        assertFalse(resp.getGoals().contains(":boulders"));
        // go to exit, cannot achieve exit
        resp = dmc.tick(Direction.LEFT);
        assertFalse(resp.getGoals().contains(":treasure"));
        assertFalse(resp.getGoals().contains(":exit"));
        assertFalse(resp.getGoals().contains(":boulders"));
    }
}
