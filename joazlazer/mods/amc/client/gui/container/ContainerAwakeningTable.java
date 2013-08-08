package joazlazer.mods.amc.client.gui.container;

import joazlazer.mods.amc.tileentity.TileEntityAwakeningTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerAwakeningTable extends Container{

	private TileEntityAwakeningTable table;
	
	public ContainerAwakeningTable(InventoryPlayer playerInv, TileEntityAwakeningTable table)
	{
		// Set the tile entity to the current tile entity.
		this.table = table;
		
		// Add the player's current inventory.
		for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 36 + j * 18 + 12, i * 18 + 146));
            }
        }
		
		// Add the player's hotbar.
        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(playerInv, i, 36 + i * 18 + 12, 204));
        }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return table.isUseableByPlayer(entityplayer);
	}
	
	
	
}
