package dungeonmania.dynamic_entity.player;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterRegistration.Dynamic;

import dungeonmania.Entity;
import dungeonmania.collectible.Collectible;
import dungeonmania.dynamic_entity.DynamicEntity;
import dungeonmania.dynamic_entity.Player;

public class BattleRecord {
    private DynamicEntity enemy;
    private List <RoundRecord> rounds;
    private double initialPlayerHealth;
    private double initialEnemyHealth;

    public BattleRecord(DynamicEntity enemy, List<RoundRecord> rounds, DynamicEntity player) {
        this.enemy = enemy;
        this.rounds = rounds;
        this.initialPlayerHealth = player.getHealth();
        this.initialEnemyHealth = enemy.getHealth();

        startBattle(player);

    }
    
    private void addRoundRecord(double changePlayerHealth, double changeEnemyHealth, List <Collectible> battleItems) {
        RoundRecord newRecord = new RoundRecord (0, 0, null);
        rounds.add(newRecord);
    }

    private void startBattle(DynamicEntity player) {
        double enemyAttack = enemy.getAttack();
        double playerAttack = player.getAttack();

        int bowModifier = 1;
        int swordAdd = 0;
        int shieldMinus = 0;

        // Check for each of these 


        double newEnemyHealth = initialEnemyHealth;
        double newPlayerHealth = initialPlayerHealth;
        while (newEnemyHealth > 0 && newPlayerHealth > 0) {

            double modifiedPlayerDamage = ((bowModifier * (playerAttack + swordAdd))/5);
            double modifiedEnemyDamage = ((enemyAttack - shieldMinus) / 10);
            newEnemyHealth = enemy.getHealth() - modifiedPlayerDamage;
            newPlayerHealth = player.getHealth() - modifiedEnemyDamage;

            // Update durability of equipment
            updateDurability(bowModifier, swordAdd, shieldMinus, player);

            addRoundRecord(-1 * modifiedEnemyDamage, -1 * modifiedPlayerDamage, )


            // Recalculate modifiers
        }

        enemy.setHealth(newEnemyHealth);
        player.setHealth(newPlayerHealth);

    }

    private void updateDurability(double bowModifier, double swordAdd, double shieldMinus, Player player) {
        if (bowModifier != 1) {
            // update durablity for bow
        }
        if (swordAdd != 0) {
            // update durability for sword
        }

        if (shieldMinus != 0) {
            // update durability for shield
        }
    }
    
    private List<Collectible> itemsAvaliable(player) {
        // List will contain three boolean values representing bow, sword and shield respectively
        List<Collectible> result = 
    }
}
