package joazlazer.mods.amc.block;

import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.lib.Strings;
import joazlazer.mods.amc.lib.Textures;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMagicTable extends Block {

	@SideOnly(Side.CLIENT)
	public IIcon sideIcon;
	
	public BlockMagicTable() {
		super(Material.field_151576_e);
		func_149663_c(Strings.MAGIC_TABLE_NAME);
		this.func_149647_a(AuricMagickCraft.tabsAMC);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
    public void func_149651_a(IIconRegister iconReg)
    {
        this.field_149761_L = iconReg.registerIcon(Textures.BLOCKS.MAGIC_TABLE_TOP);
        sideIcon = iconReg.registerIcon(Textures.BLOCKS.MAGIC_TABLE);
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon func_149691_a(int side, int metadata) {
		if (side == 1) return field_149761_L;
		else return sideIcon;
	}
}
