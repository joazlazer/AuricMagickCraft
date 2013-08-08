package joazlazer.mods.amc.block;

import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.lib.BlockIds;
import joazlazer.mods.amc.lib.GuiIds;
import joazlazer.mods.amc.lib.Reference;
import joazlazer.mods.amc.lib.Strings;
import joazlazer.mods.amc.lib.Textures;
import joazlazer.mods.amc.tileentity.TileEntityAwakeningTable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAwakeningTable extends BlockAmcContainer 
{
    // The icons for the bottom and side.
    private Icon bottom;
    private Icon side;
    
    private Icon[] tops;
    
	public BlockAwakeningTable() 
	{
		super(BlockIds.AWAKENING_TABLE, Material.rock);
		this.setCreativeTab(AuricMagickCraft.tabsAMC);
		this.setUnlocalizedName(Strings.AWAKENING_TABLE_NAME);
		// Set the texture location for the block.
		this.func_111022_d(Textures.BLOCKS.AWAKENING_TABLE_TOP);
		this.setHardness(2.0f);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconReg)
	{
	    tops = new Icon[4];
	    tops[0] = par1IconReg.registerIcon("AMC:awakeningTable_TOP_NORTH");
	    tops[1] = par1IconReg.registerIcon("AMC:awakeningTable_TOP_EAST");
	    tops[2] = par1IconReg.registerIcon("AMC:awakeningTable_TOP_SOUTH");
	    tops[3] = par1IconReg.registerIcon("AMC:awakeningTable_TOP_WEST");
	    bottom = par1IconReg.registerIcon(Reference.MOD_ID + ":" + BlockAmcContainer.getUnlocalizedNameAMC
                (this.getUnlocalizedName()) + "_BOTTOM");
	    side = par1IconReg.registerIcon(Reference.MOD_ID + ":" + BlockAmcContainer.getUnlocalizedNameAMC
                (this.getUnlocalizedName()) + "_SIDE");
	    this.blockIcon = tops[0];
	}
	
	@Override
	public Icon getIcon(int side, int metadata)
	{
	    // Give the correct icon based on the side and the direction
	    // stored in the metadata.
	    if (side == 1)
	    {
	        if (metadata == ForgeDirection.NORTH.ordinal()) return tops[0];
	        else if (metadata == ForgeDirection.EAST.ordinal()) return tops[1];
	        else if (metadata == ForgeDirection.SOUTH.ordinal()) return tops[2];
	        else if (metadata == ForgeDirection.WEST.ordinal()) return tops[3];
	        else return this.blockIcon;
	    }
	    else if (side == 0) return bottom;
	    else return this.side;
	}
	
	/**
     * Called when the block is placed in the world.
     */
	@Override
    public void onBlockPlacedBy(World par1World, int x, int y, int z, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) 
	{
	    super.onBlockPlacedBy(par1World, x, y, z, par5EntityLivingBase, par6ItemStack);
	}
	
	@Override
    public TileEntity createNewTileEntity(World world) 
	{
        return new TileEntityAwakeningTable();
    }
	
	/**
     * Called upon block activation (right click on the block.)
     */
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
    	if (!world.isRemote) {
    		if (player.isSneaking()) return false;
    		
    		FMLNetworkHandler.openGui(player, AuricMagickCraft.instance, GuiIds.AWAKENING_TABLE, world, x, y, z);
    	}
    	
        return true;
    }
}
