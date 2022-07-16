package dungeonmania;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;



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

public class PotionTests {
    @Test
    @DisplayName("Test player can pick up two kinds of potions")
    public void pickupPotions() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_twoPotionPickup", "c_standard_movement");

        // pick up invincibility potion
        res = dmc.tick(Direction.RIGHT);
        Position pos = getEntities(res, "player").get(0).getPosition();
        assertEquals(1, getInventory(res, "invincibility_potion").size());

        // pick up invisibility potion
        res = dmc.tick(Direction.RIGHT);
        //assertEquals(1, getInventory(res, "invincibility_potion").size());
        assertEquals(1, getInventory(res, "invisibility_potion").size());
        assertNotEquals(pos, getEntities(res, "player").get(0).getPosition());
    }

    @Test
    @DisplayName("Test player can pick up two kinds of potions and consumption")
    public void pickupPotionsConsumption() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_twoPotionPickup", "c_standard_movement");
        EntityResponse potionOne = getEntities(res, "invincibility_potion").get(0);
        EntityResponse potionTwo = getEntities(res, "invisibility_potion").get(0);

        // pick up invincibility potion
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "invincibility_potion").size());
        // consume invincibility potion
        res = dmc.tick(potionOne.getId());
        assertEquals(0, getInventory(res, "invincibility_potion").size());

        // pick up invisibility potion
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "invisibility_potion").size());
        // consume invisibility potion
        res = dmc.tick(potionTwo.getId());
        assertEquals(0, getInventory(res, "invisibility_potion").size());
    }



}