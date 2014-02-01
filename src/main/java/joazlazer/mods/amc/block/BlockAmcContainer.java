package joazlazer.mods.amc.block;

import joazlazer.mods.amc.tileentity.TileEntityAMC;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAmcContainer extends BlockContainer 
{
	protected BlockAmcContainer(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity func_149915_a(World var1, int var2) {
		return new TileEntityAMC(var1);
	}
}
