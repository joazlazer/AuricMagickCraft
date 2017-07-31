package joazlazer.mods.amc.api.capability.storage;

import joazlazer.mods.amc.api.capability.ISpellCaster;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import javax.annotation.Nullable;

public class SpellCasterStorage implements Capability.IStorage<ISpellCaster> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<ISpellCaster> capability, ISpellCaster instance, EnumFacing side) {
        return null;
    }

    @Override
    public void readNBT(Capability<ISpellCaster> capability, ISpellCaster instance, EnumFacing side, NBTBase nbt) {

    }
}
