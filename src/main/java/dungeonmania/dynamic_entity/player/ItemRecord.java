package dungeonmania.dynamic_entity.player;

public class ItemRecord {
    private String id;
    private String type;

    /**
     * ItemRecord Constructor
     * @param id
     * @param type
     */
    public ItemRecord(String id, String type) {
        this.id = id;
        this.type = type;
    }

    /**
     * Gets type
     * @return the type
     */
    public final String getType() {
        return type;
    }

    /**
     * Gets Id
     * @return the Id
     */
    public final String getId() {
        return id;
    }
}
