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

    @Test
    @DisplayName("Test for listing all save files")
    public void testListAllSaves(){
        
        DungeonManiaController dmc = new DungeonManiaController();
        
        dmc.newGame("d_movementTest_testMovementDown", "c_movementTest_testMovementDown");

        // move player downward
        dmc.tick(Direction.DOWN);      
        
        // save game
        dmc.saveGame("game1");
        
        // list all saves
        dmc.allGames();
    }

    @Test
    @DisplayName("Test save and load game - place bomb")
    public void testSaveLoadGamePlaceBomb(){
        
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_bombTest_placeBombRadius2", "c_bombTest_placeBombRadius2");

        // Activate Switch
        res = dmc.tick(Direction.RIGHT);

        // Pick up Bomb
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, getInventory(res, "bomb").size());

        // Place Cardinally Adjacent
        res = dmc.tick(Direction.RIGHT);
        String bombId = getInventory(res, "bomb").get(0).getId();
        res = assertDoesNotThrow(() -> dmc.tick(bombId));

        // Check Bomb exploded with radius 2
        //
        //              Boulder/Switch      Wall            Wall
        //              Bomb                Treasure
        //
        //              Treasure
        assertEquals(0, getEntities(res, "bomb").size());
        assertEquals(0, getEntities(res, "boulder").size());
        assertEquals(0, getEntities(res, "switch").size());
        assertEquals(0, getEntities(res, "wall").size());
        assertEquals(0, getEntities(res, "treasure").size());
        assertEquals(1, getEntities(res, "player").size());   
        
        dmc.saveGame("game2");
        
        //load again
        DungeonManiaController savedDMC = new DungeonManiaController();
        DungeonResponse saveRes = savedDMC.loadGame("game2");        
        
        assertEquals(0, getEntities(saveRes, "bomb").size());
        assertEquals(0, getEntities(saveRes, "boulder").size());
        assertEquals(0, getEntities(saveRes, "switch").size());
        assertEquals(0, getEntities(saveRes, "wall").size());
        assertEquals(0, getEntities(saveRes, "treasure").size());
        assertEquals(1, getEntities(saveRes, "player").size());  
    }   
    
    @Test
    @DisplayName("Test save and load game -player use key and walk through open dooor")
    public void testSaveLoadGamePlayerUseKeyWalk(){
        
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_DoorsKeysTest_useKeyWalkThroughOpenDoor", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");

        // pick up key
        res = dmc.tick(Direction.RIGHT);
        Position pos = getEntities(res, "player").get(0).getPosition();
        assertEquals(1, getInventory(res, "key").size());

        // walk through door and check key is gone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, getInventory(res, "key").size());
        assertNotEquals(pos, getEntities(res, "player").get(0).getPosition());
        
        dmc.saveGame("game3");
        
        //load again
        DungeonManiaController savedDMC = new DungeonManiaController();
        DungeonResponse saveRes = savedDMC.loadGame("game3");        
        
        assertEquals(0, getInventory(saveRes, "key").size());
        assertNotEquals(pos, getEntities(saveRes, "player").get(0).getPosition());
    }   
    
    @Test
    @DisplayName("Test save and load game - Spider Movement")
    public void testSaveLoadGameSpiderMove(){
        
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_basicMovement", "c_spiderTest_basicMovement");
        Position pos = getEntities(res, "spider").get(0).getPosition();

        List<Position> movementTrajectory = new ArrayList<Position>();
        int x = pos.getX();
        int y = pos.getY();
        int nextPositionElement = 0;
        movementTrajectory.add(new Position(x  , y-1));
        movementTrajectory.add(new Position(x+1, y-1));
        movementTrajectory.add(new Position(x+1, y));
        movementTrajectory.add(new Position(x+1, y+1));
        movementTrajectory.add(new Position(x  , y+1));
        movementTrajectory.add(new Position(x-1, y+1));
        movementTrajectory.add(new Position(x-1, y));
        movementTrajectory.add(new Position(x-1, y-1));
        
        Position lastestPos = null;

        // Assert Circular Movement of Spider
        for (int i = 0; i <= 20; ++i) {
            res = dmc.tick(Direction.UP);
            
            lastestPos = movementTrajectory.get(nextPositionElement);
            assertEquals(movementTrajectory.get(nextPositionElement), getEntities(res, "spider").get(0).getPosition());
            
            nextPositionElement++;
            if (nextPositionElement == 8){
                nextPositionElement = 0;
            }
        }
        
        dmc.saveGame("game4");
        
        //load again
        DungeonManiaController savedDMC = new DungeonManiaController();
        DungeonResponse saveRes = savedDMC.loadGame("game4");        
        
        // Assert final position of Spider
        assertEquals(lastestPos, getEntities(saveRes, "spider").get(0).getPosition());
    }   
    
    @Test
    @DisplayName("Test save and load game - move exit")
    public void testSaveLoadGameMoveExit(){
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexGoalsTest_andAll", "c_complexGoalsTest_andAll");

        assertTrue(getGoals(res).contains(":exit"));
        assertTrue(getGoals(res).contains(":treasure"));
        assertTrue(getGoals(res).contains(":boulders"));
        assertTrue(getGoals(res).contains(":enemies"));

        // kill spider
        res = dmc.tick(Direction.RIGHT);
        assertTrue(getGoals(res).contains(":exit"));
        assertTrue(getGoals(res).contains(":treasure"));
        assertTrue(getGoals(res).contains(":boulders"));
        assertFalse(getGoals(res).contains(":enemies"));

        // move boulder onto switch
        res = dmc.tick(Direction.RIGHT);
        assertTrue(getGoals(res).contains(":exit"));
        assertTrue(getGoals(res).contains(":treasure"));
        assertFalse(getGoals(res).contains(":boulders"));
        assertFalse(getGoals(res).contains(":enemies"));

        // pickup treasure
        res = dmc.tick(Direction.DOWN);
        assertTrue(getGoals(res).contains(":exit"));
        assertFalse(getGoals(res).contains(":treasure"));
        assertFalse(getGoals(res).contains(":boulders"));
        assertFalse(getGoals(res).contains(":enemies"));

        // move to exit
        res = dmc.tick(Direction.DOWN);
        assertEquals("", getGoals(res));
        
        dmc.saveGame("game4");
        
        //load again
        DungeonManiaController savedDMC = new DungeonManiaController();
        DungeonResponse saveRes = savedDMC.loadGame("game4");
        
        assertEquals("", getGoals(saveRes));
    }
}