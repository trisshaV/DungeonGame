package dungeonmania;

import dungeonmania.collectible.Key;
import dungeonmania.dynamic_entity.Player;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.static_entity.Door;
import dungeonmania.static_entity.Exit;
import dungeonmania.static_entity.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

public class DungeonManiaController {

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
                newEntity = new Player(id, position, jsonConfig);
                player = newEntity;
                break;
            case "wall":
                newEntity = new Wall();
                break;
            case "key":
                newEntity = new Key();
                break;
            case "door":
                newEntity = new Door();
                break;
            case "exit":
                newEntity = new Exit();
                break;
        default:
            return;
        }
        newEntity.setId(id);
        newEntity.setPosition(position);
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
                x.setPosition(x.getPosition().translateBy(movementDirection));
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
}
