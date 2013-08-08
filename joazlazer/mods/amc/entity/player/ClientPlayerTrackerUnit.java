package joazlazer.mods.amc.entity.player;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;

public class ClientPlayerTrackerUnit {
	// Constants
	public static final int MAX_LEVEL = 99;
	
	// Variables
	public EntityClientPlayerMP playerInstance;
	public boolean isAwake = false;
	public int auricLevel = 10;
	public int auricColor = 0xE8D500;
	public String orderUnlocName;
	public int aura = 2000;
	public boolean showAuraRosary = true;
	public ClientPlayerTrackerUnit(EntityClientPlayerMP living) {
		playerInstance = living;
	}
	public static int getMaxAura(int lvl) {
		return (int) (Math.pow(lvl, 2) * 100);
	}
	public int getMaxAura() {
		return getMaxAura(this.auricLevel);
	}
	public void savePlayer() {
		
	}
	public void setPlayer(EntityClientPlayerMP player) {
		this.playerInstance = player;
	}
}
