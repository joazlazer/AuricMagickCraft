package joazlazer.mods.amc.casting;

import joazlazer.mods.amc.api.spell.ModSpells;
import joazlazer.mods.amc.api.spell.SpellBase;
import net.minecraft.nbt.NBTTagCompound;

public class CastingStatus {
    public boolean receiveTick;
    public SpellBase selectedSpell;
    public CastingStatus() {
        changeSpell(ModSpells.SPELLS.blink);
    }
    public NBTTagCompound customNBT = new NBTTagCompound();
    public void changeSpell(SpellBase spellBase) {
        selectedSpell = spellBase;
        customNBT = new NBTTagCompound();
    }

    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setBoolean("receiveTick", receiveTick);
        nbt.setString("selectedSpell", selectedSpell.getUnlocName());
        nbt.setTag("customNBT", customNBT);
    }
}
