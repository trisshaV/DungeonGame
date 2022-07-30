package dungeonmania.MoveableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.DungeonManiaController;
import dungeonmania.Entity;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.countEntityOfType;

public class HydraTests {
    @Test
    @DisplayName("Test random movement of hydra")
    public void randomMovement() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        // CREATE NEW DUNGEON JSON FOR HYDRA + CONFIG FILE
        DungeonResponse res = dmc.newGame("d_hydraSimple", "c_M3_config");
        Position pos = getEntities(res, "hydra").get(0).getPosition();
        Position previousPos = new Position(pos.getX(), pos.getY());

        // Assert Random Movement Hydra
        for (int i = 0; i <= 20; ++i) {
            res = dmc.tick(Direction.UP);
            assertNotEquals(previousPos, getEntities(res, "hydra").get(0).getPosition());
            previousPos = getEntities(res, "hydra").get(0).getPosition();
        }
    }

    @Test
    @DisplayName("Test restricted movement for hydras")
    public void restrictedMovement() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_hydraMovementTest_againstBlocking", "c_M3_config");
        Position pos1 = getEntities(res, "hydra").get(0).getPosition();
        Position pos2 = getEntities(res, "hydra").get(1).getPosition();
        
        Position StationaryPos1 = new Position(pos1.getX(), pos1.getY());
        Position StationaryPos2 = new Position(pos2.getX(), pos2.getY());

        // Assert Random Movement of Zombie Toasts as stationary
        // Boulders and walls restrict Zombie Toast movement
        for (int i = 0; i <= 20; ++i) {
            res = dmc.tick(Direction.UP);
            assertEquals(StationaryPos1, getEntities(res, "hydra").get(0).getPosition());
            assertEquals(StationaryPos2, getEntities(res, "hydra").get(1).getPosition());
        }
    }

    @Test
    @DisplayName("Test Battle for Hydra - 100% chance of winning")
    public void HydraBattleAlwaysWinning() {
        /*
         *  wall  exit  wall  
         *  wall  play  wall  
         *  wall  [  ]  wall  
         *  wall  hydr  wall  
         *  wall  wall  wall 
         */
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_battleTests_basicHydra", "c_hydra_always_wins");
        res = dmc.tick(Direction.DOWN);

        assertEquals(1, countEntityOfType(res, "hydra"));
        assertEquals(0, countEntityOfType(res, "player"));
    }

    @Test
    @DisplayName("Test Battle for Hydra - 100% chance of Losing")
    public void HydraBattleAlwaysLoses() {
        /*
         *  wall  exit  wall  
         *  wall  play  wall  
         *  wall  [  ]  wall  
         *  wall  hydr  wall  
         *  wall  wall  wall 
         */
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_battleTests_basicHydra", "c_hydra_always_loses");
        res = dmc.tick(Direction.DOWN);

        assertEquals(0, countEntityOfType(res, "hydra"));
        assertEquals(1, countEntityOfType(res, "player"));
    }


    
}
