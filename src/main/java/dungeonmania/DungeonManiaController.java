package dungeonmania;

import dungeonmania.collectible.Arrow;
import dungeonmania.collectible.Bomb;
import dungeonmania.collectible.Collectible;
import dungeonmania.collectible.InvincibilityPotion;
import dungeonmania.collectible.InvisibilityPotion;
import dungeonmania.collectible.Key;
import dungeonmania.collectible.Sword;
import dungeonmania.collectible.Treasure;
import dungeonmania.collectible.Wood;
import dungeonmania.dynamic_entity.DynamicEntity;
import dungeonmania.dynamic_entity.Mercenary;
import dungeonmania.dynamic_entity.Player;
import dungeonmania.dynamic_entity.Spider;
import dungeonmania.dynamic_entity.ZombieToast;
import dungeonmania.dynamic_entity.player.BattleRecord;
import dungeonmania.dynamic_entity.player.ItemRecord;
import dungeonmania.dynamic_entity.player.RoundRecord;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.goal.*;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;
import dungeonmania.static_entity.ActiveBomb;
import dungeonmania.static_entity.Door.Door;
import dungeonmania.static_entity.Exit;
import dungeonmania.static_entity.FloorSwitch;
import dungeonmania.static_entity.Portal;
import dungeonmania.static_entity.StaticEntity;
import dungeonmania.static_entity.Wall;
import dungeonmania.static_entity.ZombieToastSpawner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.Random;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

public class DungeonManiaController implements Serializable{
    private int id;
    private JSONObject jsonConfig;
    private List<Portal> unpairedPortals;
    private List<Entity> entities;
    private Player player;
    private String dungeonId = "0";
    private Goal goalStrategy;
    private String dungeonName;
    private Observer observer;
    private Spiderspawner spiderspawner;


    /**
     * Gets skin
     * @return skin, i.e. "default"
     */
    public String getSkin() {
        return "default";
    }

    /**
     * Gets localisation
     * @return localisation, i.e., "en_US"
     */
    public String getLocalisation() {
        return "en_US";
    }

    /**
     * Load dungeons
     * /dungeons
     * @return dungeons
     */
    public static List<String> dungeons() {
        return FileLoader.listFileNamesInResourceDirectory("dungeons");
    }

    /**
     * Load configs
     * /configs
     * @return configs
     */
    public static List<String> configs() {
        return FileLoader.listFileNamesInResourceDirectory("configs");
    }

    /**
     * Create a new game
     * /game/new
     * @param dungeonName
     * @param configName
     * @return dungeon response model for the new game created
     * @throws IllegalArgumentException
     */
    public DungeonResponse newGame(String dungeonName, String configName) throws IllegalArgumentException {
        
        this.dungeonName = dungeonName;
        unpairedPortals = new ArrayList<>();
        entities = new ArrayList<>();
        player = null;
        dungeonId = String.valueOf(Integer.parseInt(dungeonId) + 1);

        String confContent;
        String dungeonContent;
        try {
            confContent = FileLoader.loadResourceFile("/configs/" + configName + ".json");
            dungeonContent = FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }

        JSONObject json = new JSONObject(dungeonContent);
        JSONObject jsonConfig = new JSONObject(confContent);
        JSONArray jsonEntities = json.getJSONArray("entities");
        this.jsonConfig = jsonConfig;

        goalStrategy = new SuperGoal(json.getJSONObject("goal-condition"), jsonConfig);
        spiderspawner = new Spiderspawner(this, jsonConfig.getInt("spider_attack"), jsonConfig.getInt("spider_health"), jsonConfig.getInt("spider_spawn_rate"));

        for (int i = 0; i < jsonEntities.length(); i++) {
            JSONObject jsonEntity = jsonEntities.getJSONObject(i);
            addEntity(String.valueOf(i), jsonEntity, jsonConfig);
        }
        id = entities.size();

        // Create observer
        for (Entity entity : entities) {
            if (entity.getType().equals("player")) {
                player = (Player)entity;
            }
        }
        this.observer = new Observer();
        return getDungeonResponseModel();
    }

    /**
     * Add entities to game before start
     * @param id
     * @param jsonEntity
     * @param jsonConfig
     */
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
                Door newDoor = (Door) newEntity;
                Key newKey = findKey(jsonEntity.getInt("key"));
                if (newKey != null) {
                    newDoor.setKey(newKey);
                }
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
            case "ActiveBomb":
                newEntity = new ActiveBomb(id, position);
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
                newEntity = new InvincibilityPotion(id, position, jsonConfig);
                break;
            case "invisibility_potion":
                newEntity = new InvisibilityPotion(id, position, jsonConfig);
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
    
    /**
     * Spawn spiders in game
     * @param attack
     * @param health
     */
    public void spawnSpider(int attack, int health) {
        Entity newEntity = new Spider(UUID.randomUUID().toString(), getRandomPosition(), attack, health);
        entities.add(newEntity);
    }

    /**
     * Gets random position
     * @return random positions
     */
    public Position getRandomPosition() {
        Entity player = entities.stream().filter(x -> x instanceof Player).findFirst().get();
        Player target = (Player) player;
        Position pos = target.getPosition();

        Random rand = new Random();
        Position randomPos = (new Position(pos.getX() + rand.nextInt(6) + 1 , pos.getY()));
        if (entities.stream().anyMatch(x -> (x instanceof Boulder) && (x.getPosition().equals(randomPos)))) {
            return getRandomPosition();
        }
        return randomPos;
       
    }

    /**
     * Spawns Zombie Toast
     * @param attack
     * @param health
     * @param position
     */
    public void spawnToast(int attack, int health, Position position) {
        Entity newEntity = new ZombieToast(UUID.randomUUID().toString(), position, attack, health);
        entities.add(newEntity);
    }
    
    /**
     * Adds portals to game
     * @param add
     */
    public void addPortal(Portal add) {
        unpairedPortals.add(add);
    }

    /**
     * Checks for partner portals
     * @param finder
     * @return corresponding portal
     */
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
     * Finds keys
     * @param i
     * @return corresponding key
     */
    public Key findKey(int i) {
        for (Entity entity: entities) {
            if (entity instanceof Key) {
                Key target = (Key) entity;
                if (target.getKeyId() == i) {
                    return target;
                }
            }
        }
        return null;
    }
    /**
     * Gets dunegeon response models
     * /game/dungeonResponseModel
     * @return dungeon response model
     */
    public DungeonResponse getDungeonResponseModel() {
        List<EntityResponse> entityResponseList = entities.stream()
                .map(Entity::getEntityResponse)
                .collect(Collectors.toList());
        return new DungeonResponse(
            dungeonId, dungeonName, entityResponseList, player.getInventory().getItemResponses(),
            listBattleResponses(), player.getBuildables(), goalStrategy.getGoal(entities));
    }
    
    /**
     * Checks for entity is consumable
     * @return any valid consumable
     */
    public List<String> validConsumable() {
        return Arrays.asList("bomb","invincibility_potion", "invisibility_potion");
    }
    /**
     * In game ticks
     * /game/tick/item
     * @param itemUsedId
     * @return executed ticks for game
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse tick(String itemUsedId) throws IllegalArgumentException, InvalidActionException {
        Position pos = player.getPosition();
        Collectible item = player.getItemById(itemUsedId);
        if (item == null) {
            throw new InvalidActionException("itemUsed is not in the player's inventory");
        }
        if (!validConsumable().contains(item.getType())) {
            throw new IllegalArgumentException("itemUsed must be one of bomb, invincibility_potion, invisibility_potion");
        }
        if (item.getType().equals("bomb")) {
            entities.add(new ActiveBomb(itemUsedId, pos));
            player.removeItem(item);
        }
        if (item.getType().equals("invincibility_potion")) {
            player.consumePotion(item);
            player.removeItem(item);
        }
        if (item.getType().equals("invisibility_potion")) {
            player.consumePotion(item);
            player.removeItem(item);
        }
        player.tickPotionEffects();
        
        // move Dynamic entities except Player
        entities.stream().filter(it -> (it instanceof DynamicEntity) && (it instanceof Player == false)).forEach(
            x -> {
                DynamicEntity y = (DynamicEntity) x;
                y.updatePos(null, entities);
            }
        );
        if (this.observer.checkBattle(entities) == true) {
            entities = removeDeadEntities();
        }

        // check if the bomb will explode
        List<Entity> toRemove = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.getType().equals("switch")) {
                FloorSwitch floorSwitch = (FloorSwitch) entity;
                if (floorSwitch.getActive()) {
                    toRemove.addAll(floorSwitch.activateNearby(entities, jsonConfig));
                }
                
            }
        }
        entities.removeAll(toRemove);
        return getDungeonResponseModel();
    }

    /**
     * Get rid of deceased entities from game
     * @return deceased entities
     */
    private List<Entity> removeDeadEntities() {
        List <Entity> result = new ArrayList<>();
        result = entities.stream().filter(e -> !(e instanceof DynamicEntity) || ((e instanceof DynamicEntity) && ((DynamicEntity)e).getHealth() > 0)).collect(Collectors.toList());
        return result;
    }

    /**
     * In game ticks for movement
     * /game/tick/movement
     * @param movementDirection
     * @return executed ticks for movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        //move player
        entities.stream().filter(it -> it instanceof Player).forEach(
            x -> {
                Player p = (Player) x;
                p.updatePos(movementDirection, entities);
            }
        );
        player.tickPotionEffects();
        boolean battleOccured = this.observer.checkBattle(entities);
        if (battleOccured) {
            entities = removeDeadEntities();
        }
        // move Dynamic entities except Player
        entities.stream().filter(it -> (it instanceof DynamicEntity) && (it instanceof Player == false)).forEach(
            x -> {
                DynamicEntity y = (DynamicEntity) x;
                y.updatePos(movementDirection, entities);
            }
        );
        battleOccured = this.observer.checkBattle(entities);
        if (battleOccured) {
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
        spiderspawner.tick();
        
        // Check if the bomb will explode
        List<Entity> toRemove = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.getType().equals("switch")) {
                FloorSwitch floorSwitch = (FloorSwitch) entity;
                if (floorSwitch.getActive()) {
                    toRemove.addAll(floorSwitch.activateNearby(entities, jsonConfig));
                }
                
            }
        }
        entities.removeAll(toRemove);
        return getDungeonResponseModel();
    }

    public List<String> validBuildables() {
        return Arrays.asList("bow", "shield");
    }

    /**
     * Check if buildable entity can be constructed
     * /game/build
     * @param buildable
     * @return corresponding validity, and if so buildable id and dungeon response model
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        if (!validBuildables().contains(buildable)) {
            throw new IllegalArgumentException();
        }
        Inventory playerInv = player.getInventory();
        if (!playerInv.CheckMaterials(buildable)) {
            throw new InvalidActionException("Not enough materials!");
        }

        if (playerInv.buildItem(buildable, String.valueOf(id))) {
            id ++;
        }
        return getDungeonResponseModel();
    }

    /**
     * Interactions in game
     * /game/interact
     * @param entityId
     * @return congruent interactions
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        Entity target = entities.stream().filter(x -> x.getId() == entityId).findFirst().orElse(null);
        if (target == null) {
            throw new IllegalArgumentException();
        }
        target.interact(player);
        return getDungeonResponseModel();
    }

    /**
     * Active status of switch
     * @return boolean of status
     */
    public boolean switchActive() {
        for (Entity entity : entities) {
            if (entity instanceof FloorSwitch) {
                FloorSwitch check = (FloorSwitch) entity;
                return check.getActive();
            }
        }
       return false;
    }
    /**
     * Status of exit reached
     * @return boolean of exit status
     */
    public boolean exitReached() {
        Exit exit = (Exit) entities.stream().filter(x -> x instanceof Exit).findFirst().orElse(null);
        return exit.getActive();
    }
    
    /**
     * Checks for static entity collisions
     * @param pos
     * @return relevant collisions
     */
    public Entity checkStaticCollision(Position pos) {
        List<Entity> colliders = this.entities.stream()
                .filter(x -> x.getPosition().equals(pos))
                .collect(Collectors.toList());

        return colliders.stream().filter(x -> x instanceof Boulder)
                .findFirst()
                .orElseGet(() -> colliders.stream().filter(x -> x instanceof StaticEntity).findFirst()
                .orElse(null));
    }

    /**
     * List battle responses
     * @return corresponding responses from battles
     */
    public List<BattleResponse> listBattleResponses() {
        // Convert battles to battleResponses
        List<BattleRecord> listOfBattles = this.observer.getBattleRecords();

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

    /**
     * Conversion of round records
     * @param roundRecords
     * @return converted records of rounds
     */
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

    /**
     * Conversion of item responses
     * @param itemsUsed
     * @return converted responses of items
     */
    private List<ItemResponse> convertItemResponse(List<ItemRecord> itemsUsed) {
        List <ItemResponse> result = new ArrayList<>();
        itemsUsed.stream().forEach(
            item -> {
                result.add(new ItemResponse(item.getId(), item.getType()));
            }
        );
        return result;
    }

    /**
     * Remove an entity
     * @param id
     */
    public void removeEntity(String id) {
        Entity remove = entities.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
        if (remove != null) {
            entities.remove(remove);
        }
    }

    public String getMercStatus() {
        for (Entity entity : entities) {
            if (entity instanceof Mercenary) {
                Mercenary check = (Mercenary) entity;
                return check.getStatus();
            }
        }
       return null;
    }

    /**
     * /game/save
     */
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        
        
        ArrayList<Object> objects = new ArrayList<>();
        
        objects.add(player.getPosition().getX());  
        objects.add(player.getPosition().getY());  
        
        try {
            FileOutputStream f = new FileOutputStream(new File(name + ".game.dat"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            
            o.writeObject(objects);
            
            o.close();
                        
        }catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }         
        
       return getDungeonResponseModel();
    }

    /**
     * /game/load
     */
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        
        try {
            
            FileInputStream fi = new FileInputStream(new File(name + ".game.dat"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            
            ArrayList<Object> objects = (ArrayList<Object>) oi.readObject();
            
            player.setPosition(new Position((Integer)objects.get(0),
            (Integer)objects.get(1)));
           
            oi.close();
                        
        }catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }         
        
        return getDungeonResponseModel();
    }

    /**
     * /games/all
     */
    public List<String> allGames() {
        
        List<String> games = new ArrayList<>();
        
        File f = new File(".");
        for (String name: f.list()){
            if (name.endsWith(".game.dat")){
                games.add(name.substring(0, name.indexOf(".game.dat")));
            }
        }        
        return games;
    }

}
