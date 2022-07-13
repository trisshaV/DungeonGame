package dungeonmania.collectible;
import org.json.JSONObject;

public class Bow extends Buildable {
    private int durability;
    public Bow(String id, JSONObject config) {
        super(id, "bow");
        durability = config.getInt("bow_durability");
    }
    public int getDurability() {
        return durability;
    }

    public void setDurability(int num) {
        this.durability = num;
    }
}
