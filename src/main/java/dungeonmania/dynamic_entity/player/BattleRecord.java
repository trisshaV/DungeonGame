package dungeonmania.dynamic_entity.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterRegistration.Dynamic;

import dungeonmania.Inventory;
import dungeonmania.collectible.Bow;
import dungeonmania.collectible.Buildable;
import dungeonmania.collectible.Collectible;
import dungeonmania.collectible.InvincibilityPotion;
import dungeonmania.collectible.InvisibilityPotion;
import dungeonmania.collectible.Shield;
import dungeonmania.collectible.Sword;
import dungeonmania.dynamic_entity.DynamicEntity;
import dungeonmania.dynamic_entity.Player;
import dungeonmania.response.models.ItemResponse;

public class BattleRecord {
    private DynamicEntity enemy;
    private List <RoundRecord> rounds = new ArrayList<>();
    private double initialPlayerHealth;
    private double initialEnemyHealth;

    public BattleRecord(DynamicEntity enemy, DynamicEntity player) {
        this.enemy = enemy;
        this.initialPlayerHealth = player.getHealth();
        this.initialEnemyHealth = enemy.getHealth();
        startBattle(player);
    }
    
    private void addRoundRecord(double changePlayerHealth, double changeEnemyHealth, List <Object> battleItems) {
        // Convert battleItems into ItemResponses

        List<Object> weaponsUsed = new ArrayList<>();
        battleItems.stream().forEach(
            item -> {
                Object newItem = null;
                if (item instanceof Sword) {
                    Sword temp = (Sword)item;
                    newItem = new ItemResponse(temp.getId(), temp.getType());
                } else if (item instanceof Buildable) {
                    Buildable temp = (Buildable)item;
                    newItem = new ItemResponse(temp.getId(), temp.getType());
                } else if (item instanceof InvincibilityPotion || item instanceof InvisibilityPotion) {
                    Collectible temp = (Collectible)item;
                    newItem = new ItemResponse(temp.getId(), temp.getType());
                }
                weaponsUsed.add(newItem);
            }
        );
        RoundRecord newRecord = new RoundRecord(changePlayerHealth, changeEnemyHealth, weaponsUsed);
        rounds.add(newRecord);
    }

    private void startBattle(DynamicEntity player) {
        double enemyAttack = enemy.getAttack();
        double playerAttack = player.getAttack();

        // Default values
        double bowModifier = 1;
        double swordAdd = 0;
        double shieldMinus = 0;

        double newEnemyHealth = initialEnemyHealth;
        double newPlayerHealth = initialPlayerHealth;
        while (newEnemyHealth > 0 && newPlayerHealth > 0) {

            // itemsInRoundUsed will have { List of Swords, List of Bows, List of Shields}
            List <List<Object>> itemsInRoundUsed = itemsAvaliable(player);
            // Calculate modifiers
            if (itemsInRoundUsed.get(0).size() != 0) {
                List<Object> swordsUsed = itemsInRoundUsed.get(0);
                Sword temp = (Sword)swordsUsed.get(0);
                int swordAttack = temp.getAtack();
                swordAdd = itemsInRoundUsed.get(0).size() * swordAttack;
            }

            if (itemsInRoundUsed.get(1).size() != 0) {
                bowModifier = itemsInRoundUsed.get(1).size() + 1;
            }
            if (itemsInRoundUsed.get(2).size() != 0) {
                List<Object> shieldsUsed = itemsInRoundUsed.get(2);
                Shield temp = (Shield)shieldsUsed.get(0);
                int shieldDefense = temp.getShieldDefense();
                shieldMinus = itemsInRoundUsed.get(2).size() * shieldDefense;
            }
            double modifiedPlayerDamage = ((bowModifier * (playerAttack + swordAdd))/5);
            double modifiedEnemyDamage = ((enemyAttack - shieldMinus) / 10);
            newEnemyHealth = enemy.getHealth() - modifiedPlayerDamage;
            newPlayerHealth = player.getHealth() - modifiedEnemyDamage;
            // Update durability of equipment
            updateDurability(itemsInRoundUsed, (Player)player);

            // Convert itemsInRoundUsed into single list of items used
            List <Object> battleItems = convertToList(itemsInRoundUsed);
            addRoundRecord(-1 * modifiedEnemyDamage, -1 * modifiedPlayerDamage, battleItems);
            // Remove broken items
            ((Player)player).removeBrokenItems();
            enemy.setHealth(newEnemyHealth);
            player.setHealth(newPlayerHealth);
        }

    }

    private void updateDurability(List<List<Object>> itemsUsed, Player player) {

        // Unchecked Type cast, itemsUsed has been safely type checked from method itemsAvaliable
        List<Sword> SwordsUsed = (List<Sword>)(List<?>) itemsUsed.get(0);
        List<Buildable> BowsUsed = (List<Buildable>)(List<?>) itemsUsed.get(1);
        List<Buildable> ShieldsUsed = (List<Buildable>)(List<?>) itemsUsed.get(2);
        
        SwordsUsed.stream().forEach(
            x -> {
                int currentDurability = x.getDurability();
                x.setDurability(currentDurability - 1);
            }
        );

        BowsUsed.stream().forEach(
            x -> {
                int currentDurability = x.getDurability();
                x.setDurability(currentDurability - 1);
            }
        );

        ShieldsUsed.stream().forEach(
            x -> {
                int currentDurability = x.getDurability();
                x.setDurability(currentDurability - 1);
            }
        );

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

    private List <Object> convertToList(List<List<Object>> listsOfItems) {
        return listsOfItems.stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    public List<RoundRecord> getRounds() {
        return rounds; 
    }

    public double getInitialPlayerHealth() {
        return initialPlayerHealth;
    }

    public double getInitialEnemyHealth() {
        return initialEnemyHealth;
    }

    public DynamicEntity getEnemy() {
        return enemy;
    }

    

    
}
