package dungeonmania;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import static dungeonmania.TestUtils.getInventory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static dungeonmania.TestUtils.getEntities;

public class SceptreTests {
    @Test
    @DisplayName("Player builds and mindcontrols with Sceptre")
    public void testMindControl() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("sceptre", "M3_config");
        List<ItemResponse> inventory = new ArrayList<>();
        inventory.add(new ItemResponse("0", "sceptre"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.build("sceptre");
        List<ItemResponse> inven = getInventory(res, "sceptre");
        assertEquals(1, inven.size());
        assertEquals(inventory.get(0).getType(), inven.get(0).getType());

        res = dmc.interact(getEntities(res, "mercenary").get(0).getId());
        assertEquals("FRIENDLY", dmc.getMercStatus());
        assertEquals("HOSTILE", dmc.getAssassinStatus());
    }

    @Test
    @DisplayName("Player mindcontrol Duration and Sceptre is removed")
    public void testMindControlDuration() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("sceptre", "M3_config");
        List<ItemResponse> inventory = new ArrayList<>();
        inventory.add(new ItemResponse("0", "sceptre"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.build("sceptre");
        List<ItemResponse> inven = getInventory(res, "sceptre");
        assertEquals(1, inven.size());
        assertEquals(inventory.get(0).getType(), inven.get(0).getType());

        res = dmc.interact(getEntities(res, "mercenary").get(0).getId());
        res = dmc.interact(getEntities(res, "assassin").get(0).getId());
        assertEquals("FRIENDLY", dmc.getMercStatus());
        assertEquals("FRIENDLY", dmc.getAssassinStatus());

        // Mindcontrol has ended at the END of the THIRD movement tick
        res = dmc.tick(Direction.RIGHT); //active
        res = dmc.tick(Direction.RIGHT); //active
        res = dmc.tick(Direction.RIGHT); //active
        res = dmc.tick(Direction.RIGHT); // inactive
        assertEquals("HOSTILE", dmc.getAssassinStatus());
        assertEquals("HOSTILE", dmc.getMercStatus());
        // Sceptre is removed from invent
        inven = getInventory(res, "sceptre");
        assertEquals(0, inven.size());
    }

    @Test
    @DisplayName("Mindcontrol but only one out of two entities become allies")
    public void testMindControlwithTwoEntities() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("sceptre", "M3_config");
        List<ItemResponse> inventory = new ArrayList<>();
        inventory.add(new ItemResponse("0", "sceptre"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.build("sceptre");
        List<ItemResponse> inven = getInventory(res, "sceptre");
        assertEquals(1, inven.size());
        assertEquals(inventory.get(0).getType(), inven.get(0).getType());
        
        res = dmc.tick(Direction.UP); //inactive
        res = dmc.interact(getEntities(res, "assassin").get(0).getId());
        assertEquals("HOSTILE", dmc.getMercStatus());
        assertEquals("FRIENDLY", dmc.getAssassinStatus());

        // Mindcontrol has ended at the END of the THIRD movement tick
        res = dmc.tick(Direction.UP); //active
        res = dmc.tick(Direction.UP); //active
        res = dmc.tick(Direction.UP); //active
        res = dmc.tick(Direction.UP); //inactive
        assertEquals("HOSTILE", dmc.getAssassinStatus());
        assertEquals("HOSTILE", dmc.getMercStatus());

    }


    @Test
    @DisplayName("Test that enemies aren't mindcontrolled until interacted with")
    public void testMindControlonlywithInteract() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("sceptre", "M3_config");
        List<ItemResponse> inventory = new ArrayList<>();
        inventory.add(new ItemResponse("0", "sceptre"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.build("sceptre");
        List<ItemResponse> inven = getInventory(res, "sceptre");
        assertEquals(1, inven.size());
        assertEquals(inventory.get(0).getType(), inven.get(0).getType());

        for (int i=0; i < 5; i++) {
            res = dmc.tick(Direction.UP); //inactive
            assertEquals("HOSTILE", dmc.getMercStatus());
            assertEquals("HOSTILE", dmc.getAssassinStatus());
        }

        res = dmc.interact(getEntities(res, "assassin").get(0).getId());
        assertEquals("HOSTILE", dmc.getMercStatus());
        assertEquals("FRIENDLY", dmc.getAssassinStatus());
    }
    
}
