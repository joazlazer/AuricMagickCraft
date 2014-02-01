package joazlazer.mods.amc.block;

import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.lib.GuiIds;
import joazlazer.mods.amc.lib.Strings;
import joazlazer.mods.amc.tileentity.TileEntityAwakeningTable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAwakeningTable extends BlockAmcContainer {

	@SideOnly(Side.CLIENT)
	public IIcon[] tops;
	
	@SideOnly(Side.CLIENT)
	public IIcon bottomTexture;
	
	public BlockAwakeningTable() {
		super(Material.field_151576_e);
		func_149663_c(Strings.AWAKENING_TABLE_NAME);
		this.func_149647_a(AuricMagickCraft.tabsAMC);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
    public void func_149651_a(IIconRegister iconReg)
    {
		tops = new IIcon[4];
		tops[0] = iconReg.registerIcon("AMC:awakeningTable_TOP_NORTH");
	    tops[1] = iconReg.registerIcon("AMC:awakeningTable_TOP_EAST");
	    tops[2] = iconReg.registerIcon("AMC:awakeningTable_TOP_SOUTH");
	    tops[3] = iconReg.registerIcon("AMC:awakeningTable_TOP_WEST");
	    bottomTexture = iconReg.registerIcon("AMC:awakeningTable_BOTTOM");
	    field_149761_L = iconReg.registerIcon("AMC:awakeningTable_SIDE");
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon func_149691_a(int side, int metadata) {
		if (side == 0) return bottomTexture;
		else if (side == 1) {
			if (metadata == ForgeDirection.NORTH.ordinal()) return tops[0];
	        else if (metadata == ForgeDirection.EAST.ordinal()) return tops[1];
	        else if (metadata == ForgeDirection.SOUTH.ordinal()) return tops[2];
	        else if (metadata == ForgeDirection.WEST.ordinal()) return tops[3];
	        else return tops[0];
		}
		else return field_149761_L;
	}
	
	@Override
	public void func_149689_a(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {
		int direction = 0;
        int facing = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        if (facing == 0) {
            direction = ForgeDirection.SOUTH.ordinal();
        }
        else if (facing == 1) {
            direction = ForgeDirection.WEST.ordinal();
        }
        else if (facing == 2) {
            direction = ForgeDirection.NORTH.ordinal();
        }
        else if (facing == 3) {
            direction = ForgeDirection.EAST.ordinal();
        }

        world.setBlockMetadataWithNotify(x, y, z, direction, 3);
	}
	
	@Override
	public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int par1, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
    		if (player.isSneaking()) return false;
    		FMLNetworkHandler.openGui(player, AuricMagickCraft.instance, GuiIds.AWAKENING_TABLE, world, x, y, z);

		}
        return true;
	}
	
	@Override
	public TileEntity func_149915_a(World var1, int var2) {
		return new TileEntityAwakeningTable(var1);
	}
}
