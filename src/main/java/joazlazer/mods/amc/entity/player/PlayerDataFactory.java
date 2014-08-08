package joazlazer.mods.amc.entity.player;

import net.minecraft.entity.player.EntityPlayer;

import java.lang.ref.WeakReference;

public class PlayerDataFactory {
    public static PlayerData newData(EntityPlayer player) {
        PlayerData newData = new PlayerData();
        newData.player = new WeakReference<EntityPlayer>(player);
        for(String id : PlayerDataRegistry.Booleans.keySet()) newData.Booleans.put(id, PlayerDataRegistry.Booleans.get(id));
        for(String id : PlayerDataRegistry.Strings.keySet()) newData.Strings.put(id, PlayerDataRegistry.Strings.get(id));
        return newData;
    }
}
