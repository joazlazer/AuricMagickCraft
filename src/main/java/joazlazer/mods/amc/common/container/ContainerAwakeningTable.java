package joazlazer.mods.amc.common.container;

import joazlazer.mods.amc.common.tileentity.TileEntityAwakeningTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public class ContainerAwakeningTable extends Container {
    TileEntityAwakeningTable te;
    public ContainerAwakeningTable(IInventory playerInventory, TileEntityAwakeningTable te) {
        this.te = te;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return te.canInteractWith(playerIn);
    }
}
