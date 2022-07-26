package dungeonmania.dynamic_entity;

import java.util.List;
import java.util.Random;

import dungeonmania.Entity;
import dungeonmania.SerializableJSONObject;
import dungeonmania.exceptions.InvalidActionException;
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
    public void interact (Player player) throws InvalidActionException {
        // Check the radius
        Position distance = Position.calculatePositionBetween(player.getPosition(), this.getPosition());
        double radius = Math.sqrt(Math.pow(distance.getX(), 2) + Math.pow(distance.getY(), 2));
        if (radius > bribeRadius) {
            throw new InvalidActionException("Too far from Assassin");
        }
        // check coins
        if (player.getCoins() < bribeAmount) {
            throw new InvalidActionException("Not enough coins");
        }
        // Checks probability that bribe will fail:
        double random = new Random().nextDouble();
        if (random < bribeFailRate) {
            throw new InvalidActionException("Unlucky! The Assassin did not accept your bribe");
        } else {
            status = "FRIENDLY";
        }
    }


    @Override
    public void updatePos(Direction d, List<Entity> l) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getType() {
        return "assassin";
    }
    
}
