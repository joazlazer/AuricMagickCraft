package joazlazer.mods.amc.entity.player;

import joazlazer.mods.amc.util.exception.MapNoContainsKeyException;
import net.minecraft.entity.player.EntityPlayer;
import java.lang.ref.WeakReference;
import java.util.TreeMap;

public class PlayerData {

    public PlayerData() {
        Booleans = new TreeMap<String, Boolean>();
        Strings = new TreeMap<String, String>();
    }

    public TreeMap<String, Boolean> Booleans;
    public TreeMap<String, String> Strings;
    public WeakReference<EntityPlayer> player;

    public boolean getBoolean(String id) throws MapNoContainsKeyException {
        if(Booleans.containsKey(id)) return Booleans.get(id);
        else throw new MapNoContainsKeyException("[PlayerData] Booleans.contains('" + id + "'); returns false!");
    }

    public boolean getBoolean(String id, boolean defaultValue){
        if(Booleans.containsKey(id)) return Booleans.get(id);
        else return defaultValue;
    }

    public PlayerData setBoolean(String id, boolean newValue) throws MapNoContainsKeyException {
        if(Booleans.containsKey(id)) Booleans.put(id, newValue);
        else throw new MapNoContainsKeyException("[PlayerData] Booleans.contains('" + id + "'); returns false!");
        return this;
    }

    public String getString(String id) throws MapNoContainsKeyException {
        if(Strings.containsKey(id)) return Strings.get(id);
        else throw new MapNoContainsKeyException("[PlayerData] Stings.contains('" + id + "'); returns false!");
    }

    public String getString(String id, String defaultValue) {
        if(Strings.containsKey(id)) return Strings.get(id);
        else return defaultValue;
    }

    public PlayerData setString(String id, String newValue) throws MapNoContainsKeyException {
        if(Strings.containsKey(id)) Strings.put(id, newValue);
        else throw new MapNoContainsKeyException("[PlayerData] Stings.contains('" + id + "'); returns false!");
        return this;
    }
}
