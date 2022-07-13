package dungeonmania;

import dungeonmania.collectible.Arrow;
import dungeonmania.collectible.Bomb;
import dungeonmania.collectible.Bow;
import dungeonmania.collectible.InvincibilityPotion;
import dungeonmania.collectible.InvisibilityPotion;
import dungeonmania.collectible.Key;
import dungeonmania.collectible.Shield;
import dungeonmania.collectible.Sword;
import dungeonmania.collectible.Treasure;
import dungeonmania.collectible.Wood;
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
import dungeonmania.static_entity.StaticEntity;
import dungeonmania.static_entity.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;
import dungeonmania.Inventory;
import javassist.expr.Instanceof;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterRegistration.Dynamic;

import org.json.JSONArray;
import org.json.JSONObject;

public class DungeonManiaController {

    private int id;
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
        id = entities.size();

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
                newEntity = new Key(id, position, jsonEntity.getInt("key"));
                break;
            case "door":
                newEntity = new Door(id, position);
                break;
            case "exit":
                newEntity = new Exit(id, position);
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
            case "bomb":
                newEntity = new Bomb(id, position, jsonConfig);
                break;
            case "sword":
                newEntity = new Sword(id, position, jsonConfig);
                break;
            case "arrow":
                newEntity = new Arrow(id, position, jsonConfig);
                break;
            case "wood":
                newEntity = new Wood(id, position, jsonConfig);
                break;
            case "treasure":
                newEntity = new Treasure(id, position, jsonConfig);
                break;
            case "invincibility_potion":
                newEntity = new InvisibilityPotion(id, position, jsonConfig);
                break;
            case "invisibility_potion":
                newEntity = new InvincibilityPotion(id, position, jsonConfig);
                break;
        default:
            return;
        }
        entities.add(newEntity);
    }

    /**
     * /game/dungeonResponseModel
     */
    public DungeonResponse getDungeonResponseModel() {
        List<EntityResponse> entityResponseList = entities.stream()
                .map(Entity::getEntityResponse)
                .collect(Collectors.toList());

        return new DungeonResponse(
            dungeonId, dungeonName, entityResponseList, player.getInventory().getItemResponses(),
            new ArrayList<>(), player.getBuildables(), goal);
    }

    /**
     * /game/tick/item
     */
    public DungeonResponse tick(String itemUsedId) throws IllegalArgumentException, InvalidActionException {
        return getDungeonResponseModel();
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
        player.pickUp(entities);
    
        return getDungeonResponseModel();
    }

    public List<String> validBuildables() {
        return Arrays.asList("bow", "shield");
    }

    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        if (!validBuildables().contains(buildable)) {
            throw new IllegalArgumentException();
        }
        Inventory playerInv = player.getInventory();
        if (!playerInv.hasEnoughMaterials(buildable)) {
            throw new InvalidActionException("Not enough materials!");
        }

        if (playerInv.buildItem(buildable, String.valueOf(id))) {
            id ++;
        }
        return getDungeonResponseModel();
    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    public Entity checkStaticCollision(Position pos) {
        List<Entity> colliders =  this.entities.stream().filter(x -> x.getPosition().equals(pos)).collect(Collectors.toList());
        if (colliders.stream().filter(x -> x instanceof Boulder).findFirst().orElse(null) == null) {
            return colliders.stream().filter(x -> x instanceof StaticEntity).findFirst().orElse(null);
        }
        return colliders.stream().filter(x -> x instanceof Boulder).findFirst().orElse(null);
    }
}