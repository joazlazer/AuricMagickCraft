package joazlazer.mods.amc;

import java.io.File;

import joazlazer.mods.amc.block.ModBlocks;
import joazlazer.mods.amc.client.gui.GuiHandler;
import joazlazer.mods.amc.creativetabs.CreativeTabAMC;
import joazlazer.mods.amc.item.ModItems;
import joazlazer.mods.amc.lib.Reference;
import joazlazer.mods.amc.orders.ModOrders;
import joazlazer.mods.amc.orders.OrderRegistry;
import joazlazer.mods.amc.spells.ModSpells;
import joazlazer.mods.amc.spells.SpellRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class AuricMagickCraft
{
    // Global variable definitions.
    public static boolean debugMode;
    
    // The creative tab used for all of the items/blocks in the mod.
    public static CreativeTabs tabsAMC = new CreativeTabAMC(CreativeTabs.getNextID(), Reference.MOD_ID);
    
    // Create a static instance of this class.
    @Instance(Reference.MOD_ID)
    public static AuricMagickCraft instance;
    
    // The preinitialization method for the mod.
    // Used to load resources, config files, update code, etc.
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	// Initialize the order API's OrderRegistry class.
    	OrderRegistry.initOrderMap();
    	
    	// Create a new file.
    	File configFile = event.getSuggestedConfigurationFile();
    	
    	// Load the config options.
    	ConfigSheet.load(configFile);
    	
    	debugMode = true;
    	
    	// Print that we are in debug mode.
    	AMCLogger.debugLog(Level.WARN, "We are now in debug mode for AuricMagickCraft.");
    	AMCLogger.debugLog(Level.WARN, "If this was not intended, check your configuration file.");
    
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
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
		// some example code
        System.out.println("DIRT BLOCK >> "+Blocks.dirt.func_149739_a());
    	
    	System.out.println("If you hear the sound if my voice,");
    	System.out.println("then beleive it or not, but minecraft forge 1.7 is working :D");
    	
    	// Initialize the gui handler.
        new GuiHandler();
        
        // Register the spells.
        ModSpells.registerSpells();
        
        // Register my mod's event subscribers/handlers.
        joazlazer.mods.amc.event.EventHandler.instance.registerSubs();
    }
    
    // The postinitialization method for the mod.
    // Used to register mod compatibility classes and procedures.
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	// Initialize the specialties.
    	SpellRegistry.initSpecialties();
    }
    
    
}
