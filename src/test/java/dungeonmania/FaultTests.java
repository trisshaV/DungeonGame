package dungeonmania;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertThrows;


//import dungeonmania.response.models.DungeonResponse;
//import dungeonmania.response.models.EntityResponse;

//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

//import static org.junit.jupiter.api.Assertions.assertTrue;

//import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
/*import static dungeonmania.TestUtils.getGoals;
import static dungeonmania.TestUtils.countEntityOfType;
import static dungeonmania.TestUtils.getValueFromConfigFile;*/

/*import java.util.ArrayList;
import java.util.List;

//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;

import dungeonmania.response.models.BattleResponse;
//import dungeonmania.response.models.DungeonResponse;
//import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
//import dungeonmania.util.Direction;
//import dungeonmania.util.Position;*/

public class FaultTests {
    @Test
    @DisplayName("Test throws IllegalArgumentException if dungeon JSON file does not exist")
    public void testThrowsExceptionForDungeonJSONFileNotExist() {
        assertThrows(IllegalArgumentException.class, () -> {
            DungeonManiaController dmc = new DungeonManiaController();
            dmc.newGame("d_fake_dungeon", "c_movementTest_testMovementDown");
        });
    }

    @Test
    @DisplayName("Test throws IllegalArgumentException if config JSON file does not exist")
    public void testThrowsExceptionForConfigJSONFileNotExist() {
        assertThrows(IllegalArgumentException.class, () -> {
            DungeonManiaController dmc = new DungeonManiaController();
            dmc.newGame("d_movementTest_testMovementDown", "c_fake_config");
        });
    }

    @Test
    @DisplayName("Test player only picks up one key")
    public void testPlayerPickupOneKeyOnly() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_twoKeyPickup", "c_standard_movement");

        // pick up key
        res = dmc.tick(Direction.RIGHT);
        Position pos = getEntities(res, "player").get(0).getPosition();
        assertEquals(1, getInventory(res, "key").size());

        // tries to pickup another but CANNOT
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "key").size());
        assertNotEquals(pos, getEntities(res, "player").get(0).getPosition());
    }
}
