package joazlazer.mods.amc.tileentity;

import org.apache.logging.log4j.Level;

import joazlazer.mods.amc.AMCLogger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityAwakeningTable extends TileEntityAMC implements IInventory{
	
	
	public TileEntityAwakeningTable(World var1) {
		super(var1);
	}
	
	@Override
    public void func_145841_b(NBTTagCompound tag)
    {
        super.func_145841_b(tag);
    }
    
    @Override
    public void func_145839_a(NBTTagCompound tag)
    {
        super.func_145839_a(tag);
    }
   
	@Override
	public int getSizeInventory() {
		return 0;
	}
	
	@Override
	public ItemStack getStackInSlot(int i) {
		return null;
	}
	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		return null;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}
	
	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
	}
	
	@Override
	public String func_145825_b() {
		return "awakeningTableInv";
	}
	
	@Override
	public boolean func_145818_k_() {
		return false;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return entityplayer.getDistanceSq(field_145851_c + 0.5, field_145848_d + 0.5, field_145849_e + 0.5) <= 64;
	}
	
	@Override
	public void openChest() {
		
	}
	
	@Override
	public void closeChest() {
		
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}
	
}
