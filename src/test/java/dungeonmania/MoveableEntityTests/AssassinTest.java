package dungeonmania.MoveableEntityTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import static dungeonmania.TestUtils.getEntities;
import static org.junit.jupiter.api.Assertions.assertEquals;

// import java.util.ArrayList;
// import java.util.List;
// public class AssassinTest {
//     @Test
//     @DisplayName("Test basic movement of Assassin")
//     public void basicMovement() {
//         DungeonManiaController dmc = new DungeonManiaController();
//         DungeonResponse res = dmc.newGame("d_assassinSimple", "c_M3_config");
//         Position pos = getEntities(res, "assassin").get(0).getPosition();
//         System.out.println(pos);
        
//         List<Position> movementTrajectory = new ArrayList<Position>();
//         int x = pos.getX();
//         int y = pos.getY();
//         int nextPositionElement = 0;
//         movementTrajectory.add(new Position(x  , y-1));
//         movementTrajectory.add(new Position(x  , y-2));
//         movementTrajectory.add(new Position(x  , y-3));
        
//         // Assert Movement for Assassin
//         for (int i = 0; i <= 2; ++i) {
//             res = dmc.tick(Direction.UP);
//             assertEquals(movementTrajectory.get(nextPositionElement), getEntities(res, "assassin").get(0).getPosition());
//             nextPositionElement++;
//         }
//     }
// }
