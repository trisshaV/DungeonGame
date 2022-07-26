package dungeonmania.dynamic_entity;

import java.util.List;

import dungeonmania.Entity;
import dungeonmania.SerializableJSONObject;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Assassin extends DynamicEntity{
    private String status = "HOSTILE";
    private int reconRadius;
    private int bribeRadius;
    private int bribeAmount;
    private double bribeFailRate;

    public Assassin(String id, Position xy, SerializableJSONObject jsonConfig) {
        super(id, "assassin", xy);
        this.attack = jsonConfig.getDouble("assassin_attack");
        this.health = jsonConfig.getDouble("assassin_health");
        this.reconRadius = jsonConfig.getInt("assassin_recon_radius");
        this.bribeRadius = jsonConfig.getInt("assassin_bribe_amount");
        this.bribeAmount = jsonConfig.getInt("assassin_bribe_amount");
        this.bribeFailRate = jsonConfig.getDouble("assassin_bribe_fail_rate");
    }

    @Override
    public void updatePos(Direction d, List<Entity> l) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getType() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
