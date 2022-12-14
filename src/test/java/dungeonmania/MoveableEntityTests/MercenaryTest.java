package dungeonmania.MoveableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static dungeonmania.TestUtils.getEntities;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
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
    public void testChaseMovement() {
    
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

    @Test
    @DisplayName("Test random movement of mercenary")
    public void testRandomMovement() throws IllegalArgumentException, InvalidActionException {
         /*
        *  wall   [  ]  wall  [  ] 
        *  play   wall  [  ]  wall 
        *  invis  wall  merc  wall 
        *  [  ]   wall  [  ]  wall  
        *  [  ]   [  ]  wall  [  ]
        */
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenary_RandomMovement", "c_UnbrokenWeaponsWithSpiderTests");
        EntityResponse potionOne = getEntities(res, "invisibility_potion").get(0);
        // Pick up potion and consume;
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(potionOne.getId());

        Position pos = getEntities(res, "mercenary").get(0).getPosition();
        Position previousPos = new Position(pos.getX(), pos.getY());

        // Assert Random Movement of Mercenary
        for (int i = 0; i <= 1; ++i) {
            res = dmc.tick(Direction.UP);
            assertNotEquals(previousPos, getEntities(res, "mercenary").get(0).getPosition());
            previousPos = getEntities(res, "mercenary").get(0).getPosition();
        }

    }

    @Test
    @DisplayName("Test invincible movement with msingle mercenary")
    public void testInvincibleMovementSingle() throws IllegalArgumentException, InvalidActionException {
         /*
        *  [  ] [  ]  wall wall wall
        *  wall invin play [  ] merc
        *  [  ] [  ]  wall wall wall
        */
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryInvincible", "c_UnbrokenWeaponsWithSpiderTests");
        EntityResponse potionOne = getEntities(res, "invincibility_potion").get(0);
        Position pos = getEntities(res, "mercenary").get(0).getPosition();
        
        // Pick up potion;
        res = dmc.tick(Direction.LEFT);
        int x = pos.getX();
        int y = pos.getY();
        assertEquals(new Position(x-1, y), getEntities(res, "mercenary").get(0).getPosition());

        // Consume potion
        res = dmc.tick(potionOne.getId());
        

        // Assert Invincible Movement of Mercenary
        assertEquals(new Position(x, y), getEntities(res, "mercenary").get(0).getPosition());

    }

    @Test
    @DisplayName("Test invincible movement of mercenary")
    public void testInvincibleMovementMultiple() throws IllegalArgumentException, InvalidActionException {
         /*
        *  wall wall wall  wall  wall wall wall
        *  wall [  ] wall  [  ]  merc [  ] wall
        *  wall wall wall  [  ]  wall merc wall
        *  wall [  ] [  ]  invin play [  ] wall
        *  wall merc wall  [  ]  wall wall wall
        *  wall [  ] merc  [  ]  wall [  ] wall
        *  wall wall  wall  wall wall wall wall
        */
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryInvincible", "c_UnbrokenWeaponsWithSpiderTests");
        EntityResponse potionOne = getEntities(res, "invincibility_potion").get(0);
        
        // Pick up potion;
        res = dmc.tick(Direction.LEFT);
        Position pos = getEntities(res, "player").get(0).getPosition();
        int x = pos.getX();
        int y = pos.getY();

        // Consume potion
        res = dmc.tick(potionOne.getId());
        
        // 4 positions which should be empty
        Position pos1 = new Position(x-2, y );
        Position pos2 = new Position(x+2, y );
        Position pos3 = new Position( x ,y+2);
        Position pos4 = new Position( x ,y-2);

        // Assert Invincible Movement of Mercenary
        List <EntityResponse> listOfMercenaries = getEntities(res, "mercenary"); 
        listOfMercenaries.stream().forEach(
            mercenary -> {
                Position mercPos = mercenary.getPosition();
                assertNotEquals(pos1, mercPos);
                assertNotEquals(pos2, mercPos);
                assertNotEquals(pos3, mercPos);
                assertNotEquals(pos4, mercPos);
            }
        );


    }
    
    
}
