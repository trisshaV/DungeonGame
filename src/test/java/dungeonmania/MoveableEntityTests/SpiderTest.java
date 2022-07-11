package dungeonmania.MoveableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static dungeonmania.TestUtils.getEntities;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SpiderTest {
    /*
    @Test
    @DisplayName("Test basic movement of spiders")
    public void basicMovement() {
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
        
        // Assert Circular Movement of Spider
        for (int i = 0; i <= 20; ++i) {
            res = dmc.tick(Direction.UP);
            assertEquals(movementTrajectory.get(nextPositionElement), getEntities(res, "spider").get(0).getPosition());
            
            nextPositionElement++;      
            if (nextPositionElement == 8){
                nextPositionElement = 0;
            }
        }
    }
    
    @Test
    @DisplayName("Test basic movement of spiders with boulder")
    public void basicMovementBoulderCycle() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_boulderCycle", "c_spiderTest_basicMovement");
        Position pos = getEntities(res, "spider").get(0).getPosition();
        
        List<Position> movementTrajectory = new ArrayList<Position>();
        int x = pos.getX();
        int y = pos.getY();
        int nextPositionElement = 0;
        movementTrajectory.add(new Position(x  , y-1));
        movementTrajectory.add(new Position(x+1, y-1));
        movementTrajectory.add(new Position(x+1, y));
        movementTrajectory.add(new Position(x+1, y+1));
        movementTrajectory.add(new Position(x+1, y));
        movementTrajectory.add(new Position(x+1, y-1));
        movementTrajectory.add(new Position(x  , y-1));
        
        // Assert Circular Movement of Spider with reversal
        for (int i = 0; i <= 6; ++i) {
            res = dmc.tick(Direction.UP);
            assertEquals(movementTrajectory.get(nextPositionElement), getEntities(res, "spider").get(0).getPosition());
            nextPositionElement++;      
        }
    }

    @Test
    @DisplayName("Test basic movement of spiders with boulder blocking up motion, spider remains stationary")
    public void basicMovementBoulderBlockCycle() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_boulderBlockCycle", "c_spiderTest_basicMovement");
        Position pos = getEntities(res, "spider").get(0).getPosition();
        
        List<Position> movementTrajectory = new ArrayList<Position>();
        int x = pos.getX();
        int y = pos.getY();
        int nextPositionElement = 0;
        movementTrajectory.add(new Position(x  , y));
        movementTrajectory.add(new Position(x  , y));
        
        // Assert Spider with no movement due to boulder
        for (int i = 0; i <= 1; ++i) {
            res = dmc.tick(Direction.UP);
            assertEquals(movementTrajectory.get(nextPositionElement), getEntities(res, "spider").get(0).getPosition());
            nextPositionElement++;      
        }
    }

    
    @Test
    @DisplayName("Test basic movement of spiders with boulder which is cleared by Player")
    public void basicMovementClearBoulder() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_boulderCycle", "c_spiderTest_basicMovement");
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
        
        // Assert Circular Movement of Spider 
        for (int i = 0; i <= 20; ++i) {
            res = dmc.tick(Direction.LEFT);
            assertEquals(movementTrajectory.get(nextPositionElement), getEntities(res, "spider").get(0).getPosition());
            
            nextPositionElement++;      
            if (nextPositionElement == 8){
                nextPositionElement = 0;
            }
        }
    }
    */
}
