package joazlazer.mods.amc.entity.player;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.HashMap;

public class PlayerDataRegistry {
    public static HashMap<String, Boolean> Booleans;
    public static HashMap<String, String> Strings;

    static {
        Booleans = new HashMap<String, Boolean>();
        Strings = new HashMap<String, String>();
    }

    public static void registerBoolean(String id, boolean defaultValue) throws KeyAlreadyExistsException {
        if(Booleans.containsKey(id)) throw new KeyAlreadyExistsException("[PlayerDataRegistry] Booleans already contains key '" + id + "'!!!");
        else {
            Booleans.put(id, defaultValue);
        }
    }

    public static void registerString(String id, String defaultValue) throws KeyAlreadyExistsException {
        if(Strings.containsKey(id)) throw new KeyAlreadyExistsException("[PlayerDataRegistry] Strings already contains key '" + id + "'!!!");
        else {
            Strings.put(id, defaultValue);
        }
    }
}
