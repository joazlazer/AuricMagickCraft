package joazlazer.mods.amc.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import joazlazer.mods.amc.creativetab.CreativeTabAMC;
import joazlazer.mods.amc.reference.Textures;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockAMC extends Block {
    public BlockAMC(Material material) {
        super(material);
        this.setCreativeTab(CreativeTabAMC.AMC_TAB);
    }

    public BlockAMC() {
        super(Material.rock);
        this.setCreativeTab(CreativeTabAMC.AMC_TAB);
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("tile.%s%s", Textures.RESOURCE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconReg) {
        blockIcon = par1IconReg.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf('.') + 1);
    }
}
