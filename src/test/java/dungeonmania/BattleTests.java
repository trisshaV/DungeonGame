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

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;


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
    
    private void assertBattleCalculationsWithUnbrokenWeapons(String enemyType, BattleResponse battle, boolean enemyDies, String configFilePath, List<Integer> numberOfWeapons) {
        List<RoundResponse> rounds = battle.getRounds();
        double playerHealth = Double.parseDouble(getValueFromConfigFile("player_health", configFilePath));
        double enemyHealth = Double.parseDouble(getValueFromConfigFile(enemyType + "_health", configFilePath));
        double playerAttack = Double.parseDouble(getValueFromConfigFile("player_attack", configFilePath));
        double enemyAttack = Double.parseDouble(getValueFromConfigFile(enemyType + "_attack", configFilePath));

        // Adjust attack accord to weapons that exist
        double swordAttack = Double.parseDouble(getValueFromConfigFile("sword_attack", configFilePath));
        double shieldDefense = Double.parseDouble(getValueFromConfigFile("shield_defence", configFilePath));
        // add shield defense
        enemyAttack = enemyAttack - shieldDefense * numberOfWeapons.get(2);
        // Add sword Attack
        playerAttack = playerAttack + swordAttack * numberOfWeapons.get(0);
        // Add bow modifier
        playerAttack = (numberOfWeapons.get(1) + 1) * playerAttack;

        for (RoundResponse round : rounds) {
            assertEquals(round.getDeltaCharacterHealth(), -(enemyAttack / 10));
            assertEquals(round.getDeltaEnemyHealth(), -(playerAttack / 5));
            enemyHealth += round.getDeltaEnemyHealth();
            playerHealth += round.getDeltaCharacterHealth();
        }
        if (enemyDies) {
            assertTrue(enemyHealth <= 0);
        } else {
            assertTrue(playerHealth <= 0);
        }
    }
    
    @Test
    @DisplayName("Test battle with sword")
    public void testSwordBattle() {

        /*
         *  [  ] [  ] [  ] [  ] swor boul
         *  arro arro arro wood play spid
         *  [  ] [  ] [  ] [  ] wood [  ]
         *  [  ] [  ] [  ] [  ] wood [  ]
         *  [  ] [  ] [  ] [  ] key  [  ]
         */


        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_UnbrokenWeaponTest", "c_UnbrokenWeaponsWithSpiderTests");

        // Pickup Sword
        dmc.tick(Direction.UP);
        dmc.tick(Direction.DOWN);

        // Battle
        DungeonResponse postBattleResponse = dmc.tick(Direction.RIGHT);
        BattleResponse battle = postBattleResponse.getBattles().get(0);

        // List of integers as {Sword Bow Shield}
        // 1 sword is used
        List<Integer> weaponsUsed = new ArrayList<>();
        weaponsUsed.add(1);
        weaponsUsed.add(0);
        weaponsUsed.add(0);

        assertBattleCalculationsWithUnbrokenWeapons("spider", battle, true, "c_UnbrokenWeaponsWithSpiderTests", weaponsUsed);
    }

    @Test
    @DisplayName("Test battle with bow")
    public void testBowBattle() throws IllegalArgumentException, InvalidActionException {

         /*
         *  [  ] [  ] [  ] [  ] swor boul
         *  arro arro arro wood play spid
         *  [  ] [  ] [  ] [  ] wood [  ]
         *  [  ] [  ] [  ] [  ] wood [  ]
         *  [  ] [  ] [  ] [  ] key  [  ]
         */


        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_UnbrokenWeaponTest", "c_UnbrokenWeaponsWithSpiderTests");

        // Pickup materials for bow
        dmc.tick(Direction.LEFT);
        dmc.tick(Direction.LEFT);
        dmc.tick(Direction.LEFT);
        dmc.tick(Direction.LEFT);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);

        // Craft bow
        dmc.build("bow");

        // Battle
        DungeonResponse postBattleResponse = dmc.tick(Direction.RIGHT);
        BattleResponse battle = postBattleResponse.getBattles().get(0);

        // List of integers as {Sword Bow Shield}
        // 1 bow is used
        List<Integer> weaponsUsed = new ArrayList<>();
        weaponsUsed.add(0);
        weaponsUsed.add(1);
        weaponsUsed.add(0);

        assertBattleCalculationsWithUnbrokenWeapons("spider", battle, true, "c_UnbrokenWeaponsWithSpiderTests", weaponsUsed);
    }

    @Test
    @DisplayName("Test battle with shield")
    public void testShieldBattle() throws IllegalArgumentException, InvalidActionException {

         /*
         *  [  ] [  ] [  ] [  ] swor boul
         *  arro arro arro wood play spid
         *  [  ] [  ] [  ] [  ] wood [  ]
         *  [  ] [  ] [  ] [  ] wood [  ]
         *  [  ] [  ] [  ] [  ] key  [  ]
         */


        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_UnbrokenWeaponTest", "c_UnbrokenWeaponsWithSpiderTests");

        // Pickup materials for shield
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.UP);
        dmc.tick(Direction.UP);
        dmc.tick(Direction.UP);


        // Craft shield
        dmc.build("shield");

        // Battle
        DungeonResponse postBattleResponse = dmc.tick(Direction.RIGHT);
        BattleResponse battle = postBattleResponse.getBattles().get(0);

        // List of integers as {Sword Bow Shield}
        // 1 bow is used
        List<Integer> weaponsUsed = new ArrayList<>();
        weaponsUsed.add(0);
        weaponsUsed.add(0);
        weaponsUsed.add(1);

        assertBattleCalculationsWithUnbrokenWeapons("spider", battle, true, "c_UnbrokenWeaponsWithSpiderTests", weaponsUsed);
    }

    @Test
    @DisplayName("Test battle with Multiple Weapons")
    public void testMultipleWeapons() throws IllegalArgumentException, InvalidActionException {

         /*
         *  [  ] [  ] [  ] [  ] swor boul
         *  arro arro arro wood play spid
         *  [  ] [  ] [  ] [  ] wood [  ]
         *  [  ] [  ] [  ] [  ] wood [  ]
         *  [  ] [  ] [  ] [  ] key  [  ]
         */


        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_UnbrokenWeaponTest", "c_UnbrokenWeaponsWithSpiderTests");

        // Pickup materials for shield
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.UP);
        dmc.tick(Direction.UP);
        dmc.tick(Direction.UP);

        // Pickup materials for bow
        dmc.tick(Direction.LEFT);
        dmc.tick(Direction.LEFT);
        dmc.tick(Direction.LEFT);
        dmc.tick(Direction.LEFT);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);

        // Pickup Sword
        dmc.tick(Direction.UP);
        dmc.tick(Direction.DOWN);

        // Craft shield and bow
        dmc.build("shield");
        dmc.build("bow");

        // Battle
        DungeonResponse postBattleResponse = dmc.tick(Direction.RIGHT);
        BattleResponse battle = postBattleResponse.getBattles().get(0);

        // List of integers as {Sword Bow Shield}
        // 1 bow is used
        List<Integer> weaponsUsed = new ArrayList<>();
        weaponsUsed.add(1);
        weaponsUsed.add(1);
        weaponsUsed.add(1);

        assertBattleCalculationsWithUnbrokenWeapons("spider", battle, true, "c_UnbrokenWeaponsWithSpiderTests", weaponsUsed);
    }

    @Test
    @DisplayName("Test battle with invincibilty potion status")
    public void testInvinciblePotion() throws IllegalArgumentException, InvalidActionException {

         /*
         *  [  ] [  ] [  ] [  ] [  ] boul
         *  [  ] [  ] [  ] invi play spid
         *  [  ] [  ] [  ] [  ] [  ] [  ]
         *  [  ] [  ] [  ] [  ] [  ] [  ]
         *  [  ] [  ] [  ] [  ] [  ] [  ]
         */


        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_InvinciblePotionTest", "c_UnbrokenWeaponsWithSpiderTests");

        // Pickup potion
        EntityResponse potionOne = getEntities(res, "invincibility_potion").get(0);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.RIGHT);
        // Consume potion
        assertEquals(1, getInventory(res, "invincibility_potion").size());
        res = dmc.tick(potionOne.getId());

        // Battle with spider
        DungeonResponse postBattleResponse = dmc.tick(Direction.RIGHT);
        BattleResponse battle = postBattleResponse.getBattles().get(0);

        // 1 Round only
        assert(battle.getRounds().size() == 1);
        RoundResponse singleRound = battle.getRounds().get(0);
        assertEquals(singleRound.getDeltaCharacterHealth(), 0);
        assertEquals(singleRound.getDeltaEnemyHealth(), -1 * Double.parseDouble(getValueFromConfigFile("spider_health", "c_UnbrokenWeaponsWithSpiderTests")));
        // 1 item used
        assert(singleRound.getWeaponryUsed().size() == 1);
        ItemResponse potion = singleRound.getWeaponryUsed().get(0);
        assertEquals(potion.getId(), potionOne.getId());
        assertEquals(potion.getType(), potionOne.getType());

    }

}
