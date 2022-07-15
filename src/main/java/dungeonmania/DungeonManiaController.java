package dungeonmania;

import dungeonmania.collectible.Arrow;
import dungeonmania.collectible.Bomb;
import dungeonmania.collectible.Bow;
import dungeonmania.collectible.Buildable;
import dungeonmania.collectible.Collectible;
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
import dungeonmania.dynamic_entity.player.BattleRecord;
import dungeonmania.dynamic_entity.player.RoundRecord;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.static_entity.ActiveBomb;
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
import dungeonmania.Inventory;
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

    private int id;
    private List<Portal> unpairedPortals = new ArrayList<>();
    private List<Entity> entities = new ArrayList<>();
    private Player player = null;
	private String dungeonId = "1";	
    private String goal;
	private String dungeonName;
    private Observer observer;

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

        // Create observer
        Player player = null;
        for (Entity entity : entities) {
            if (entity.getType().equals("player")) {
                player = (Player)entity;
            }
        }
        observer = new Observer(entities, player);
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
            case "switch":
                newEntity = new FloorSwitch(id, position);
                break;
            case "exit":
                newEntity = new Exit(id, position, this);
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
            dungeonId, dungeonName, entityResponseList, player.getInventory().getItemResponses(),
            listBattleResponses(), player.getBuildables(), goal);
    }

    /**
     * /game/tick/item
     */
    public DungeonResponse tick(String itemUsedId) throws IllegalArgumentException, InvalidActionException {

        Position pos = player.getPosition();
        Collectible item = player.getItemById(itemUsedId);
        if (item.getType().equals("bomb")) {
            entities.add(new ActiveBomb(itemUsedId, pos));
            player.removeItem(item);
        }
        if (item.getType().equals("invincibility_potion")) {
            player.removeItem(item);
        }
        if (item.getType().equals("invisibility_potion")) {
            player.removeItem(item);
        }
        
        // move Dynamic entities except Player
        entities.stream().filter(it -> (it instanceof DynamicEntity) && (it instanceof Player == false)).forEach(
            x -> {
                DynamicEntity y = (DynamicEntity) x;
                y.updatePos(null, entities);
            }
        );
        if (observer.checkBattle() == true) {
            entities = removeDeadEntities();
        }
        return getDungeonResponseModel();
    }

    private List <Entity> removeDeadEntities() {
        List <Entity> result = new ArrayList<>();
        result = entities.stream().filter(e -> e instanceof DynamicEntity && ((DynamicEntity)e).getHealth() < 0).collect(Collectors.toList());
        return result;
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
        if (observer.checkBattle() == true) {
            entities = removeDeadEntities();
        }
        // move Dynamic entities except Player
        entities.stream().filter(it -> (it instanceof DynamicEntity) && (it instanceof Player == false)).forEach(
            x -> {
                DynamicEntity y = (DynamicEntity) x;
                y.updatePos(movementDirection, entities);
            }
        );
        if (observer.checkBattle() == true) {
            entities = removeDeadEntities();
        }
        player.pickUp(entities);
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

    public List<BattleResponse> listBattleResponses() {
        // Convert battles to battleResponses
        List<BattleRecord> listOfBattles = observer.getBattleRecords();

        List<BattleResponse> result = new ArrayList<>();
        listOfBattles.stream().forEach(
            battle -> {
                List<RoundResponse> roundResponses = convertRoundRecords(battle.getRounds());
                DynamicEntity temp = battle.getEnemy();
                result.add(new BattleResponse(temp.getType(), roundResponses, battle.getInitialPlayerHealth(), battle.getInitialEnemyHealth()));
            }
        );

        return result;
    }

    public List<RoundResponse> convertRoundRecords(List<RoundRecord> roundRecords) {
        List<RoundResponse> result = new ArrayList<>();
        roundRecords.stream().forEach(
            round -> {
                List<ItemResponse> itemResponses = convertItemResponse(round.getItemsUsed());
                result.add(new RoundResponse(round.getChangePlayerHealth(), round.getChangeEnemyHealth(), itemResponses));
            }
        );
        return result;
    }

    private List<ItemResponse> convertItemResponse(List<Object> itemsUsed) {
        List <ItemResponse> result = new ArrayList<>();
        itemsUsed.stream().forEach(
            item -> {
                if (item instanceof Collectible) {
                    Collectible temp = (Collectible)item;
                    result.add(new ItemResponse(temp.getId(), temp.getType()));
                } else if (item instanceof Buildable) {
                    Buildable temp = (Buildable)item;
                    result.add(new ItemResponse(temp.getId(), temp.getType()));
                }
            }
        );
        return result;
    }
}