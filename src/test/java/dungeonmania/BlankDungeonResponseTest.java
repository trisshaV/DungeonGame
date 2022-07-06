package dungeonmania;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Position;

public class BlankDungeonResponseTest {
    @Test
    @DisplayName("Test the game starts")
    /**
     * This is more or less a placeholder test - this should be changed
     */
    public void testBlankResponse() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse resp = dmc.newGame("d_DoorsKeysTest_useKeyWalkThroughOpenDoor", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        List<EntityResponse> expected_entities = new ArrayList<>();
        expected_entities.add(new EntityResponse("placeholder", "player", new Position(1, 1), false));
        expected_entities.add(new EntityResponse("placeholder", "key", new Position(2,1), false));
        expected_entities.add(new EntityResponse("placeholder", "door", new Position(3,1), false));
        expected_entities.add(new EntityResponse("placeholder", "exit", new Position(8,8), false));

        DungeonResponse exp = new DungeonResponse(
            "dungeonId", "d_DoorsKeysTest_useKeyWalkThroughOpenDoor", expected_entities, new ArrayList<ItemResponse>(), 
            new ArrayList<BattleResponse>(), new ArrayList<String>(), "exit");
        assert(resp == exp);
    }
}