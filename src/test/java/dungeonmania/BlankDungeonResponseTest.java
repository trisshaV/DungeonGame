package dungeonmania;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;

import static dungeonmania.TestUtils.countEntityOfType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlankDungeonResponseTest {
    /**
     * This is more or less a placeholder test - this should be changed
     */
    @Test
    @DisplayName("Test the game starts")
    public void testBlankResponse() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse resp = dmc.newGame("d_DoorsKeysTest_useKeyWalkThroughOpenDoor", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");

        assertEquals(1, countEntityOfType(resp, "player"));
        assertEquals(1, countEntityOfType(resp, "door"));
        assertEquals(1, countEntityOfType(resp, "exit"));
        assertEquals(1, countEntityOfType(resp, "key"));
    }
}