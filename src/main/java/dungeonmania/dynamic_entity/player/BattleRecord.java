package dungeonmania.dynamic_entity.player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterRegistration.Dynamic;

import dungeonmania.Entity;
import dungeonmania.Inventory;
import dungeonmania.collectible.Bow;
import dungeonmania.collectible.Buildable;
import dungeonmania.collectible.Collectible;
import dungeonmania.collectible.Shield;
import dungeonmania.collectible.Sword;
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

        double bowModifier = 1;
        double swordAdd = 0;
        double shieldMinus = 0;

        // Check for each of these 
        // itemsInRoundUsed will have { List of Swords, List of Bows, List of Shields}
        List <List<Object>> itemsInRoundUsed = itemsAvaliable(player);

        double newEnemyHealth = initialEnemyHealth;
        double newPlayerHealth = initialPlayerHealth;
        while (newEnemyHealth > 0 && newPlayerHealth > 0) {

            double modifiedPlayerDamage = ((bowModifier * (playerAttack + swordAdd))/5);
            double modifiedEnemyDamage = ((enemyAttack - shieldMinus) / 10);
            newEnemyHealth = enemy.getHealth() - modifiedPlayerDamage;
            newPlayerHealth = player.getHealth() - modifiedEnemyDamage;

            // Update durability of equipment
            updateDurability(itemsInRoundUsed, (Player)player);

            addRoundRecord(-1 * modifiedEnemyDamage, -1 * modifiedPlayerDamage, null);


            // Recalculate modifiers
        }

        enemy.setHealth(newEnemyHealth);
        player.setHealth(newPlayerHealth);


        // Starts

        // Going to check avaliable battle items
        
        // HERE
        // Caculate the modifiers 
        // Player hit enemey
        // Enemey hits player
        // updateDurablity

        // Check: if Player.health <= 0, Enemey.health <= 0 
        // If they're alive, go back up to HERE

    }

    private void updateDurability(List<List<Object>> itemsUsed, Player player) {

        // Unchecked Type cast, itemsUsed has been safely type checked from method itemsAvaliable
        List<Collectible> SwordsUsed = (List<Collectible>)(List<?>) itemsUsed.get(0);
        List<Buildable> BowsUsed = (List<Buildable>)(List<?>) itemsUsed.get(1);
        List<Buildable> ShieldsUsed = (List<Buildable>)(List<?>) itemsUsed.get(2);

        SwordsUsed.stream().forEach(
            x -> {
                
            }
        );


        // Remove broken weapons
    }
    
    // List will contain three values representing sword, bow and shield respectively
    /**
     * Returns a list of the list of items avaliable in the form { List of Swords, List of Bows, List of Shields} 
     * @param player
     * @return List<List<Object>>
     */
    private List<List<Object>> itemsAvaliable(DynamicEntity player) {

        List<List<Object>> listsOfItems = new ArrayList<>();
        Inventory i = ((Player) player).getInventory();
        List <Collectible> CollectableItems = i.getCollectableItems();
        List <Buildable> BuildableItems = i.getBuildableItems();

              
        listsOfItems.add(CollectableItems.stream().filter(item -> item instanceof Sword).collect(Collectors.toList()));
        listsOfItems.add(BuildableItems.stream().filter(item -> item instanceof Bow).collect(Collectors.toList()));
        listsOfItems.add(BuildableItems.stream().filter(item -> item instanceof Shield).collect(Collectors.toList()));

        return listsOfItems;
    }
}
