package joazlazer.mods.amc.api.capability.aura;

import com.github.lzyzsd.randomcolor.RandomColor;
import joazlazer.mods.amc.AuricMagickCraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import java.awt.Color;

public class CapabilityAuraHandler {
    @CapabilityInject(IAuraHandler.class)
    public static Capability<IAuraHandler> AURA_HANDLER = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IAuraHandler.class, new Capability.IStorage<IAuraHandler>() {
            @Override
            public NBTBase writeNBT(Capability<IAuraHandler> capability, IAuraHandler instance, EnumFacing side) {
                return serialize(instance);
            }

            @Override
            public void readNBT(Capability<IAuraHandler> capability, IAuraHandler instance, EnumFacing side, NBTBase nbt) {
                deserialize(instance, nbt);
            }
        }, EntityAuraHandler::new);
    }

    public static NBTBase serialize(IAuraHandler instance) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setFloat("au", instance.getAuraUnits());
        nbt.setFloat("maxAu", instance.getMaxAuraUnits());
        nbt.setInteger("color", instance.getColor().getRGB());
        return nbt;
    }

    public static void deserialize(IAuraHandler instance, NBTBase nbt) {
        if(nbt instanceof NBTTagCompound) {
            NBTTagCompound nbtTag = (NBTTagCompound)nbt;
            instance.setAuraUnits(nbtTag.getFloat("au"));
            instance.setMaxAuraUnits(nbtTag.getFloat("maxAu"));
            int colorInt = nbtTag.getInteger("color");
            instance.setColor(colorInt != -1 ? new Color(colorInt, true) : new Color((new RandomColor()).randomColor()));
        } else AuricMagickCraft.logger.error("[CapabilityAuraHandler.deserialize(...)] NBT Capability data is not an instance of NBTTagCompound; data not loaded");
    }
}
