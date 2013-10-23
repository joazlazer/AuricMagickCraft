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
	public void saveToNBT(NBTTagCompound entityData) {
		System.out.println("Saving!");
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
