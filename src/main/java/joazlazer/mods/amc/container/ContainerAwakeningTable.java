package joazlazer.mods.amc.container;

import joazlazer.mods.amc.tileentity.TileEntityAwakeningTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ContainerAwakeningTable extends ContainerAMC {

    public ContainerAwakeningTable (InventoryPlayer inventoryPlayer, TileEntityAwakeningTable te){
        // Empty because we don't have any slots.
    }

    @Override
    public boolean canInteractWith(EntityPlayer p_75145_1_) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        // Filler because we don't have any slots.
        return new ItemStack(Items.record_cat, 7);
    }
}
