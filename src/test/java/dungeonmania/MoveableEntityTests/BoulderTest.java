package dungeonmania.MoveableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static dungeonmania.TestUtils.getEntities;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static dungeonmania.TestUtils.countEntityOfType;

public class BoulderTest {
    
    @Test
    @DisplayName("Test construction of boulder")
    public void testConstructor() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_boulderTest_move", "c_standard_movement");
        // Assert two boulders exist in the dungeon
        assertEquals(2, countEntityOfType(res, "boulder"));
    }
    @Test
    @DisplayName("Test moving one boulder")
    public void basicMovement() {
    
        
        /*
         *  [  ]   [  ]  [  ]  [  ]  [  ]  [  ] 
         *  [  ]   play  [  ]  bold  [  ]  bold 
         *  [  ]   [  ]  [  ]  wall  [  ]  [  ]  
         *  [  ]   [  ]  [  ]  [  ]  [  ]  [  ]
         *  [  ]   [  ]  [  ]  [  ]  [  ]  [  ]
         *  [  ]   [  ]  [  ]  [  ]  [  ]  exit
         */
    
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_boulderTest_move", "c_standard_movement");

        // Move boulder to the right by one
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        List<EntityResponse> temp = getEntities(res, "boulder");
        List<Position> actualResult = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            EntityResponse curr = temp.get(i);
            actualResult.add(curr.getPosition());
        }

        List<Position> expectedResult = new ArrayList<>();
        expectedResult.add(new Position(4,1));
        expectedResult.add(new Position(5,1));

        assertTrue(expectedResult.containsAll(actualResult));
    }
    @Test
    @DisplayName("Test player inability to move a boulder past a wall")
    public void boulderWallBlock() {
    
        
        /*
         *  [  ]   [  ]  [  ]  [  ]  [  ]  [  ] 
         *  [  ]   play  [  ]  bold  [  ]  bold 
         *  [  ]   [  ]  [  ]  wall  [  ]  [  ]  
         *  [  ]   [  ]  [  ]  [  ]  [  ]  [  ]
         *  [  ]   [  ]  [  ]  [  ]  [  ]  [  ]
         *  [  ]   [  ]  [  ]  [  ]  [  ]  exit
         */
    
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_boulderTest_move", "c_standard_movement");

        // Move boulder to the right by one
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);

        List<EntityResponse> temp = getEntities(res, "boulder");
        List<Position> actualResult = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            EntityResponse curr = temp.get(i);
            actualResult.add(curr.getPosition());
        }

        List<Position> expectedResult = new ArrayList<>();
        expectedResult.add(new Position(3,1));
        expectedResult.add(new Position(5,1));

        assertTrue(expectedResult.containsAll(actualResult));
    }

    @Test
    @DisplayName("Test player inability to move a boulder past another boulder")
    public void twoBoulderBlock() {
    
        /*
         *  [  ]   [  ]  [  ]  [  ]  [  ]  [  ] 
         *  [  ]   play  [  ]  bold  [  ]  bold 
         *  [  ]   [  ]  [  ]  wall  [  ]  [  ]  
         *  [  ]   [  ]  [  ]  [  ]  [  ]  [  ]
         *  [  ]   [  ]  [  ]  [  ]  [  ]  [  ]
         *  [  ]   [  ]  [  ]  [  ]  [  ]  exit
         */
    
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_boulderTest_move", "c_standard_movement");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        List<EntityResponse> temp = getEntities(res, "boulder");
        List<Position> actualResult = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            EntityResponse curr = temp.get(i);
            actualResult.add(curr.getPosition());
        }

        List<Position> expectedResult = new ArrayList<>();
        expectedResult.add(new Position(4,1));
        expectedResult.add(new Position(5,1));

        assertTrue(expectedResult.containsAll(actualResult));
    }

    @Test
    @DisplayName("Moving different boulders multiple times")
    public void complexBoulderMove() {
    
        /*
         *  [  ]   [  ]  [  ]  [  ]  [  ]  [  ] 
         *  [  ]   play  [  ]  bold  [  ]  bold 
         *  [  ]   [  ]  [  ]  wall  [  ]  [  ]  
         *  [  ]   [  ]  [  ]  [  ]  [  ]  [  ]
         *  [  ]   [  ]  [  ]  [  ]  [  ]  [  ]
         *  [  ]   [  ]  [  ]  [  ]  [  ]  exit
         */
    
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_boulderTest_move", "c_standard_movement");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);



        List<EntityResponse> temp = getEntities(res, "boulder");
        List<Position> actualResult = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            EntityResponse curr = temp.get(i);
            actualResult.add(curr.getPosition());
        }

        List<Position> expectedResult = new ArrayList<>();
        expectedResult.add(new Position(1,1));
        expectedResult.add(new Position(5,3));

        assertTrue(expectedResult.containsAll(actualResult));
    }
    
}
