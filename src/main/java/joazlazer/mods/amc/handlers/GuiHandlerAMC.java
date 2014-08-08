package joazlazer.mods.amc.handlers;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import joazlazer.mods.amc.client.gui.GuiAwakeningTable;
import joazlazer.mods.amc.client.gui.GuiTechTree;
import joazlazer.mods.amc.container.ContainerAwakeningTable;
import joazlazer.mods.amc.reference.GuiId;
import joazlazer.mods.amc.reference.Reference;
import joazlazer.mods.amc.tileentity.TileEntityAwakeningTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class GuiHandlerAMC implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te != null) {
            if (ID == GuiId.AWAKENING_TABLE.ordinal()) {
                if (te instanceof TileEntityAwakeningTable)
                    return new ContainerAwakeningTable(player.inventory, (TileEntityAwakeningTable) te);
            } else return null;
        } else {
            if (ID == GuiId.TECH_TREE.ordinal()) {
                return new GuiTechTree();
            } else return null;
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te != null) {
            if (ID == GuiId.AWAKENING_TABLE.ordinal()) {
                if (te instanceof TileEntityAwakeningTable)
                    return new GuiAwakeningTable(player.inventory, (TileEntityAwakeningTable) te);
            } else return null;

        } else {
            if (ID == GuiId.TECH_TREE.ordinal()) {
                return new GuiTechTree();
            } else return null;
        }
        return null;
    }

}