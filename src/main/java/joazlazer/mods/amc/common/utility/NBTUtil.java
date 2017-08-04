package joazlazer.mods.amc.common.utility;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class NBTUtil {
    public static void writeResourceLocation(NBTTagCompound nbt, String key, ResourceLocation rl) {
        NBTTagCompound rlTag = new NBTTagCompound();
        rlTag.setString("domain", rl.getResourceDomain());
        rlTag.setString("path", rl.getResourcePath());
        nbt.setTag(key, rlTag);
    }

    public static ResourceLocation getResourceLocation(NBTTagCompound nbt, String key) {
        NBTTagCompound rlTag = nbt.getCompoundTag(key);
        String domain = rlTag.getString("domain");
        String path = rlTag.getString("path");
        return new ResourceLocation(domain, path);
    }
}
