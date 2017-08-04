package joazlazer.mods.amc.api.capability.progression;

import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.api.order.OrderBase;
import joazlazer.mods.amc.common.utility.NBTUtil;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityAmcProgressionHandler {
    @CapabilityInject(IAmcProgressionHandler.class)
    public static Capability<IAmcProgressionHandler> AMC_PROGRESSION_HANDLER = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IAmcProgressionHandler.class, new Capability.IStorage<IAmcProgressionHandler>() {
            @Override
            public NBTBase writeNBT(Capability<IAmcProgressionHandler> capability, IAmcProgressionHandler instance, EnumFacing side) {
                return serialize(instance);
            }

            @Override
            public void readNBT(Capability<IAmcProgressionHandler> capability, IAmcProgressionHandler instance, EnumFacing side, NBTBase nbt) {
                deserialize(instance, nbt);
            }
        }, AmcProgressionHandler::new);
    }

    public static NBTBase serialize(IAmcProgressionHandler instance) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setBoolean("isAwakened", instance.getIsAwakened());
        if(instance.getOrder() != null)  NBTUtil.writeResourceLocation(nbt, "order", instance.getOrder().getRegistryName());
        return nbt;
    }

    public static void deserialize(IAmcProgressionHandler instance, NBTBase nbt) {
        if(nbt instanceof NBTTagCompound) {
            NBTTagCompound nbtTag = (NBTTagCompound) nbt;
            instance.setAwakened(nbtTag.getBoolean("isAwakened"));
            if(nbtTag.hasKey("order")) {
                ResourceLocation rl = NBTUtil.getResourceLocation(nbtTag, "order");
                if(OrderBase.registry.containsKey(rl)) {
                    instance.setOrder(OrderBase.registry.getValue(rl));
                } else AuricMagickCraft.logger.error(String.format("[CapabilityAmcProgressionHandler.deserialize(...)] NBT specified Order '%s' that has not been registered; data not loaded", rl.toString()));
            }
        } else AuricMagickCraft.logger.error("[CapabilityAmcProgressionHandler.deserialize(...)] NBT Capability data is not an instance of NBTTagCompound; data not loaded");
    }
}
