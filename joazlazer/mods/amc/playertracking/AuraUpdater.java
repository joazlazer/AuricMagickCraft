package joazlazer.mods.amc.playertracking;

import java.util.EnumSet;

import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.network.PacketHandler;
import joazlazer.mods.amc.network.packet.UpdateType;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;

public class AuraUpdater implements IScheduledTickHandler {

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {		
		for (int i = 0; i < MinecraftServer.getServer().getAllUsernames().length; i++) {
			// If the player is awake.
			if (AuricMagickCraft.playerTracker.playerStats.get(MinecraftServer.getServer().getAllUsernames()[i]).isAwake) {
				AmcPlayerStats stats = AuricMagickCraft.playerTracker.playerStats.get(MinecraftServer.getServer().getAllUsernames()[i]);
				// If the aura is greater than or equal to the maximum,
				if (stats.aura >= AmcPlayerStats.getMaxAura(stats.auraLevel, stats.auraColor)) {
					// Reset it.
					stats.aura = AmcPlayerStats.getMaxAura(stats.auraLevel, stats.auraColor);
					stats.auraIncBuffer = 0.0f;
				}
				// If those criteria aren't met,
				else {
					// Increment stuff.
					stats.auraIncBuffer = stats.auraIncBuffer + stats.auraIncrement;
					while (stats.auraIncBuffer >= 1.0f) {
						stats.auraIncBuffer--;
						stats.aura++;
					}
					
					// Validate again.
					// If the aura is greater than or equal to the maximum,
					if (stats.aura >= AmcPlayerStats.getMaxAura(stats.auraLevel, stats.auraColor)) {
						// Reset it.
						stats.aura = AmcPlayerStats.getMaxAura(stats.auraLevel, stats.auraColor);
						stats.auraIncBuffer = 0.0f;
					}
				}
				
				// Update.
				AuricMagickCraft.playerTracker.playerStats.put(MinecraftServer.getServer().getAllUsernames()[i], stats);
				AuricMagickCraft.playerTracker.updateToClient(UpdateType.COUNT, 
						stats.aura, 0, 0, 
						(EntityPlayerMP) AuricMagickCraft.playerTracker.
						playerStats.get(MinecraftServer.getServer().
								getAllUsernames()[i]).player.get());
				AuricMagickCraft.playerTracker.updateToClient(UpdateType.AURA_INC_BUFF, 
						(int) (stats.auraIncBuffer * 1000.0F), 0, 0, 
						(EntityPlayerMP) AuricMagickCraft.playerTracker.
						playerStats.get(MinecraftServer.getServer().
								getAllUsernames()[i]).player.get());
				System.out.println("aura_inc = " + stats.auraIncrement);
			}
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
	
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.SERVER);
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int nextTickSpacing() {
		// TODO Auto-generated method stub
		return 0;
	}

}
