package joazlazer.mods.amc.api.capability.storage;

import joazlazer.mods.amc.api.capability.IAmcProgressionHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import javax.annotation.Nullable;

public class AmcProgressionStorage implements Capability.IStorage<IAmcProgressionHandler> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IAmcProgressionHandler> capability, IAmcProgressionHandler instance, EnumFacing side) {
        return null;
    }

    @Override
    public void readNBT(Capability<IAmcProgressionHandler> capability, IAmcProgressionHandler instance, EnumFacing side, NBTBase nbt) {

    }
}
