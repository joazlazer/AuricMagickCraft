package joazlazer.mods.amc.client.gui;

import joazlazer.mods.amc.AMCLogger;
import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.client.gui.container.ContainerAwakeningTable;
import joazlazer.mods.amc.client.gui.inventory.GuiAwakeningTable;
import joazlazer.mods.amc.lib.GuiIds;
import joazlazer.mods.amc.tileentity.TileEntityAwakeningTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler {

	public GuiHandler()	{
		NetworkRegistry.INSTANCE.registerGuiHandler(AuricMagickCraft.instance, this);
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == GuiIds.AWAKENING_TABLE)
		{
			TileEntity te = world.func_147438_o(x, y, z);
			if (te != null && te instanceof TileEntityAwakeningTable) {
				return new ContainerAwakeningTable(player.inventory, (TileEntityAwakeningTable)te);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == GuiIds.AWAKENING_TABLE)
		{
			TileEntity te = world.func_147438_o(x, y, z);
			if (te != null && te instanceof TileEntityAwakeningTable) {
				return new GuiAwakeningTable(player.inventory, (TileEntityAwakeningTable)te);
			}
		}
		return null;
		
	}
}
