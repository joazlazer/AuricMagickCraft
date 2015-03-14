package joazlazer.mods.amc.network;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class ClientWrapper {
    public static World getTheWorld() {
        return Minecraft.getMinecraft().theWorld;
    }
}
