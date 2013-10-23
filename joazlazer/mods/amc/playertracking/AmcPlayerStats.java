package joazlazer.mods.amc.playertracking;

import java.lang.ref.WeakReference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class AmcPlayerStats {
	public WeakReference<EntityPlayer> player;
	public int auraLevel;
	public int aura;
	public int auraColor;
	public String orderUnlocName;
	public boolean isAwake;
	public boolean showAuraRosary;
	public float auraIncrement;
	public float auraIncBuffer;
	public void saveToNBT() {
		
		// Get the player's data.
		NBTTagCompound tags = player.get().getEntityData();
		
		// Save all of the variables to NBT.
        NBTTagCompound saves = new NBTTagCompound();
        saves.setBoolean("isAwake", isAwake);
        saves.setInteger("auraCount",  aura);
        saves.setInteger("auraLevel", auraLevel);
        saves.setInteger("auraIncBuffer", (int) (auraIncBuffer * 1000.0F));
        saves.setInteger("auraColor", auraColor);
        saves.setString("orderUnlocName", orderUnlocName);
        saves.setBoolean("showAuraRosary", showAuraRosary);
        
        // Set the amc player tracker tag in the NBT 
        // of the player to the newly created one.
        tags.setCompoundTag("AMC", saves);
	}
	public static int getMaxAura(int level, int auraColor) {
		int coeff = 4;
		switch (auraColor) {
			case 0x000000:
			case 0xE8D500: {
				coeff = 1;
				break;
			}
			case 0xF9F7F2: {
				coeff = 2;
			}
		}
		return 100 * (level ^ 2) / coeff;
	}
}
