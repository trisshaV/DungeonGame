package dungeonmania.dynamic_entity;

import java.util.List;
import java.util.Random;

import dungeonmania.Entity;
import dungeonmania.SerializableJSONObject;
import dungeonmania.dynamic_entity.movement.ChaseMovement;
import dungeonmania.dynamic_entity.movement.FollowMovement;
import dungeonmania.dynamic_entity.movement.Movement;
import dungeonmania.dynamic_entity.movement.RunAwayMovement;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Assassin extends DynamicEntity{
    private String status = "HOSTILE";
    private int reconRadius;
    private int bribeRadius;
    private int bribeAmount;
    private double bribeFailRate;
    private Movement move;

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
    public String getType() {
        return "assassin";
    }

    public String getStatus() {
        return status;
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
    public EntityResponse getEntityResponse() {
        if (status.equals("FRIENDLY")) {
            return new EntityResponse(getId(), getType(), getPosition(), false);
        }
        return new EntityResponse(getId(), getType(), getPosition(), true);
    }


    @Override
    public void updatePos(Direction d, List<Entity> l) {
        if (status.equals("HOSTILE")) {
            Player p = (Player)l.stream().filter(x -> x instanceof Player).findFirst().orElse(null);
            if (p.getStatus().equals("INVISIBLE")) {
                // Check for Radius
                // If player is within Recon Radius, Assassin will still chase after it
                Position distance = Position.calculatePositionBetween(p.getPosition(), this.getPosition());
                double radius = Math.sqrt(Math.pow(distance.getX(), 2) + Math.pow(distance.getY(), 2));
                if (radius > reconRadius) {
                    move = new RandomMovement();
                } else {
                    move = new ChaseMovement();
                }
            } else if (p.getStatus().equals("INVINCIBLE")) {
                move = new RunAwayMovement();
            } else {
                move = new ChaseMovement();
            }
        // If they are Allies, Assassins will follow the Player
        } else {
            move = new FollowMovement();
        }
        setPosition(move.getNextPosition(this, l));
    }

}
