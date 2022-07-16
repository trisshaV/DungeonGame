package dungeonmania;

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


public class BattleTests {
    private static DungeonResponse genericMercenarySequence(DungeonManiaController controller, String configFile) {
        /*
         *  exit   wall  wall  wall
         * player  [  ]  merc  wall
         *  wall   wall  wall  wall
         */
        DungeonResponse initialResponse = controller.newGame("d_battleTest_basicMercenary", configFile);
        int mercenaryCount = countEntityOfType(initialResponse, "mercenary");
        
        assertEquals(1, countEntityOfType(initialResponse, "player"));
        assertEquals(1, mercenaryCount);
        return controller.tick(Direction.RIGHT);
    }

    private static DungeonResponse genericSpiderSequence(DungeonManiaController controller, String configFile) {
        /*
         *  wall  exit  wall  
         *  wall  play  wall  
         *  wall  [  ]  wall  
         *  wall  spid  wall  
         *  wall  wall  wall 
         */
        DungeonResponse initialResponse = controller.newGame("d_battleTest_basicSpider", configFile);
        int spiderCount = countEntityOfType(initialResponse, "spider");
        
        assertEquals(1, countEntityOfType(initialResponse, "player"));
        assertEquals(1, spiderCount);
        return controller.tick(Direction.DOWN);
    }

    private static DungeonResponse genericZombieSequence(DungeonManiaController controller, String configFile) {
        /*
         *  wall  exit  wall  
         *  wall  play  wall  
         *  wall  [  ]  wall  
         *  wall  zomb  wall  
         *  wall  wall  wall 
         */
        DungeonResponse initialResponse = controller.newGame("d_battleTest_basicZombie", configFile);
        int zombieCount = countEntityOfType(initialResponse, "zombie_toast");
        
        assertEquals(1, countEntityOfType(initialResponse, "player"));
        assertEquals(1, zombieCount);
        return controller.tick(Direction.DOWN);
    }


    private void assertBattleCalculations(String enemyType, BattleResponse battle, boolean enemyDies, String configFilePath) {
        List<RoundResponse> rounds = battle.getRounds();
        double playerHealth = Double.parseDouble(getValueFromConfigFile("player_health", configFilePath));
        double enemyHealth = Double.parseDouble(getValueFromConfigFile(enemyType + "_health", configFilePath));
        double playerAttack = Double.parseDouble(getValueFromConfigFile("player_attack", configFilePath));
        double enemyAttack = Double.parseDouble(getValueFromConfigFile(enemyType + "_attack", configFilePath));

        for (RoundResponse round : rounds) {
            assertEquals(round.getDeltaCharacterHealth(), -(enemyAttack / 10));
            assertEquals(round.getDeltaEnemyHealth(), -(playerAttack / 5));
            enemyHealth += round.getDeltaEnemyHealth();
            playerHealth += round.getDeltaCharacterHealth();
            System.out.println(enemyHealth);
            System.out.println(playerHealth);
        }
        if (enemyDies) {
            assertTrue(enemyHealth <= 0);
        } else {
            assertTrue(playerHealth <= 0);
        }
    }

    
    @Test
    @DisplayName("Test basic battle calculations - mercenary - player loses")
    public void testHealthBelowZeroMercenary() {
       DungeonManiaController controller = new DungeonManiaController();
       DungeonResponse postBattleResponse = genericMercenarySequence(controller, "c_battleTests_basicMercenaryPlayerDies");
       BattleResponse battle = postBattleResponse.getBattles().get(0);
       assertBattleCalculations("mercenary", battle, false, "c_battleTests_basicMercenaryPlayerDies");
    }
    
    @Test
    @DisplayName("Test basic battle calculations - mercenary - player wins")
    public void testRoundCalculationsMercenary() {
       DungeonManiaController controller = new DungeonManiaController();
       DungeonResponse postBattleResponse = genericMercenarySequence(controller, "c_battleTests_basicMercenaryMercenaryDies");
       BattleResponse battle = postBattleResponse.getBattles().get(0);
       assertBattleCalculations("mercenary", battle, true, "c_battleTests_basicMercenaryMercenaryDies");
    }
    
    @Test
    @DisplayName("Test basic battle calculations - spider - player loses")
    public void testHealthBelowZeroSpider() {
       DungeonManiaController controller = new DungeonManiaController();
       DungeonResponse postBattleResponse = genericSpiderSequence(controller, "c_battleTests_basicSpiderPlayerDies");
       BattleResponse battle = postBattleResponse.getBattles().get(0);
       assertBattleCalculations("spider", battle, false, "c_battleTests_basicSpiderPlayerDies");
    }

    @Test
    @DisplayName("Test basic battle calculations - spider - player wins")
    public void testRoundCalculationsSpider() {
       DungeonManiaController controller = new DungeonManiaController();
       DungeonResponse postBattleResponse = genericSpiderSequence(controller, "c_battleTests_basicSpiderSpiderDies");
       BattleResponse battle = postBattleResponse.getBattles().get(0);
       assertBattleCalculations("spider", battle, true, "c_battleTests_basicSpiderSpiderDies");
    }

    @Test
    @DisplayName("Test basic battle calculations - zombie_toast - player wins")
    public void testRoundCalculationsZombieToast() {
       DungeonManiaController controller = new DungeonManiaController();
       DungeonResponse postBattleResponse = genericZombieSequence(controller, "c_battleTests_basicZombieZombieDies");
       BattleResponse battle = postBattleResponse.getBattles().get(0);
       assertBattleCalculations("zombie", battle, true, "c_battleTests_basicZombieZombieDies");
    }

    @Test
    @DisplayName("Test basic battle calculations - zombie_toast - zombie wins")
    public void testHealthBelowZeroZombieToast() {
       DungeonManiaController controller = new DungeonManiaController();
       DungeonResponse postBattleResponse = genericZombieSequence(controller, "c_battleTests_basicZombiePlayerDies");
       BattleResponse battle = postBattleResponse.getBattles().get(0);
       assertBattleCalculations("zombie", battle, false, "c_battleTests_basicZombiePlayerDies");
    }


}
