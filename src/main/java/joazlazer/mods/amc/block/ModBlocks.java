package joazlazer.mods.amc.block;

import joazlazer.mods.amc.lib.Strings;
import joazlazer.mods.amc.tileentity.TileEntityAwakeningTable;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks 
{
    // Global Block Definitions.
    public static class BLOCKS
    {
       public static Block awakeningTable;
       public static Block magicTable;
    }
    
    // Block Initialization
    public static void initBlocks()
    {
        BLOCKS.awakeningTable = new BlockAwakeningTable();
        BLOCKS.magicTable = new BlockMagicTable();
    }
    
    // Block Registry.
    public static void registerBlocks()
    {
        GameRegistry.registerBlock(BLOCKS.awakeningTable, Strings.AWAKENING_TABLE_NAME);
        GameRegistry.registerBlock(BLOCKS.magicTable, Strings.MAGIC_TABLE_NAME);
    }
    
    // Tool Registry
    public static void registerTools()
    {
       // MinecraftForge.setBlockHarvestLevel(BLOCKS.awakeningTable, "axe", 0);
      // MinecraftForge.setBlockHarvestLevel(BLOCKS.magicTable, "pickaxe", 1);
    }
    
    // Register the shaped recipes.
    public static void registerRecipes()
    {
       /* GameRegistry.addRecipe(new ItemStack(BLOCKS.awakeningTable),
        		new Object[] {"abc", "ded", "fff",
            'a', ITEMS.emeraldTablet, 
            'b', ITEMS.awakeningTome,
            'c', Item.paper,
            'd', Block.stone,
            'e', BLOCKS.magicTable,
            'f', Block.bookShelf});
        GameRegistry.addRecipe(new ItemStack(BLOCKS.awakeningTable),
                new Object[] {"abc", "ded", "fff",
            'a', Item.paper, 
            'b', ITEMS.awakeningTome,
            'c', ITEMS.emeraldTablet,
            'd', Block.stone,
            'e', BLOCKS.magicTable,
            'f', Block.bookShelf}); */
        GameRegistry.addRecipe(new ItemStack(BLOCKS.magicTable),
        		new Object[] {"aba", "ccc", "ccc",
        	'a', Items.gold_ingot,
        	'b', Items.emerald,
        	'c', Blocks.stone }); 
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
