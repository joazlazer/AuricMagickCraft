package joazlazer.mods.amc.casting.client;

import joazlazer.mods.amc.api.spell.SpellBase;
import joazlazer.mods.amc.api.spell.SpellRegistry;
import net.minecraft.nbt.NBTTagCompound;

public class ClientCastingStatus {
    public String username;
    public boolean receiveTick;
    public SpellBase selectedSpell;
    public NBTTagCompound customNBT;

    public ClientCastingStatus(String username) {
        this.username = username;
        receiveTick = false;
        selectedSpell = null;
        customNBT = new NBTTagCompound();
    }

    public void readFromNBT(NBTTagCompound nbt) {
        receiveTick = nbt.getBoolean("receiveTick");
        selectedSpell = SpellRegistry.getSpell(nbt.getString("selectedSpell"));
        customNBT = nbt.getCompoundTag("customNBT");
    }
}
