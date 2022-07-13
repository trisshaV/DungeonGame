package dungeonmania;

import dungeonmania.static_entity.FloorSwitch;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getGoals;
import static dungeonmania.TestUtils.countEntityOfType;
import static dungeonmania.TestUtils.getValueFromConfigFile;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class FloorSwitchTests {
    @Test
    @DisplayName("Push Boulder onto Floor Switch")
    public void testActivateFloorSwitch() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_activateFloorSwitch", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        res = dmc.tick(Direction.DOWN);
        Position posSwitch = getEntities(res, "switch").get(0).getPosition();
        Position posBoulder = getEntities(res, "boulder").get(0).getPosition();
        assertEquals(posSwitch, posBoulder);
        assertEquals(true, dmc.switchActive());
        
    }
    @Test
    @DisplayName("Floor Switch Not Active")
    public void testFloorSwitchNotActive() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_activateFloorSwitch", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        assertEquals(false, dmc.switchActive());
    }
    @Test
    @DisplayName("Push Boulder off Switch")
    public void testDeactivateFloorSwitch() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_activateFloorSwitch", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        assertEquals(false, dmc.switchActive());

    }
}
