package joazlazer.mods.amc.item;

import joazlazer.mods.amc.lib.Strings;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModItems
{
    // The variables for the items.
    public static class ITEMS
    {
        public static Item emeraldTablet;
    }
    
    // Initialization of the items.
    public static void initItems()
    {
        ITEMS.emeraldTablet = new ItemEmeraldTablet();
    }
    
    
    // Register the items within the language registry.
    public static void registerNames()
    {
        LanguageRegistry.instance().addNameForObject(ITEMS.emeraldTablet, 
                "en_US", "Emerald Tablet");
    }
    
    // Register the shaped recipes.
    public static void registerRecipes()
    {
        GameRegistry.addRecipe(new ItemStack(ITEMS.emeraldTablet, 1), new Object[] 
             {"   ", "xxx", "xxx",
             'x', Item.emerald});
    }
    
    // Register the shapeless recipes.
    public static void registerShapelessRecipes()
    {
        
    }
    
    // Register any mod/furnace/brewing/ore recipes.
    public static void registerOtherRecipes()
    {
        
    }
    
    // Method for the main mod class to call.
    public static void fullRegister()
    {
        // Do everything required.
        initItems();
        registerNames();
    }
    
    // Full Recipe registry
    public static void registerAllRecipes()
    {
        // Register all of the recipes.
        registerRecipes();
        registerShapelessRecipes();
        registerOtherRecipes();
    }
}
