package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.dynamic_entity.DynamicEntity;
import dungeonmania.dynamic_entity.Mercenary;
import dungeonmania.dynamic_entity.Player;
import dungeonmania.dynamic_entity.player.BattleRecord;
import dungeonmania.util.Position;

public class Observer {
    
    public boolean playerDeceased = false;
    public List<BattleRecord> battleRecords = new ArrayList<>();

    /**
     * Observer contructor
     * @param entitiesList
     * @param player
     */
    public Observer() {
    }

    /**
     * Checks battles
     * @return relevant recordings
     */
    public boolean checkBattle(List <Entity> entities) {

        Entity player = entities.stream().filter(x -> x.getType().equals("player")).findFirst().get();
        Position playerPos = player.getPosition();
        int numBattles = battleRecords.size();  
        entities.stream().filter(x -> x instanceof DynamicEntity && !(x instanceof Player)).forEach(
            e -> {
                Position entityPosition = e.getPosition();
                if (playerPos.equals(entityPosition)) {
                    System.out.println("hi");
                    BattleRecord newBattle = new BattleRecord((DynamicEntity)e, (DynamicEntity)player);
                    battleRecords.add(newBattle);
                }
            }
        );
        if (numBattles == battleRecords.size()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if player has deceased
     * @return
     */
    public boolean isPlayerDeceased() {
        return playerDeceased;
    }

    /**
     * Gets battle records
     * @return records of battles
     */
    public List<BattleRecord> getBattleRecords() {
        return battleRecords;
    }  
}
