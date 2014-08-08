package joazlazer.mods.amc.proxy;

import cpw.mods.fml.common.network.NetworkRegistry;
import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.handlers.GuiHandlerAMC;
import joazlazer.mods.amc.handlers.NetworkHandler;

public abstract class CommonProxy implements IProxy {
    @Override
    public void registerNetworkHandlers() {
        NetworkRegistry.INSTANCE.registerGuiHandler(AuricMagickCraft.instance, new GuiHandlerAMC());
        NetworkHandler.init();
    }
}
