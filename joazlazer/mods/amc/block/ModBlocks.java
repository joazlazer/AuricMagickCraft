package joazlazer.mods.amc.block;

import joazlazer.mods.amc.item.ModItems.ITEMS;
import joazlazer.mods.amc.lib.Strings;
import joazlazer.mods.amc.tileentity.TileEntityAwakeningTable;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class ModBlocks 
{
    // Global Block Definitions.
    public static class BLOCKS
    {
       public static Block awakeningTable;
    }
    
    // Block Initialization
    public static void initBlocks()
    {
        BLOCKS.awakeningTable = new BlockAwakeningTable();
    }
    
    // Block Registry.
    public static void registerBlocks()
    {
        GameRegistry.registerBlock(BLOCKS.awakeningTable, Strings.AWAKENING_TABLE_NAME);
    }
    
    // Tool Registry
    public static void registerTools()
    {
        MinecraftForge.setBlockHarvestLevel(BLOCKS.awakeningTable, "axe", 0);
    }
    
    // Name Registry.
    public static void registerNames()
    {
        LanguageRegistry.addName(BLOCKS.awakeningTable, "Awakening Table");
    }
    
    // Register the shaped recipes.
    public static void registerRecipes()
    {
        GameRegistry.addRecipe(new ItemStack(BLOCKS.awakeningTable),
                new Object[] {"abc", "ded", "fff",
            'a', Item.ingotGold, 
            'b', ITEMS.emeraldTablet,
            'c', Item.book,
            'd', Block.stone,
            'e', Item.diamond,
            'f', Block.bookShelf});
        GameRegistry.addRecipe(new ItemStack(BLOCKS.awakeningTable),
                new Object[] {"abc", "ded", "fff",
            'a', Item.book, 
            'b', ITEMS.emeraldTablet,
            'c', Item.ingotGold,
            'd', Block.stone,
            'e', Item.diamond,
            'f', Block.bookShelf});
    }
    
    // Register the shapeless recipes.
    public static void registerShapelessRecipes()
    {
        
    }
    
    // Register any mod/furnace/brewing/ore recipes.
    public static void registerOtherRecipes()
    {
     
    }
    
    // Call all of the different methods.
    public static void fullRegister()
    {
        // Initialize the blocks.
        initBlocks();
        
        // Register the blocks.
        registerBlocks();
        
        // Add the names.
        registerNames();
        
        // Register the tools.
        registerTools();
        
        // Register the tile entities.
        registerTileEntities();
    }
    
    // Full Recipe registry
    public static void registerAllRecipes()
    {
        // Register all of the recipes.
        registerRecipes();
        registerShapelessRecipes();
        registerOtherRecipes();
    }
    
    // Registering of the tile entities.
    public static void registerTileEntities()
    {
    	// Register all of the tile entities.
    	GameRegistry.registerTileEntity(TileEntityAwakeningTable.class, Strings.AWAKENING_TABLE_TE_ID);
    }
}
