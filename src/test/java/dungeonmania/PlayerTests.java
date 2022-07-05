package dungeonmania;

import static dungeonmania.TestUtils.getPlayer;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PlayerTests {
    @Test
    @DisplayName("Test the player can move down")
    public void testMovementDown() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("/dungeons/d_standardMovementTest", "/configs/c_standard_movement");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(4, 5), false);

        // move player downward
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Test the player can move up")
    public void testMovementUp() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("/dungeons/d_standardMovementTest", "/configs/c_standard_movement");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(4, 3), false);

        // move player upward
        DungeonResponse actualDungonRes = dmc.tick(Direction.UP);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Test the player can move left")
    public void testMovementLeft() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("/dungeons/d_standardMovementTest", "/configs/c_standard_movement");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(3, 4), false);

        // move player leftward
        DungeonResponse actualDungonRes = dmc.tick(Direction.LEFT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Test the player can move right")
    public void testMovementRight() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("/dungeons/d_standardMovementTest", "/configs/c_standard_movement");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(5, 4), false);

        // move player rightward
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Test the player can move all directions with repeated directions")
    public void testMovementAllDirections() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("/dungeons/d_standardMovementTest", "/configs/c_standard_movement");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer0 = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(5, 4), false);

        // move player rightward
        DungeonResponse actualDungonRes0 = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer0 = getPlayer(actualDungonRes0).get();

        // assert after movement
        assertEquals(expectedPlayer0, actualPlayer0);

        // create the expected result
        EntityResponse expectedPlayer1 = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(6, 4), false);

        // move player rightward
        DungeonResponse actualDungonRes1 = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer1 = getPlayer(actualDungonRes1).get();

        // assert after movement
        assertEquals(expectedPlayer1, actualPlayer1);

        // create the expected result
        EntityResponse expectedPlayer2 = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(6, 5), false);

        // move player downward
        DungeonResponse actualDungonRes2 = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer2 = getPlayer(actualDungonRes2).get();

        // assert after movement
        assertEquals(expectedPlayer2, actualPlayer2);

        // create the expected result
        EntityResponse expectedPlayer3 = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(5, 5), false);

        // move player leftward
        DungeonResponse actualDungonRes3 = dmc.tick(Direction.LEFT);
        EntityResponse actualPlayer3 = getPlayer(actualDungonRes3).get();

        // assert after movement
        assertEquals(expectedPlayer3, actualPlayer3);

        // create the expected result
        EntityResponse expectedPlayer4 = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(4, 5), false);

        // move player leftward
        DungeonResponse actualDungonRes4 = dmc.tick(Direction.LEFT);
        EntityResponse actualPlayer4 = getPlayer(actualDungonRes4).get();

        // assert after movement
        assertEquals(expectedPlayer4, actualPlayer4);

        // create the expected result
        EntityResponse expectedPlayer5 = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(4, 6), false);

        // move player downward
        DungeonResponse actualDungonRes5 = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer5 = getPlayer(actualDungonRes5).get();

        // assert after movement
        assertEquals(expectedPlayer5, actualPlayer5);

        // create the expected result
        EntityResponse expectedPlayer6 = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(4, 7), false);

        // move player downward
        DungeonResponse actualDungonRes6 = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer6 = getPlayer(actualDungonRes6).get();

        // assert after movement
        assertEquals(expectedPlayer6, actualPlayer6);

        // create the expected result
        EntityResponse expectedPlayer7 = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(3, 7), false);

        // move player leftward
        DungeonResponse actualDungonRes7 = dmc.tick(Direction.LEFT);
        EntityResponse actualPlayer7 = getPlayer(actualDungonRes7).get();

        // assert after movement
        assertEquals(expectedPlayer7, actualPlayer7);

        // create the expected result
        EntityResponse expectedPlayer8 = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(3, 6), false);

        // move player upward
        DungeonResponse actualDungonRes8 = dmc.tick(Direction.UP);
        EntityResponse actualPlayer8 = getPlayer(actualDungonRes8).get();

        // assert after movement
        assertEquals(expectedPlayer8, actualPlayer8);

        // create the expected result
        EntityResponse expectedPlayer9 = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(3, 5), false);

        // move player upward
        DungeonResponse actualDungonRes9 = dmc.tick(Direction.UP);
        EntityResponse actualPlayer9 = getPlayer(actualDungonRes9).get();

        // assert after movement
        assertEquals(expectedPlayer9, actualPlayer9);

        // create the expected result
        EntityResponse expectedPlayer10 = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 5), false);

        // move player leftward
        DungeonResponse actualDungonRes10 = dmc.tick(Direction.LEFT);
        EntityResponse actualPlayer10 = getPlayer(actualDungonRes10).get();

        // assert after movement
        assertEquals(expectedPlayer10, actualPlayer10);

        // create the expected result
        EntityResponse expectedPlayer11 = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 5), false);

        // move player leftward
        DungeonResponse actualDungonRes11 = dmc.tick(Direction.LEFT);
        EntityResponse actualPlayer11 = getPlayer(actualDungonRes11).get();

        // assert after movement
        assertEquals(expectedPlayer11, actualPlayer11);

        // create the expected result
        EntityResponse expectedPlayer12 = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 4), false);

        // move player upward
        DungeonResponse actualDungonRes12 = dmc.tick(Direction.UP);
        EntityResponse actualPlayer12 = getPlayer(actualDungonRes12).get();

        // assert after movement
        assertEquals(expectedPlayer12, actualPlayer12);

        // create the expected result
        EntityResponse expectedPlayer13 = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 3), false);

        // move player upward
        DungeonResponse actualDungonRes13 = dmc.tick(Direction.UP);
        EntityResponse actualPlayer13 = getPlayer(actualDungonRes13).get();

        // assert after movement
        assertEquals(expectedPlayer13, actualPlayer13);

        // create the expected result
        EntityResponse expectedPlayer14 = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 2), false);

        // move player upward
        DungeonResponse actualDungonRes14 = dmc.tick(Direction.UP);
        EntityResponse actualPlayer14 = getPlayer(actualDungonRes14).get();

        // assert after movement
        assertEquals(expectedPlayer14, actualPlayer14);
    }
}
