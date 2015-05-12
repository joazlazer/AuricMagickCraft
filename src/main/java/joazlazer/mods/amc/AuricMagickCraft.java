package joazlazer.mods.amc;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import joazlazer.mods.amc.api.order.ModOrders;
import joazlazer.mods.amc.api.order.OrderRegistry;
import joazlazer.mods.amc.api.spell.ModSpells;
import joazlazer.mods.amc.api.spell.SpellRegistry;
import joazlazer.mods.amc.block.ModBlocks;
import joazlazer.mods.amc.entity.player.PlayerTracker;
import joazlazer.mods.amc.handlers.ConfigurationHandler;
import joazlazer.mods.amc.handlers.EventHandler;
import joazlazer.mods.amc.item.ModItems;
import joazlazer.mods.amc.proxy.IProxy;
import joazlazer.mods.amc.reference.Reference;


@Mod(modid=Reference.MOD_ID, name=Reference.MOD_NAME, version=Reference.VERSION, guiFactory=Reference.GUI_FACTORY_CLASS)
public class AuricMagickCraft {

    @Mod.Instance(Reference.MOD_ID)
    public static AuricMagickCraft instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    public static PlayerTracker PlayerTracker;

    public static SimpleNetworkWrapper NetworkHandler;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Load the handlers from file.
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new ConfigurationHandler());

        // Register all blocks.
        ModBlocks.fullRegister();

        // Register all items.
        ModItems.fullRegister();

        // If we are on the client, register renders.
        proxy.registerCustomRenders();

        // If we are on the server, register the network handlers.
        proxy.registerNetworkHandlers();

        // If we are on the client, register key bindings.
        proxy.registerKeyHandlers();

        // Create an instance of the player tracker.
        PlayerTracker = new PlayerTracker();

        // If we are on the client, initialize the render helper.
        proxy.initializeRenderHandler();

        // Initialize the order map in the order registry.
        OrderRegistry.initOrderMap();

        // Initialize the casting manager.
        proxy.initCastingManager();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // Register all orders native to amc.
        ModOrders.registerOrders();

        // Register all spells native to amc.
        ModSpells.registerSpells();

        // Initialize the event handlers.
        EventHandler.initEventHandlers();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        // After mods have registered spells, calculate all the specialties.
        SpellRegistry.initSpecialties();
    }
}
