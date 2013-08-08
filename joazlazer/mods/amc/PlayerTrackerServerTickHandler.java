package joazlazer.mods.amc;

import java.util.EnumSet;
import joazlazer.mods.amc.entity.player.ServerPlayerTracker;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class PlayerTrackerServerTickHandler implements IScheduledTickHandler{

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		// If we're receiving a player.
		if (tickData[0] instanceof EntityPlayerMP)
		{
			// Create a player object.
			EntityPlayerMP player = (EntityPlayerMP) tickData[0];
			
			// Remove any players if needed.
			ServerPlayerTracker.instance.removePlayersIfNeeded();
			
			// Update the players.
			ServerPlayerTracker.instance.updatePlayers();
			
			// Sync with the client.
			
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel() {
		return "amc.tick.serverplayerupdate";
	}

	@Override
	public int nextTickSpacing() {
		return 1;
	}

	// Instance.
	public static PlayerTrackerServerTickHandler instance;
	
	// A method called by mod initialization.
	public static void initInstance() {
		instance = new PlayerTrackerServerTickHandler();
		TickRegistry.registerScheduledTickHandler(instance, Side.SERVER);
	}
}
