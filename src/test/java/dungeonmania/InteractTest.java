package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class InteractTest {
    @Test
    @DisplayName("Interact Spawner Valid")
    public void testInteractSpawner() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_interactSpawnerValid", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        res = dmc.tick(Direction.RIGHT);
        res = dmc.interact(getEntities(res, "zombie_toast_spawner").get(0).getId());
        assertEquals(0, getEntities(res, "zombie_toast_spawner").size());
    }
    @Test 
    @DisplayName("Illegal Argument") 
    public void testInvalidArgument() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_interactSpawnerValid", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        assertThrows(IllegalArgumentException.class , () -> dmc.interact("a"));
    }
    @Test
    @DisplayName("Interact Spawner No Weapon")
    public void testInteractSpawnerNoWeapon() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_interactSpawnerNoWeapon", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        assertThrows(InvalidActionException.class, () -> dmc.interact(getEntities(dmc.tick(Direction.RIGHT), "zombie_toast_spawner").get(0).getId()));
    }
    @Test
    @DisplayName("Interact Spawner Not Adjacent")
    public void testInteractSpawnerNotAdjacent() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_interactSpawnerNotAdjacent", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        assertThrows(InvalidActionException.class, () -> dmc.interact(getEntities(dmc.tick(Direction.RIGHT), "zombie_toast_spawner").get(0).getId()));
        
    }
}
