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

        assertEquals("FRIENDLY", dmc.getAssassinStatus());
        assertEquals("FRIENDLY", dmc.getMercStatus());
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

        assertEquals("FRIENDLY", dmc.getAssassinStatus());
        assertEquals("FRIENDLY", dmc.getMercStatus());

        // Mindcontrol has ended on the THIRD movement tick
        res = dmc.tick(Direction.RIGHT); //active
        res = dmc.tick(Direction.RIGHT); //active
        res = dmc.tick(Direction.RIGHT); //inactive
        assertEquals("HOSTILE", dmc.getAssassinStatus());
        assertEquals("HOSTILE", dmc.getMercStatus());
        // Sceptre is removed from invent
        inven = getInventory(res, "sceptre");
        assertEquals(0, inven.size());
    }

    
}
