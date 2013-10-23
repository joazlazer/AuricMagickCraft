package joazlazer.mods.amc.event;

import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.client.gui.GuiAuraHUD;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class EventHandler {
	public static EventHandler instance;
	
	public static void registerSubs() {
		instance = new EventHandler();
		MinecraftForge.EVENT_BUS.register(new GuiAuraHUD(Minecraft.getMinecraft()));
		TickRegistry.registerScheduledTickHandler(AuricMagickCraft.auraUpdater, Side.SERVER);
	}
}
