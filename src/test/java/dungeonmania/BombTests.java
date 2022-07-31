package dungeonmania;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.countEntityOfType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;


public class BombTests {
    @Test
    @DisplayName("Player can detonate the bomb of radius 1 when bomb is used next an active switch")
    public void testDetonateBombMethod1() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("bombs", "simple");
        EntityResponse Bomb = getEntities(res, "bomb").get(0);

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Bomb.getId());

        int count_wall = countEntityOfType(res, "wall");
        int count_treasure = countEntityOfType(res, "treasure");
        // check to see if the bomb dissapears after exploding
        int count_bomb = countEntityOfType(res, "bomb");

        assertEquals(1, count_wall);
        assertEquals(1, count_treasure);
        assertEquals(0, count_bomb);
    }

    @Test
    @DisplayName("Player turns on the switch after the bomb is placed to activate the bomb")
    public void testDetonateBombMethod2() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("bombs", "simple");
        EntityResponse Bomb = getEntities(res, "bomb").get(0);

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Bomb.getId());
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);

        int count_wall = countEntityOfType(res, "wall");
        int count_treasure = countEntityOfType(res, "treasure");
        // check to see if the bomb dissapears after exploding
        int count_bomb = countEntityOfType(res, "bomb");

        assertEquals(1, count_wall);
        assertEquals(1, count_treasure);
        assertEquals(0, count_bomb);
    }

    @Test
    @DisplayName("Player can change the radius of the bomb")
    public void testChangeRadius() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("bombs", "bomb_radius_2");
        EntityResponse Bomb = getEntities(res, "bomb").get(0);

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Bomb.getId());

        int count_wall = countEntityOfType(res, "wall");
        int count_exit = countEntityOfType(res, "exit");
        assertEquals(0, count_wall);
        assertEquals(1, count_exit);
    }

    @Test
    @DisplayName("Bomb will not explode next to a deactivated switch")
    public void testNoExplode() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("bombs", "bomb_radius_2");
        EntityResponse Bomb = getEntities(res, "bomb").get(0);

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Bomb.getId());

        int count_wall = countEntityOfType(res, "wall");
        int count_exit = countEntityOfType(res, "exit");
        int count_bomb = countEntityOfType(res, "bomb");

        assertEquals(2, count_wall);
        assertEquals(1, count_exit);
        assertEquals(1, count_bomb);
    }

    @Test
    @DisplayName("Player can detonate the bomb of radius 10 when bomb is used next an active switch")
    public void testLargeRadius() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_bombExplode", "c_bombExplode");
        EntityResponse Bomb = getEntities(res, "bomb").get(0);

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Bomb.getId());

        int count_wall = countEntityOfType(res, "wall");
        int count_treasure = countEntityOfType(res, "treasure");
        // check to see if the bomb dissapears after exploding
        int count_bomb = countEntityOfType(res, "bomb");

        assertEquals(0, count_wall);
        assertEquals(1, count_treasure);
        assertEquals(0, count_bomb);
    }
}
