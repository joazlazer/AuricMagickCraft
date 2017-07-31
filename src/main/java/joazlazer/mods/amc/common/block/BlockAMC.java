package joazlazer.mods.amc.common.block;

import joazlazer.mods.amc.common.creativetab.CreativeTabsAMC;
import joazlazer.mods.amc.common.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockAMC extends Block {

    public BlockAMC(Material blockMaterialIn, final String blockName) {
        super(blockMaterialIn);
        setBlockName(this, blockName);
        setCreativeTab(CreativeTabsAMC.AMC_TAB);
    }

    public static void setBlockName(final Block block, final String blockName) {
        block.setRegistryName(Reference.MOD_ID, blockName);
        block.setUnlocalizedName(block.getRegistryName().toString());
    }
}
