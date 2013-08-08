package joazlazer.mods.amc.entity.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;

public class ClientPlayerTracker {
	private ClientPlayerTrackerUnit playerUnit;

	static {
		instance = new ClientPlayerTracker();
	}

	public static final ClientPlayerTracker instance;

	public ClientPlayerTracker() {
		
	}

	/**
	 * @return the playerUnit
	 */
	public ClientPlayerTrackerUnit getPlayer() {
		return playerUnit;
	}

	/**
	 * @param playerUnit
	 *            the playerUnit to set
	 */
	public void setPlayer(ClientPlayerTrackerUnit playerUnit) {
		this.playerUnit = playerUnit;
	}

	public ClientPlayerTrackerUnit getPlayerTrackerUnit() {
		return playerUnit;
	}

	public void setPlayer(EntityClientPlayerMP living) {
		// Change the player.
		ClientPlayerTrackerUnit unit = new ClientPlayerTrackerUnit(living);
		playerUnit = unit;
	}
	
	public void assertPlayer() {
		if (ClientPlayerTracker.instance.getPlayer() == null)
		{
			// Set the player to the correct one.
			ClientPlayerTracker.instance.setPlayer(Minecraft.getMinecraft().thePlayer);
		}
	}
}
