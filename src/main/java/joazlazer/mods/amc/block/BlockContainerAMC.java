package joazlazer.mods.amc.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import joazlazer.mods.amc.creativetab.CreativeTabAMC;
import joazlazer.mods.amc.reference.Reference;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockContainerAMC extends BlockContainer {

    public BlockContainerAMC(Material material) {
        super(material);
        this.setCreativeTab(CreativeTabAMC.AMC_TAB);
    }

    public BlockContainerAMC() {
        super(Material.rock);
        this.setCreativeTab(CreativeTabAMC.AMC_TAB);
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("tile.%s:%s", Reference.MOD_ID.toLowerCase(), getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconReg) {
        blockIcon = par1IconReg.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf('.') + 1);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return null;
    }
}
