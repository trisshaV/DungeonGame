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

    private List<DynamicEntity> entities;
    private Player player;

    public Observer(List<Entity> entitiesList, Player player) {
        List<Entity> temp = entitiesList.stream().filter(entity -> entity instanceof DynamicEntity && !(entity instanceof Player)).collect(Collectors.toList());
        temp.stream().forEach(
            e -> {
                if (e instanceof Mercenary && ((Mercenary)e).getStatus().equals("HOSTILE")) {
                    entities.add((DynamicEntity)e);
                } else {
                    entities.add((DynamicEntity)e);
                }
            }
        );
        this.player = player;
    }

    public boolean checkBattle() {
        Position playerPos = player.getPosition();
        int numBattles = battleRecords.size();
        entities.stream().forEach(
            e -> {
                Position entityPosition = e.getPosition();
                if (playerPos.equals(entityPosition)) {
                    BattleRecord newBattle = new BattleRecord(e, player);
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

    public boolean isPlayerDeceased() {
        return playerDeceased;
    }

    public List<BattleRecord> getBattleRecords() {
        return battleRecords;
    }  
}
