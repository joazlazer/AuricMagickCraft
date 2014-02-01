package joazlazer.mods.amc.client.gui.container;

import joazlazer.mods.amc.tileentity.TileEntityAwakeningTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerAwakeningTable extends Container{

	private TileEntityAwakeningTable table;
	
	public ContainerAwakeningTable(InventoryPlayer playerInv, TileEntityAwakeningTable table)
	{
		// Set the tile entity to the current tile entity.
		this.table = table;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return table.isUseableByPlayer(entityplayer);
	}
}
