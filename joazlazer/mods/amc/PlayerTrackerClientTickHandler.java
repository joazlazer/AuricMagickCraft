package joazlazer.mods.amc;

import java.util.EnumSet;

import net.minecraft.client.entity.EntityClientPlayerMP;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import joazlazer.mods.amc.entity.player.*;

public class PlayerTrackerClientTickHandler implements IScheduledTickHandler {

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		// If we're receiving a player.
		if (tickData[0] instanceof EntityClientPlayerMP)
		{
			// Create a player object.
			EntityClientPlayerMP player = (EntityClientPlayerMP) tickData[0];
			
			// Change the player.
			ClientPlayerTracker.instance.getPlayer().setPlayer(player);
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
		return "amc.tick.clientplayerupdate";
	}

	@Override
	public int nextTickSpacing() {
		return 1;
	}
	
	// Instance.
	public static PlayerTrackerClientTickHandler instance;
	
	// A method called by mod initialization.
	public static void initInstance() {
		instance = new PlayerTrackerClientTickHandler();
		TickRegistry.registerScheduledTickHandler(instance, Side.CLIENT);
	}
}
