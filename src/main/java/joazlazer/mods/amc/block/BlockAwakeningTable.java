package joazlazer.mods.amc.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.reference.GuiId;
import joazlazer.mods.amc.reference.Names;
import joazlazer.mods.amc.tileentity.TileEntityAwakeningTable;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockAwakeningTable extends BlockContainerAMC {
    public BlockAwakeningTable() {
        super(ModBlocks.MATERIALS.table);
        this.setBlockName(Names.Blocks.AWAKENING_TABLE);
        this.setStepSound(Block.soundTypeWood);
        this.setHarvestLevel("axe", 0);
        this.setHardness(1);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
        return new TileEntityAwakeningTable();
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isSideSolid(IBlockAccess block, int x, int y, int z, ForgeDirection direction) {
        if(direction == ForgeDirection.DOWN || direction == ForgeDirection.UP) return true;
        else return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return -1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer sender, int meta, float f0, float f1, float f2) {
        TileEntity te = world.getTileEntity(x, y, z);
        if(te != null && !sender.isSneaking() && te instanceof TileEntityAwakeningTable && !world.isRemote) {
            sender.openGui(AuricMagickCraft.instance, GuiId.AWAKENING_TABLE.ordinal(), world, x, y, z);
            return true;
        }
        else return false;
    }
}
