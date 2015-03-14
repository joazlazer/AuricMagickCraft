package joazlazer.mods.amc.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.casting.CastingManager;
import joazlazer.mods.amc.handlers.ModGuiHandler;
import joazlazer.mods.amc.handlers.NetworkHandler;
import joazlazer.mods.amc.util.LogHelper;

public abstract class CommonProxy implements IProxy {
    @Override
    public void registerNetworkHandlers() {
        NetworkRegistry.INSTANCE.registerGuiHandler(AuricMagickCraft.instance, new ModGuiHandler());
        try {
            NetworkHandler.init();
        }
        catch(NoClassDefFoundError ex) {
            LogHelper.warn("dafuq");
            ex.printStackTrace();
        }
    }

    @Override
    public void initCastingManager() {
        FMLCommonHandler.instance().bus().register(new CastingManager());
    }
}
