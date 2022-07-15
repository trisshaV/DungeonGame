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

public class MercenaryTest {

    
    @Test
    @DisplayName("Test basic movement of mercenary")
    public void basicMovement() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_movement", "c_standard_movement");
        Position pos = getEntities(res, "mercenary").get(0).getPosition();
        
        List<Position> movementTrajectory = new ArrayList<Position>();
        int x = pos.getX();
        int y = pos.getY();
        int nextPositionElement = 0;
        movementTrajectory.add(new Position(x  , y-1));
        movementTrajectory.add(new Position(x  , y-2));
        movementTrajectory.add(new Position(x  , y-3));
        
        // Assert Line Movement of Mercenary
        for (int i = 0; i <= 2; ++i) {
            res = dmc.tick(Direction.UP);
            assertEquals(movementTrajectory.get(nextPositionElement), getEntities(res, "mercenary").get(0).getPosition());
            nextPositionElement++;      
        }
    }
        

    
    @Test
    @DisplayName("Test basic movement of mercenary chasing player")
    public void ChaseMovement() {
    
        /*
        *  [  ]   [  ]  wall  wall  wall  wall  wall
        *  [  ]   [  ]  wall  play  [  ]  [  ]  wall
        *  [  ]   [  ]  wall  merc  wall  [  ]  wall
        *  [  ]   [  ]  wall  [  ]  [  ]  [  ]  wall
        *  [  ]   [  ]  wall  wall  wall  wall  wall
        *  [  ]   [  ]  [  ]  [  ]  [  ]  [  ]  [  ]
        *  [  ]   [  ]  [  ]  [  ]  [  ]  [  ]  exit
        */
    
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_chase", "c_standard_movement");
        Position pos = getEntities(res, "mercenary").get(0).getPosition();
        
        // Assert Chase Movement of Mercenary
        Position finalPosition = new Position(pos.getX(), pos.getY() - 1);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(finalPosition, getEntities(res, "mercenary").get(0).getPosition());
        
        finalPosition = new Position(pos.getX() + 2, pos.getY() - 1);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        
        finalPosition = new Position(pos.getX() + 1, pos.getY() + 1);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        assertEquals(finalPosition, getEntities(res, "mercenary").get(0).getPosition());
    }
    
}
