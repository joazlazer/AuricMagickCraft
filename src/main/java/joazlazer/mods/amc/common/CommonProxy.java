package joazlazer.mods.amc.common;

import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.common.handlers.CapabilityHandler;
import joazlazer.mods.amc.common.handlers.GuiProxy;
import joazlazer.mods.amc.common.handlers.NetworkHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {

    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(AuricMagickCraft.instance, new GuiProxy());
        NetworkHandler.registerPackets();
        (new CapabilityHandler()).register();
    }

    public void postInit(FMLPostInitializationEvent e) {

    }
}
