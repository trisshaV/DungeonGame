package dungeonmania.collectible;

import dungeonmania.util.Position;

/**
 * Are among the collectible entities and is stored as Player inventory. Has following properties:
 *      - Can be collected when Player moves onto the square it is on.
 *      - Used in recipes to create buildable entities.
 *      - Player can only hold one key at a time.
 *      - There is ONLY ONE key for a corresponding door.
 *      - They will disappear if used to open a door or build an item.
 *      - If a key is used to build an item before opening its correspnding door, the key is destroyed in the process 
 *          as mentioned above and also consequently makes the correspnding door be locked forever.
 */
public class Key extends Collectible {
    private int keyId;

    /**
     * Key Constructor
     * @param id
     * @param xy
     * @param keyId
     */
    public Key(String id, Position xy, int keyId) {
        super(id, "key", xy);
        this.keyId = keyId;
    }
    
    /**
     * Sets key Id
     * @param id
     */
    public void setKeyId(int id) {
        keyId = id;
    }

    /**
     * Gets key Id
     * @return the Id of key
     */
    public int getKeyId() {
        return keyId;
    }

    /**
     * Gets type
     * @return the type, i.e. "key"
     */
    @Override
    public String getType() {
        return "key";
    }
}
