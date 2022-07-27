package dungeonmania.MoveableEntityTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import static dungeonmania.TestUtils.getEntities;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
public class AssassinTest {
    @Test
    @DisplayName("Test basic movement of Assassin")
    public void basicMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinSimple", "c_M3_config");
        Position pos = getEntities(res, "assassin").get(0).getPosition();
        System.out.println(pos);
        
        List<Position> movementTrajectory = new ArrayList<Position>();
        int x = pos.getX();
        int y = pos.getY();
        int nextPositionElement = 0;
        movementTrajectory.add(new Position(x-1  , y));
        movementTrajectory.add(new Position(x-2  , y));
        movementTrajectory.add(new Position(x-3  , y));
        
        // Assert Movement for Assassin
        for (int i = 0; i <= 2; ++i) {
            res = dmc.tick(Direction.UP);
            assertEquals(movementTrajectory.get(nextPositionElement), getEntities(res, "assassin").get(0).getPosition());
            nextPositionElement++;
        }
    }

    @Test
    @DisplayName("Test invincible movement with Assassin")
    public void testInvincibleMovement() throws InvalidActionException {
         /*
        *  [  ] [  ]  wall wall wall [  ]
        *  wall invin play [  ] ass  [  ]
        *  [  ] [  ]  wall wall wall [  ]
        */
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinInvincible", "c_M3_config");
        EntityResponse potionOne = getEntities(res, "invincibility_potion").get(0);
        Position pos = getEntities(res, "assassin").get(0).getPosition();
        
        // Pick up potion;
        res = dmc.tick(Direction.LEFT);
        int x = pos.getX();
        int y = pos.getY();
        assertEquals(new Position(x-1, y), getEntities(res, "assassin").get(0).getPosition());

        // Consume potion
        res = dmc.tick(potionOne.getId());
        
        // Assert Invincible Movement of Mercenary
        assertEquals(new Position(x, y), getEntities(res, "assassin").get(0).getPosition());

    }

    @Test
    @DisplayName("Test invisible movement of Assassin when player within Recon Radius")
    public void testInvisibleReconRadius() throws IllegalArgumentException, InvalidActionException {
         /*
        *  wall   [  ]  wall  [  ] 
        *  play   wall  [  ]  wall 
        *  invis  [  ]  [  ]   ass 
        *  [  ]   wall  [  ]  wall  
        *  [  ]   [  ]  wall  [  ]
        */
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinInvisibleClose", "c_M3_config");
        EntityResponse potionOne = getEntities(res, "invisibility_potion").get(0);
        Position pos = getEntities(res, "assassin").get(0).getPosition();

        // Pick up potion and consume;
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(potionOne.getId());
        
        int x = pos.getX();
        int y = pos.getY();
        assertEquals(new Position(x-2, y), getEntities(res, "assassin").get(0).getPosition());

        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(x-3, y), getEntities(res, "assassin").get(0).getPosition());
    }

    @Test
    @DisplayName("Test invisible movement of Assassin when player is not within Recon Radius")
    public void testRandomMovement() throws IllegalArgumentException, InvalidActionException {
         /*
        *  wall   [  ]  wall  [  ] [  ]
        *  play   wall  [  ]  wall [  ]
        *  invis  [  ]  [  ]  [  ] ass
        *  [  ]   wall  [  ]  wall [  ]
        *  [  ]   [  ]  wall  [  ] [  ]
        */
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinInvisibleFar", "c_assassinFarRecon");
        EntityResponse potionOne = getEntities(res, "invisibility_potion").get(0);
        Position pos = getEntities(res, "assassin").get(0).getPosition();

        // Pick up potion and consume;
        res = dmc.tick(Direction.DOWN);

        int x = pos.getX();
        int y = pos.getY();
        assertEquals(new Position(x-1, y), getEntities(res, "assassin").get(0).getPosition());

        res = dmc.tick(potionOne.getId());
        
        pos = getEntities(res, "assassin").get(0).getPosition();
        Position previousPos = new Position(pos.getX(), pos.getY());
        // Assert Random Movement of Assassin once player has consumed potion
        for (int i = 0; i <= 5; ++i) {
            res = dmc.tick(Direction.DOWN);
            assertNotEquals(previousPos, getEntities(res, "assassin").get(0).getPosition());
            previousPos = getEntities(res, "assassin").get(0).getPosition();
        }
    }

    // | BELOW ARE BRIBERY TESTS |

    @Test
    @DisplayName("Interact Assassin is too Far")
    public void testInteractAssassinNotInRange() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinSimple", "c_assassin_never_bribed");
        assertThrows(InvalidActionException.class , () ->  dmc.interact(getEntities(dmc.tick(Direction.RIGHT), "assassin").get(0).getId()));
    }

    @Test
    @DisplayName("Interact Assassin No Coins")
    public void testInteractAssassinNoCoins() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinFailBribe", "c_assassin_never_bribed");
        assertThrows(InvalidActionException.class , () ->  dmc.interact(getEntities(res, "assassin").get(0).getId()));
    }

    // Good Test for Frontend
    @Test
    @DisplayName("Interact Assassin Impossible Bribe")
    public void testInteractAssassinImpossibleBribe() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinSimple", "c_assassin_never_bribed");
        assertThrows(InvalidActionException.class , () ->  dmc.interact(getEntities(dmc.tick(Direction.RIGHT), "assassin").get(0).getId()));
    }

    @Test
    @DisplayName("Bribe Assassin Valid")
    public void testInteractAssasinValid() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinSimple", "c_assassinFarRecon");
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        String id = getEntities(res, "assassin").get(0).getId();
        res = dmc.interact(id);
        assertEquals("FRIENDLY", dmc.getAssassinStatus());
        assertEquals(0, getEntities(res, "treasure").size());
    }

    @Test
    @DisplayName("Bribe Assassin with Far Bribe Radius")
    public void testInteractAssasinValidRadius() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinSimple", "c_assassinFarRecon");
        res = dmc.tick(Direction.RIGHT);
        String id = getEntities(res, "assassin").get(0).getId();
        res = dmc.interact(id);
        assertEquals("FRIENDLY", dmc.getAssassinStatus());
        assertEquals(2, getEntities(res, "treasure").size());
    }

}
