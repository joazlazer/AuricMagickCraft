package joazlazer.mods.amc.block;

import cpw.mods.fml.common.registry.GameRegistry;
import joazlazer.mods.amc.reference.Names;
import joazlazer.mods.amc.reference.Recipes;
import joazlazer.mods.amc.reference.Reference;
import joazlazer.mods.amc.tileentity.TileEntityAwakeningTable;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {
    public static class BLOCKS {
        public static Block awakeningTable;
    }

    public static class MATERIALS {
        public static class TableMaterial extends Material {
            public TableMaterial() {
                super(MapColor.brownColor);
            }
        }
        public static TableMaterial table  = new TableMaterial();
    }

    public static void fullRegister() {
        // Initialize the blocks.
        initBlocks();

        // Register the blocks in the GameRegistry.
        registerBlocks();

        // Register the tile entities associated with the blocks.
        registerTileEntities();

        // Register the shaped recipes.
        registerShapedRecipes();
    }

    public static void initBlocks() {
        BLOCKS.awakeningTable = new BlockAwakeningTable();
    }

    public static void registerBlocks() {
        GameRegistry.registerBlock(BLOCKS.awakeningTable, Names.Blocks.AWAKENING_TABLE);
    }

    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityAwakeningTable.class, Names.Blocks.AWAKENING_TABLE);
    }

    public static void registerShapedRecipes() {
        //GameRegistry.addRecipe(new ShapedOreRecipe(BLOCKS.awakeningTable, 1), Recipes.AWAKENING_TABLE);
    }
}
