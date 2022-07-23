package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static dungeonmania.TestUtils.getPlayer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PersistenceTests {
    @Test
    @DisplayName("Test save and load game (player location)")
    public void testSaveLoadGameLocation(){
        
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "c_movementTest_testMovementDown");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 2), false);

        // move player downward
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);        
        
        dmc.saveGame("game1");
        
        //load again
        dmc = new DungeonManiaController();
        initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "c_movementTest_testMovementDown");
        initPlayer = getPlayer(initDungonRes).get();
        DungeonResponse res = dmc.loadGame("game1");        
        
        //test the play again
        EntityResponse savedPlayer = getPlayer(res).get();
        
        // assert after loading
        assertEquals(expectedPlayer.getPosition(), savedPlayer.getPosition());
        
    }
    
    
    
    
    @Test
    @DisplayName("Test save and load game (player inventory)")
    public void testSaveLoadGameInventory(){
        
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "c_movementTest_testMovementDown");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 2), false);

        // move player downward
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);        
        
        dmc.saveGame("game1");
        
        //load again
        dmc = new DungeonManiaController();
        initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "c_movementTest_testMovementDown");
        initPlayer = getPlayer(initDungonRes).get();
        DungeonResponse res = dmc.loadGame("game1");        
        
        //test the play again
        EntityResponse savedPlayer = getPlayer(res).get();
        
        // assert after loading
        assertEquals(expectedPlayer.getPosition(), savedPlayer.getPosition());
    }

    @Test
    @DisplayName("Test throws IllegalArgumentException if load file does not exist")
    public void testThrowsExceptionForNoLoadFile() {
        assertThrows(IllegalArgumentException.class, () -> {
            DungeonManiaController dmc = new DungeonManiaController();
            dmc.loadGame("game2");
        });
    }
}