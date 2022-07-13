package dungeonmania;

import dungeonmania.collectible.Key;
import dungeonmania.dynamic_entity.DynamicEntity;
import dungeonmania.dynamic_entity.Mercenary;
import dungeonmania.dynamic_entity.Player;
import dungeonmania.dynamic_entity.Spider;
import dungeonmania.dynamic_entity.ZombieToast;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.static_entity.Door;
import dungeonmania.static_entity.Exit;
import dungeonmania.static_entity.FloorSwitch;
import dungeonmania.static_entity.Portal;
import dungeonmania.static_entity.StaticEntity;
import dungeonmania.static_entity.Wall;
import dungeonmania.static_entity.ZombieToastSpawner;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;
import javassist.expr.Instanceof;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.FilterRegistration.Dynamic;

import org.json.JSONArray;
import org.json.JSONObject;

public class DungeonManiaController {
    private List<Portal> unpairedPortals = new ArrayList<>();
    private List<Entity> entities = new ArrayList<>();
    private Player player = null;
	private String dungeonId = "1";	
    private String goal;
	private String dungeonName;

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    /**
     * /dungeons
     */
    public static List<String> dungeons() {
        return FileLoader.listFileNamesInResourceDirectory("dungeons");
    }

    /**
     * /configs
     */
    public static List<String> configs() {
        return FileLoader.listFileNamesInResourceDirectory("configs");
    }

    /**
     * /game/new
     */
    public DungeonResponse newGame(String dungeonName, String configName) throws IllegalArgumentException {
        this.dungeonName = dungeonName;

        String confContent;
        String dungeonContent;
        try {
            confContent = FileLoader.loadResourceFile("/configs/" + configName + ".json");
            dungeonContent = FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }

        JSONObject json = new JSONObject(dungeonContent);
        JSONObject jsonConfig = new JSONObject(confContent);
        JSONArray jsonEntities = json.getJSONArray("entities");

        goal = json.getJSONObject("goal-condition").getString("goal");

        for (int i = 0; i < jsonEntities.length(); i++) {
            JSONObject jsonEntity = jsonEntities.getJSONObject(i);
            addEntity(String.valueOf(i), jsonEntity, jsonConfig);
        }

        return getDungeonResponseModel();

    }

    private void addEntity(String id, JSONObject jsonEntity, JSONObject jsonConfig) {
        String type = jsonEntity.getString("type");
        Position position = new Position(jsonEntity.getInt("x"), jsonEntity.getInt("y"));
        Entity newEntity = null;
        switch(type) {
            case "player":
                player = new Player(id, position, jsonConfig);
                newEntity = player;
                break;
            case "wall":
                newEntity = new Wall(id, position);
                break;
            case "key":
                newEntity = new Key(id, position);
                break;
            case "door":
                newEntity = new Door(id, position);
                break;
            case "exit":
                newEntity = new Exit(id, position, this);
                break;
            case "switch":
                newEntity = new FloorSwitch(id, position);
                break;
            case "spider":
                newEntity = new Spider(id, position, jsonConfig);
                break;
            case "zombie_toast":
                newEntity = new ZombieToast(id, position, jsonConfig);
                break;
            case "mercenary":
                newEntity = new Mercenary(id, position, jsonConfig);
                break;
            case "boulder":
                newEntity = new Boulder(this, id, position);
                break;
            case "zombie_toast_spawner":
                newEntity = new ZombieToastSpawner(this, id, position, jsonConfig.getInt("zombie_spawn_rate"), jsonConfig.getInt("zombie_attack"), jsonConfig.getInt("zombie_health"));
                break;
            case "portal":
                newEntity = new Portal(this, id, position, jsonEntity.getString("colour"));
                Portal newPortal = (Portal) newEntity;
                addPortal(newPortal);
                Portal partner = checkForPartner(newPortal);
                if (partner != null) {
                    partner.setLinkPosition(newPortal.getPosition());
                    newPortal.setLinkPosition(partner.getPosition());
                }
                break;
        default:
            return;
        }
        entities.add(newEntity);
    }

    public void spawnToast(int attack, int health, Position position) {
        Entity newEntity = new ZombieToast(UUID.randomUUID().toString(), position, attack, health);
        entities.add(newEntity);
    }
    
    public void addPortal(Portal add) {
        unpairedPortals.add(add);
    }

    public Portal checkForPartner(Portal finder) {
        for (Portal portal : unpairedPortals) {
            if (portal.getColour().equals(finder.getColour()) && !(finder.equals(portal))) {
                unpairedPortals.remove(portal);
                return portal;
            }
        }
        return null;
    }
    /**
     * /game/dungeonResponseModel
     */
    public DungeonResponse getDungeonResponseModel() {
        List<EntityResponse> entityResponseList = entities.stream()
                .map(Entity::getEntityResponse)
                .collect(Collectors.toList());

        return new DungeonResponse(
            dungeonId, dungeonName, entityResponseList, player.getInventory(),
            new ArrayList<>(), player.getBuildables(), goal);
    }

    /**
     * /game/tick/item
     */
    public DungeonResponse tick(String itemUsedId) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        //move player
    	entities.stream().filter(it -> it instanceof Player).forEach(
            x -> {
                Player p = (Player) x;
                p.updatePos(movementDirection, entities);
            }
        );

        // move entities
        entities.stream().filter(it -> (it instanceof DynamicEntity) && (it instanceof Player == false)).forEach(
            x -> {
                DynamicEntity y = (DynamicEntity) x;
                y.updatePos(movementDirection, entities);
            }
        );
        List <Entity> copy = new ArrayList<>();
        copy.addAll(entities);
        copy.stream().filter(x -> x instanceof ZombieToastSpawner).forEach(
            x -> {
                ZombieToastSpawner spawner = (ZombieToastSpawner) x;
                spawner.tick();
            }
        );
    
        return getDungeonResponseModel();
    }

    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return null;
    }
    public boolean switchActive() {
        for (Entity entity : entities) {
            if (entity instanceof FloorSwitch) {
                FloorSwitch check = (FloorSwitch) entity;
                return check.getActive();
            }
        }
       return false;
    }
    public boolean exitReached() {
        Exit exit = (Exit) entities.stream().filter(x -> x instanceof Exit).findFirst().orElse(null);
        return exit.getActive();
    }
    
    public Entity checkStaticCollision(Position pos) {
        List<Entity> colliders = this.entities.stream()
                .filter(x -> x.getPosition().equals(pos))
                .collect(Collectors.toList());

        return colliders.stream().filter(x -> x instanceof Boulder)
                .findFirst()
                .orElseGet(() -> colliders.stream().filter(x -> x instanceof StaticEntity).findFirst()
                .orElse(null));
    }
}
