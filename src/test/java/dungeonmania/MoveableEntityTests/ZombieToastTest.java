package dungeonmania.MoveableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import javax.xml.stream.events.EntityReference;

import static dungeonmania.TestUtils.getEntities;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.Entity;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ZombieToastTest {
    
    @Test
    @DisplayName("Test random movement of zombie toast")
    public void randomMovement() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombie_toastTest_randomMovement", "c_standard_movement");
        Position pos = getEntities(res, "zombie_toast").get(0).getPosition();
        Position previousPos = new Position(pos.getX(), pos.getY());

        // Assert Random Movement of Zombie toast
        for (int i = 0; i <= 20; ++i) {
            res = dmc.tick(Direction.UP);
            System.out.print(previousPos);
            assertNotEquals(previousPos, getEntities(res, "zombie_toast").get(0).getPosition());
            previousPos = getEntities(res, "zombie_toast").get(0).getPosition();
        }
    }

    @Test
    @DisplayName("Test random movement of two zombie toasts against boulders and walls")
    public void restrictedMovement() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombie_toastTest_restrictedMovement", "c_standard_movement");
        Position pos1 = getEntities(res, "zombie_toast").get(0).getPosition();
        Position pos2 = getEntities(res, "zombie_toast").get(1).getPosition();
        
        Position StationaryPos1 = new Position(pos1.getX(), pos1.getY());
        Position StationaryPos2 = new Position(pos2.getX(), pos2.getY());

        // Assert Random Movement of Zombie Toasts as stationary
        // Boulders and walls restrict Zombie Toast movement
        for (int i = 0; i <= 20; ++i) {
            res = dmc.tick(Direction.UP);
            assertEquals(StationaryPos1, getEntities(res, "zombie_toast").get(0).getPosition());
            assertEquals(StationaryPos2, getEntities(res, "zombie_toast").get(1).getPosition());
        }
    }

    @Test
    @DisplayName("Test random movement of zombie toast with portal")
    public void randomMovementPortal() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombie_toastTest_portal", "c_standard_movement");
        Position pos = getEntities(res, "zombie_toast").get(0).getPosition();
        
        Position nextPos = new Position(pos.getX(), pos.getY() + 1);

        // Assert Random Movement of Zombie Toasts
        // No teleport movement from portal
        res = dmc.tick(Direction.UP);
        assertEquals(nextPos, getEntities(res, "zombie_toast").get(0).getPosition());
    }
     
}
