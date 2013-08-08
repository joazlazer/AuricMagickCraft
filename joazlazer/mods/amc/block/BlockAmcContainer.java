package joazlazer.mods.amc.block;

import joazlazer.mods.amc.tileentity.TileEntityAMC;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockAmcContainer extends BlockContainer 
{
	protected BlockAmcContainer(int par1, Material par2Material) 
	{
		super(par1, par2Material);
	}

	@Override
	public TileEntity createNewTileEntity(World world) 
	{
		return null;
	}

	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) 
    {
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
        
        ((TileEntityAMC) world.getBlockTileEntity(x, y, z)).setOrientation(direction);
    }
	
	public static String getUnlocalizedNameAMC(String unlocName)
	{
	    return unlocName.substring(5);
	}
}
