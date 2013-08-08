package joazlazer.mods.amc.entity.player;

import net.minecraft.entity.player.EntityPlayerMP;

public class ServerPlayerTrackerUnit {
	// Constants
	public static final int MAX_LEVEL = 99;
	
	// Variables
	public EntityPlayerMP playerInstance;
	public boolean isAwake;
	public int auricLevel;
	public int auricColor;
	public String orderUnlocName;
	public int aura;
	public ServerPlayerTrackerUnit(EntityPlayerMP living) {
		playerInstance = living;
	}
	public ServerPlayerTrackerUnit() {
		
	}
	public static int getMaxAura(int lvl) {
		return (int) (Math.pow(lvl, 2) * 100);
	}
	public int getMaxAura() {
		return getMaxAura(this.auricLevel);
	}
	public void savePlayer() {
		
	}
	public ServerPlayerTrackerUnit setPlayer(EntityPlayerMP player) {
		this.playerInstance = player;
		return this;
	}
}