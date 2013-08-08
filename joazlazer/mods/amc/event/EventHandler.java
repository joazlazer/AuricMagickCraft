package joazlazer.mods.amc.event;

import joazlazer.mods.amc.client.gui.GuiAuraHUD;
import joazlazer.mods.amc.entity.player.ServerPlayerTracker;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public class EventHandler {
	public static EventHandler instance;
	
	public static void registerSubs() {
		instance = new EventHandler();
		MinecraftForge.EVENT_BUS.register(new GuiAuraHUD(Minecraft.getMinecraft()));
		MinecraftForge.EVENT_BUS.register(ServerPlayerTracker.instance);
	}
}
