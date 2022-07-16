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

public class ZombieToastSpawnerTests {
    @Test
    @DisplayName("Spawn zombie toast after correct amount of ticks")
    public void testSpawnZombieToast() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieToastSpawnerSimple", "c_zombieToastSpawner");
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(6, 5), getEntities(res, "zombie_toast").get(0).getPosition());
    }
    @Test 
    @DisplayName("Spawn zombie toast in open square when cardinally blocked left")
    public void testSpawnZombieToastBlocked() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieToastBlocked", "c_zombieToastSpawner");
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(6, 5), getEntities(res, "zombie_toast").get(0).getPosition());
    }

    @Test 
    @DisplayName("Spawn zombie toast in open square when cardinally blocked left and right")
    public void testSpawnZombieToastBlockedLeft() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieToastBlockedTwice", "c_zombieToastSpawner");
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(5, 6), getEntities(res, "zombie_toast").get(0).getPosition());
    }

    @Test 
    @DisplayName("Spawn zombie toast in open square when cardinally blocked left right and down")
    public void testSpawnZombieToastBlockedTwice() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieToastBlockedThrice", "c_zombieToastSpawner");
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(5, 4), getEntities(res, "zombie_toast").get(0).getPosition());
    }
    
    @Test
    @DisplayName("Zombie toast won't be destroyed when player doesn't have a weapon") 
    public void testCannotDestroyZombieToast() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieToastNoWeapon", "c_zombieToastSpawner");
        assertThrows(InvalidActionException.class, () -> {
            dmc.interact(getEntities(res, "zombie_toast_spawner").get(0).getId());
        });

    }
}
