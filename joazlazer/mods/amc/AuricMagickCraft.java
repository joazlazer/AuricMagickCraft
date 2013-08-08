package joazlazer.mods.amc;

import java.io.File;

import joazlazer.mods.amc.block.ModBlocks;
import joazlazer.mods.amc.client.ClientProxy;
import joazlazer.mods.amc.client.gui.GuiHandler;
import joazlazer.mods.amc.creativetabs.CreativeTabAMC;
import joazlazer.mods.amc.event.EventHandler;
import joazlazer.mods.amc.item.ModItems;
import joazlazer.mods.amc.lib.BlockIds;
import joazlazer.mods.amc.lib.Reference;
import joazlazer.mods.amc.network.PacketHandler;
import joazlazer.mods.amc.orders.ModOrders;
import joazlazer.mods.amc.orders.OrderRegistry;
import joazlazer.mods.amc.playertracking.PlayerTracker;
import joazlazer.mods.amc.spells.ModSpells;
import joazlazer.mods.amc.spells.SpellRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;


@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, 
	channels = {"AmcAwaken", "AmcPlayerRespawn", "AwakenFail"}, packetHandler = PacketHandler.class)
public class AuricMagickCraft
{
	// Create a static instance of this class.
    @Instance(Reference.MOD_ID)
    public static AuricMagickCraft instance;
    
    // Define the proxies used for the mod.
    @SidedProxy(clientSide="joazlazer.mods.amc.client.ClientProxy", 
    		serverSide="joazlazer.mods.amc.CommonProxy")
    public static CommonProxy proxy;
    public static ClientProxy cProxy;
    
    // Global variable definitions.
    public static boolean debugMode;
    
    // The creative tab used for all of the items/blocks in the mod.
    public static CreativeTabs tabsAMC = new CreativeTabAMC(CreativeTabs.getNextID(), Reference.MOD_ID);
    	
    // The preinitialization method for the mod.
    // Used to load resources, config files, update code, etc.
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	// Initialize the order API's OrderRegistry class.
    	OrderRegistry.initOrderMap();
    	
    	// Create a new file.
    	File configFile = event.getSuggestedConfigurationFile();
    	
    	// Initialize the config.
    	getFromConfig(new Configuration(configFile));
    	
    	// Print that we are in debug mode.
    	if(debugMode) System.out.println("We are now in debug mode for AMC.");
    	if(debugMode) System.out.println("If this was not intended, check your configuration file.");
    	
        // Fully register the mod blocks.
        ModBlocks.fullRegister();
        
        // Fully register the mod items.
        ModItems.fullRegister();
        
        // Register all mod recipes.
        ModBlocks.registerAllRecipes();
        ModItems.registerAllRecipes();
        
        // Register the different orders.
        ModOrders.registerOrders();
        
        // Initialize the spells.
        ModSpells.initSpells();
    }
    
    // The initialization method for the mod.
    // Used to register blocks, items, names, and do main loading procedures.
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        // Register the custom creative tab's name in the language registry.
        LanguageRegistry.instance().addStringLocalization("itemGroup.AMC",
         "en_US", "Auric Magick Craft");
        
        // Initialize the gui handler.
        new GuiHandler();
        
        // Register the spells.
        ModSpells.registerSpells();
        
        // Register my mod's event subscribers/handlers.
        EventHandler.registerSubs();
        
        // Initialize the instances of the tick handlers.
        PlayerTrackerClientTickHandler.initInstance();
        PlayerTrackerServerTickHandler.initInstance();
    }
    
    // The postinitialization method for the mod.
    // Used to register mod compatibility classes and procedures.
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	// Initialize the specialties.
    	SpellRegistry.initSpecialties();
    	
    	// Register the player tracker.
    	GameRegistry.registerPlayerTracker(new PlayerTracker());
    }
    
    // The method used to get everything from the config.
    public static void getFromConfig(Configuration config)
    {
    	// Open the configuration file.
    	config.load();
    	
    	// Create a new property for the debug mode.
    	Property debug = config.get(
    			Configuration.CATEGORY_GENERAL, 
    			"debugMode", 
    			false);
    	
    	// Comment on the debug info.
    	debug.comment = "Unintended side effects may occur when using this. " +
    			"Only use this if you know what you're doing.";
    	
    	// Grab a boolean.
    	debugMode = debug.getBoolean(false);
    	
    	// Get the block ids.
    	BlockIds.AWAKENING_TABLE = config.get(Configuration.CATEGORY_BLOCK, "awakeningTable", BlockIds.AWAKENING_TABLE_DEFAULT).getInt();
    	
    	// Save the configuration file.
    	config.save();
    }
}
