package dungeonmania;

import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONObject;

/**
 * SerializableJSONObject is used to replace JSON that support
 */
public class SerializableJSONObject extends HashMap<String, Object> {

    /**
     * constructor
     *
     * @param jsonObject json object
     */
    public SerializableJSONObject(JSONObject jsonObject) {

        Iterator<String> keys = jsonObject.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            put(key, jsonObject.get(key));
        }
    }    

    /**
     * get an integer by key 
     * @param key
     * @return integer
     */
    public int getInt(String key) {
        return Integer.parseInt(String.valueOf(get(key)));
    }
    
    /**
     * get an double by key 
     * @param key
     * @return double
     */
    public double getDouble(String key) {
        return Double.parseDouble(String.valueOf(get(key)));
    }

    /**
     * get the value by key
     * @param key
     * @return value
     */
    public String getString(String key) {
        return String.valueOf(get(key));
    }
}