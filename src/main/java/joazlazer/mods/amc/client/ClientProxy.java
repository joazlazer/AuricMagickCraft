package joazlazer.mods.amc.client;

import joazlazer.mods.amc.client.gui.GuiAmcIngame;
import joazlazer.mods.amc.common.CommonProxy;
import joazlazer.mods.amc.common.handlers.GuiProxy;
import joazlazer.mods.amc.common.handlers.KeyHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        MinecraftForge.EVENT_BUS.register(new KeyHandler());
        MinecraftForge.EVENT_BUS.register(new GuiAmcIngame());
        MinecraftForge.EVENT_BUS.register(new GuiProxy());
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }
}
