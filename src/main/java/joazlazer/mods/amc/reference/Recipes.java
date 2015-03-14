package joazlazer.mods.amc.reference;

import joazlazer.mods.amc.block.ModBlocks;
import joazlazer.mods.amc.item.ModItems;
import joazlazer.mods.amc.util.RecipeHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class Recipes {
    public static final ShapedOreRecipe AWAKENING_CRYSTAL = new ShapedOreRecipe(new ItemStack(ModItems.ITEMS.awakeningCrystal), new Object[] { "aba", "bcb", "aba", 'a', "dyeLime", 'b', "gemQuartz", 'c', "gemEmerald" } );
    public static final ShapedOreRecipe AWAKENING_CRYSTAL_FLIPPED = new ShapedOreRecipe(new ItemStack(ModItems.ITEMS.awakeningCrystal), RecipeHelper.flipCircular(new Object[] { "aba", "bcb", "aba", 'a', "gemQuartz", 'b', "dyeLime", 'c', "gemEmerald" }));
    public static final ShapedOreRecipe AWAKENING_TABLE = new ShapedOreRecipe(new ItemStack(ModBlocks.BLOCKS.awakeningTable ), new Object[] { "aba", "cdc", "cec" , 'a', "ingotGold", 'b', new ItemStack(Items.book, 1), 'c', new ItemStack(Blocks.planks, 1, OreDictionary.WILDCARD_VALUE), 'd', new ItemStack(ModItems.ITEMS.awakeningCrystal), 'e', new ItemStack(Blocks.bookshelf, 1) });
}
