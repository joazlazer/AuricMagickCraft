package joazlazer.mods.amc.entity.player;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import joazlazer.mods.amc.AuricMagickCraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class ServerPlayerTracker {
	private Hashtable<String, ServerPlayerTrackerUnit> playerMap;
	private Hashtable<String, ServerPlayerTrackerUnit> playerData;
	
	static {
		instance = new ServerPlayerTracker();
	}
	
	public static final ServerPlayerTracker instance;
	
	public ServerPlayerTracker()
	{
		this.playerMap = new Hashtable<String, ServerPlayerTrackerUnit>();
		this.playerData = new Hashtable<String, ServerPlayerTrackerUnit>();
	}

	/**
	 * @return the playerMap
	 */
	public Hashtable<String, ServerPlayerTrackerUnit> getPlayerMap() {
		return playerMap;
	}

	/**
	 * @param playerMap the playerMap to set
	 */
	public void setPlayerMap(Hashtable<String, ServerPlayerTrackerUnit> playerMap) {
		this.playerMap = playerMap;
	}
	
	public ServerPlayerTrackerUnit getPlayerTrackerUnit(String username)
	{
		if (playerMap.containsKey(username))
		return playerMap.get(username);
		else return null;
	}
	
	public void loadFromFile(File fileToLoad) {
		
	}
	
	// A method for adding the player.
	@ForgeSubscribe(priority = EventPriority.NORMAL)
	public void addPlayerToList(EntityJoinWorldEvent e)
	{
		// Add the player to the list.
		if (e.entity instanceof EntityLivingBase)
		{
			EntityLivingBase living = (EntityLivingBase) e.entity;
			if (living instanceof EntityPlayerMP)
			{
				ServerPlayerTrackerUnit unit = playerData.containsKey(((EntityPlayerMP)living).username) ? 
						(playerData.get(((EntityPlayerMP)living).username)) : new ServerPlayerTrackerUnit((EntityPlayerMP)living);
				unit.setPlayer((EntityPlayerMP)living);
				playerMap.put(((EntityPlayerMP)living).username, unit);
			}
		}
	}
	
	// A method for updating all of the player instances with the server's current ones.
	public void updatePlayers() {
		 for (WorldServer world : MinecraftServer.getServer().worldServers) {
			 // Loop through the players.
			 for (Object obj : world.playerEntities) {
				 // Safely cast the player.
				 if (obj instanceof EntityPlayerMP) {
					 // Create a player variable.
					 EntityPlayerMP player = (EntityPlayerMP) obj;
					 
					 // Set its player.
					 playerMap.put(player.username, playerMap.get(player.username).setPlayer(player));
				 }
			 }
		 }
	}
	
	// A method for removing a player if needed.
	public void removePlayersIfNeeded()
	{
		// Get the count of players on the server.
		int count = MinecraftServer.getServer().getCurrentPlayerCount();
		
		// If the current count of players isn't equal to the current count of tracked players.
		if (count > playerMap.keySet().size()) {
			// Delete players until we get the correct count.
			while (count > playerMap.keySet().size()) {
				// Get the username of the player to be deleted.
				String user = null;
				
				// Get the list of players.
				String[] usernameList = MinecraftServer.getServer().getAllUsernames();
				
				// Create an array list for storing player usernames.
				ArrayList<String> usernames = new ArrayList<String>();
				ArrayList<String> keys = new ArrayList<String>();
				
				// Loop through the player usernames.
				for (int i = 0; i < usernameList.length; i++) {
					// Add the current username to the list.
					usernames.add(usernameList[i]);
				}
				
				// Iterate through the keys.
				for (Enumeration<String> e = playerMap.keys(); e.hasMoreElements();)
				{
					// Add the current username to the list.
					keys.add(e.nextElement());
				}
				
				// Loop through the players.
				for (int i = 0; i < playerMap.keySet().size(); i++) {
					// If the current player isn't in the current list,
					if (!usernames.contains(keys.get(i))) user = keys.get(i);
				}
				
				// Add the current player's data to the playerData.
				playerData.put(user, playerMap.get(user));
				
				// Remove the current user from the playerMap.
				playerMap.remove(playerMap.get(user));
				
				// Print debug text.
				if (AuricMagickCraft.debugMode) {
					System.out.println("User " + user + " has just been removed from the active player list for amc server player tracker.");
				}
			}
		}
	}

	public void awaken(String username, String orderUnlocName) {
		// Make sure we contain the given username.
		if (playerMap.containsKey(username)) {
			// Set the different data.
			playerMap.get(username).aura = 0;
			playerMap.get(username).auricLevel = 1;
			playerMap.get(username).isAwake = true;
			playerMap.get(username).orderUnlocName = orderUnlocName;
		}
	}
}
