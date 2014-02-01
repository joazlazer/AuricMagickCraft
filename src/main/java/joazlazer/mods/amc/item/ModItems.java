package joazlazer.mods.amc.item;

public class ModItems
{
    // The variables for the items.
    public static class ITEMS
    {
        //public static Item emeraldTablet;
        //public static Item awakeningTome;
    }
    
    // Initialization of the items.
    public static void initItems()
    {
        //ITEMS.emeraldTablet = new ItemEmeraldTablet();
        //ITEMS.awakeningTome = new ItemAwakeningTome();
    }
    
    
    // Register the items within the language registry.
    public static void registerNames()
    {
        /* LanguageRegistry.instance().addNameForObject(ITEMS.emeraldTablet, 
                "en_US", "Emerald Tablet");
        LanguageRegistry.instance().addNameForObject(ITEMS.awakeningTome,
        		"en_US", "Awakening Tome"); */
    }
    
    // Register the shaped recipes.
    public static void registerRecipes()
    {
        /*GameRegistry.addRecipe(new ItemStack(ITEMS.emeraldTablet, 1), new Object[] 
             {"   ", "xxx", "xxx",
             'x', Item.emerald}); */
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
