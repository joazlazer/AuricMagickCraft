package joazlazer.mods.amc;

import joazlazer.mods.amc.common.CommonProxy;
import joazlazer.mods.amc.common.reference.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = AuricMagickCraft.MODID, version = AuricMagickCraft.VERSION, useMetadata = true)
public class AuricMagickCraft {
    public static final String MODID = Reference.MOD_ID;
    public static final String VERSION = Reference.VERSION;

    @Mod.Instance
    public static AuricMagickCraft instance;

    @SidedProxy(clientSide="joazlazer.mods.amc.client.ClientProxy", serverSide="joazlazer.mods.amc.server.ServerProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
