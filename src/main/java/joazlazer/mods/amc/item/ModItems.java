package joazlazer.mods.amc.item;

import cpw.mods.fml.common.registry.GameRegistry;
import joazlazer.mods.amc.reference.Names;
import joazlazer.mods.amc.reference.Recipes;
import joazlazer.mods.amc.util.RecipeHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.Sys;

public class ModItems {
    public static class ITEMS {
        public static ItemAMC awakeningCrystal;
    }

    public static void initItems() {
        ITEMS.awakeningCrystal = new ItemAwakeningCrystal();
    }

    public static void registerItems() {
        GameRegistry.registerItem(ITEMS.awakeningCrystal, Names.Items.AWAKENING_CRYSTAL);
    }

    public static void fullRegister() {
        initItems();
        registerItems();
        registerShapedRecipes();
    }

    public static void registerShapedRecipes() {
        GameRegistry.addShapedRecipe(new ItemStack(ITEMS.awakeningCrystal, 1), Recipes.AWAKENING_CRYSTAL);
        GameRegistry.addShapedRecipe(new ItemStack(ITEMS.awakeningCrystal, 1), RecipeHelper.flipCircular(Recipes.AWAKENING_CRYSTAL));
    }
}
