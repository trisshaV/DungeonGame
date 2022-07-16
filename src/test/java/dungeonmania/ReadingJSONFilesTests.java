package dungeonmania;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReadingJSONFilesTests {
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
}