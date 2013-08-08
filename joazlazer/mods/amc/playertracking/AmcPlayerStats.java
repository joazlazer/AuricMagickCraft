package joazlazer.mods.amc.playertracking;

import java.lang.ref.WeakReference;
import net.minecraft.entity.player.EntityPlayer;

public class AmcPlayerStats {
	public WeakReference<EntityPlayer> player;
	public int auraLevel;
	public int aura;
	public int auraColor;
	public String orderUnlocName;
	public boolean isAwake;
	public boolean showAuraRosary;
}
