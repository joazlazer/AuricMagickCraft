package joazlazer.mods.amc.common.handlers;

import joazlazer.mods.amc.client.gui.GuiAwakeningScreen;
import joazlazer.mods.amc.client.gui.GuiAwakeningTable;
import joazlazer.mods.amc.common.container.ContainerAwakeningTable;
import joazlazer.mods.amc.common.tileentity.TileEntityAwakeningTable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiProxy implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityAwakeningTable) {
            return new ContainerAwakeningTable(player.inventory, (TileEntityAwakeningTable) te);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityAwakeningTable) {
            TileEntityAwakeningTable containerTileEntity = (TileEntityAwakeningTable) te;
            return new GuiAwakeningTable(containerTileEntity, new ContainerAwakeningTable(player.inventory, containerTileEntity));
        }
        return null;
    }
}
