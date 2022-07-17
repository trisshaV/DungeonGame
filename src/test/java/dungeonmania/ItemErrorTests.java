package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;

public class ItemErrorTests {
    @Test
    @DisplayName("Player cant use an item that is not in their inventory")
    public void testUseItemNotInInventory() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("bombs", "bomb_radius_2");
        assertThrows(InvalidActionException.class, () -> dmc.tick("bomb"));
        assertThrows(InvalidActionException.class, () -> dmc.tick("invisibility_potion"));
        assertThrows(InvalidActionException.class, () -> dmc.tick("invincibility_potion"));
    }

    @Test
    @DisplayName("Player cant use an item that is not consumable")
    public void testItemNotConsumable() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("build_bow", "simple");
        res = dmc.tick(Direction.RIGHT);
        List<ItemResponse> woodInventory = TestUtils.getInventory(res, "wood");
        assertEquals(1, woodInventory.size());
        assertThrows(IllegalArgumentException.class, () -> dmc.tick(woodInventory.get(0).getId()));
    }

    @Test
    @DisplayName("Player cant build an item because not enough materials")
    public void testCantBuild() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("build_bow", "simple");
        res = dmc.tick(Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> dmc.build("bow"));
        assertThrows(InvalidActionException.class, () -> dmc.build("shield"));
    }
    @Test
    @DisplayName("Player cant build an item because the item is uncraftable")
    public void testCantBuild2() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("build_bow", "simple");
        assertThrows(IllegalArgumentException.class, () -> dmc.build("sword"));
        assertThrows(IllegalArgumentException.class, () -> dmc.build("banana"));
    }
}
