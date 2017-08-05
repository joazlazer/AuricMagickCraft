package joazlazer.mods.amc.api.capability;

import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.api.capability.progression.AmcProgressionHandler;
import joazlazer.mods.amc.api.capability.progression.CapabilityAmcProgressionHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerCapabilityProvider implements ICapabilityProvider, INBTSerializable<NBTTagCompound> {
    private AmcProgressionHandler amcProgressionHandler;

    public PlayerCapabilityProvider() {
        amcProgressionHandler = new AmcProgressionHandler();
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityAmcProgressionHandler.AMC_PROGRESSION_HANDLER;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityAmcProgressionHandler.AMC_PROGRESSION_HANDLER) {
            return (T) amcProgressionHandler;
        } else return null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("amcProgressionHandler", amcProgressionHandler.serializeNBT());
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        NBTBase amcProgressionHandlerNBT = nbt.getTag("amcProgressionHandler");
        amcProgressionHandler.deserializeNBT(amcProgressionHandlerNBT);
    }
}
