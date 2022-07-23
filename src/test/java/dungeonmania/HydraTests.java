package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.Entity;
import static dungeonmania.TestUtils.getEntities;

// public class HydraTests {
//     @Test
//     @DisplayName("Test random movement of hydra")
//     public void randomMovement() {
//         DungeonManiaController dmc;
//         dmc = new DungeonManiaController();
//         // CREATE NEW DUNGEON JSON FOR HYDRA + CONFIG FILE
//         DungeonResponse res = dmc.newGame("d_zombie_toastTest_randomMovement", "c_standard_movement");
//         Position pos = getEntities(res, "hydra").get(0).getPosition();
//         Position previousPos = new Position(pos.getX(), pos.getY());

//         // Assert Random Movement Hydra
//         for (int i = 0; i <= 20; ++i) {
//             res = dmc.tick(Direction.UP);
//             assertNotEquals(previousPos, getEntities(res, "hydra").get(0).getPosition());
//             previousPos = getEntities(res, "hydra").get(0).getPosition();
//         }
//     }
// }
