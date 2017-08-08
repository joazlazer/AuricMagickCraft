package joazlazer.mods.amc.api.capability;

import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.api.capability.aura.CapabilityAuraHandler;
import joazlazer.mods.amc.api.capability.aura.EntityAuraHandler;
import joazlazer.mods.amc.api.capability.progression.EntityAmcProgressionHandler;
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
    private EntityAmcProgressionHandler entityAmcProgressionHandler;
    private EntityAuraHandler entityAuraHandler;

    public PlayerCapabilityProvider() {
        entityAmcProgressionHandler = new EntityAmcProgressionHandler();
        entityAuraHandler = new EntityAuraHandler();
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityAmcProgressionHandler.AMC_PROGRESSION_HANDLER) {
            return true;
        } else return capability == CapabilityAuraHandler.AURA_HANDLER;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityAmcProgressionHandler.AMC_PROGRESSION_HANDLER) {
            return (T) entityAmcProgressionHandler;
        } else if(capability == CapabilityAuraHandler.AURA_HANDLER) {
            return (T) entityAuraHandler;
        } else return null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("amcProgressionHandler", entityAmcProgressionHandler.serializeNBT());
        nbt.setTag("auraHandler", entityAuraHandler.serializeNBT());
        AuricMagickCraft.logger.info(String.format("[PlayerCapabilityProvider.serializeNBT(...)] Serialized player capability bundle: {%s,%s}", entityAmcProgressionHandler.toString(), entityAuraHandler.toString()));
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        NBTBase amcProgressionHandlerNBT = nbt.getTag("amcProgressionHandler");
        NBTBase auraHandlerNBT = nbt.getTag("auraHandler");
        entityAmcProgressionHandler.deserializeNBT(amcProgressionHandlerNBT);
        entityAuraHandler.deserializeNBT(auraHandlerNBT);
        AuricMagickCraft.logger.info(String.format("[PlayerCapabilityProvider.deserializeNBT(...)] Deserialized player capability bundle: {%s,%s}", entityAmcProgressionHandler.toString(), entityAuraHandler.toString()));
    }
}
