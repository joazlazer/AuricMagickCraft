package joazlazer.mods.amc.tileentity;

import joazlazer.mods.amc.lib.Strings;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityAMC extends TileEntity
{
    protected ForgeDirection orientation;

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        if (nbtTagCompound.hasKey(Strings.NBT_TE_DIRECTION_KEY)) {
            orientation = ForgeDirection.getOrientation(nbtTagCompound.getByte(Strings.NBT_TE_DIRECTION_KEY));
        }
    }

    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setByte(Strings.NBT_TE_DIRECTION_KEY, (byte) orientation.ordinal());
    }
    
    public ForgeDirection getOrientation() 
    {
        return orientation;
    }

    public void setOrientation(ForgeDirection orientation) 
    {
        this.orientation = orientation;
    }

    public void setOrientation(int orientation) 
    {
        this.orientation = ForgeDirection.getOrientation(orientation);
    }
}