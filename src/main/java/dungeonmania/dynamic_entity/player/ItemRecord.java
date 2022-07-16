package dungeonmania.dynamic_entity.player;

public class ItemRecord {
    private String id;
    private String type;

    public ItemRecord(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public final String getType() {
        return type;
    }

    public final String getId() {
        return id;
    }
}
