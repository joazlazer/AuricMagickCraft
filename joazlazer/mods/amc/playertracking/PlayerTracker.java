package joazlazer.mods.amc.playertracking;

import java.lang.ref.WeakReference;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.network.PacketHandler;
import joazlazer.mods.amc.network.packet.UpdateType;
import joazlazer.mods.amc.orders.OrderRegistry;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.relauncher.Side;

public class PlayerTracker implements IPlayerTracker {

	// Current hash map.
	public ConcurrentHashMap<String, AmcPlayerStats> playerStats = new ConcurrentHashMap<String, AmcPlayerStats>();
	
	@Override
	public void onPlayerLogin(EntityPlayer player) {
		// Get the player's data.
		NBTTagCompound tags = player.getEntityData();
		
		// If the tags don't have the tag,
        if (!tags.hasKey("AMC"))
        {
        	// The create it.
            tags.setCompoundTag("AMC", new NBTTagCompound());
        }
        
        // Create a new AMC player statistics instance.
		AmcPlayerStats stats = new AmcPlayerStats();
		
		// Set the player.
        stats.player = new WeakReference<EntityPlayer>(player);
        
        // Get the NBT tag compound.
        NBTTagCompound saves = tags.getCompoundTag("AMC");
        
        // Load the data.
        stats.isAwake = saves.getBoolean("isAwake");
        stats.aura = saves.getInteger("auraCount");
        stats.auraLevel = saves.getInteger("auraLevel");
        stats.auraIncBuffer = ((float) saves.getInteger("auraIncBuffer") / 1000.0F);
        
        if (saves.hasKey("auraColor")) {
        	stats.auraColor = saves.getInteger("auraColor");
        }
        else {
        	Random rand = new Random();
        	int red = rand.nextInt(255);
        	int blue = rand.nextInt(255);
        	int green = rand.nextInt(255);
        	if (player.username == "joazlazer" || player.username == "Boom_Doom2") {
        		red = 0;
        		green = 0;
        		blue = 0;
        	}
        	else {
        		int chance = rand.nextInt(100);
        		if (chance > 2 && chance < 5) {
        			red = 249;
        			green = 247;
        			blue = 242;
        		}
        		else if (chance > 78 && chance < 81) {
        			red = 232;
        			green = 213;
        		}
        	}
        	// Bit shift the color.
        	stats.auraColor = ((red << 16) + (green << 8) + blue);
        	
        	// Print debug info.
            if (AuricMagickCraft.debugMode) {
            	System.out.println(" R: " + red + " G: " + green + " B: " + blue + ".");
            }
        }
        
        // Figure out the aura increment.
        stats.auraIncrement = 0.5F;
        
        stats.orderUnlocName = saves.getString("orderUnlocName");
        stats.showAuraRosary = saves.getBoolean("showAuraRosary");
        
        // Save the player's statistics.
        playerStats.put(player.username, stats);
        
        // Print debug info.
        if (AuricMagickCraft.debugMode) {
        	System.out.println("Player " + player.username + " has just logged on with AMC player tracker.");
        }
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
		// Print debug info.
        if (AuricMagickCraft.debugMode) {
        	System.out.println("Player " + player.username + " has just logged out with AMC player tracker.");
        }
        
        savePlayerStats(player, true);
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {
		// Print debug info.
        if (AuricMagickCraft.debugMode) {
        	System.out.println("Player " + player.username + " has just changed dimensions with AMC player tracker.");
        }
        
        savePlayerStats(player, false);
	}

	public void savePlayerStats(EntityPlayer player, boolean remove) {
		if (player != null)
        {
            AmcPlayerStats stats = getPlayerStats(player.username);
            if (stats != null)
            {
            	// Save the stats.
            	stats.saveToNBT();
                if (remove)
                    playerStats.remove(player.username);
            }
        }
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {
		// Print debug info.
        if (AuricMagickCraft.debugMode) {
        	System.out.println("Player " + player.username + " has just respawned with AMC player tracker.");
        }
        
        // Revalidate the entity data.
        AmcPlayerStats stats = getPlayerStats(player.username);
        stats.player = new WeakReference<EntityPlayer>(player);

        stats.saveToNBT();
	}
	
	/* Find the right player */
    public AmcPlayerStats getPlayerStats (String username)
    {
    	AmcPlayerStats stats = playerStats.get(username);
        if (stats == null)
        {
            stats = new AmcPlayerStats();
            playerStats.put(username, stats);
        }
        return stats;
    }

    public EntityPlayer getEntityPlayer (String username)
    {
    	AmcPlayerStats stats = playerStats.get(username);
        if (stats == null)
        {
            return null;
        }
        else
        {
            return stats.player.get();
        }
    }
    
    public void awaken(String username, String orderUnlocName2) {
    	// Make sure we contain the given username.
	 	if (playerStats.containsKey(username)) {
	 		// Set the different data.
	 		playerStats.get(username).aura = AmcPlayerStats.getMaxAura(1, playerStats.get(username).auraColor);
	 		playerStats.get(username).auraLevel = 1;
	 		playerStats.get(username).isAwake = true;
	 		playerStats.get(username).orderUnlocName = orderUnlocName2;
	 		
	 		// Determine what side we're on.
	        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
	        	if (AuricMagickCraft.debugMode) { System.out.println("We are on the client side when we awoken"); }
	        }
	        else {
	        	if (AuricMagickCraft.debugMode) { System.out.println("We are on the server side when we awoken"); }
	        	EntityPlayerMP player = (EntityPlayerMP) playerStats.get(username).player.get();
	        	AmcPlayerStats stats = playerStats.get(username);
	        	updateToClient(UpdateType.COUNT, stats.aura, 0, 0, player);
	        	updateToClient(UpdateType.LEVEL, stats.auraLevel, 0, 0, player);
	        	updateToClient(UpdateType.IS_AWAKE, 1, 0, 0, player);
	        	updateToClient(UpdateType.ORDER_ID, OrderRegistry.getOrder(orderUnlocName2).getId(), 0, 0, player);
	        	updateToClient(UpdateType.COLOR, stats.auraColor, 0, 0, player);
	        	updateToClient(UpdateType.SHOW_ROSARY, 1, 0, 0, player);
	        }
	 	}
    }
    
    public void updateToClient(UpdateType type, int p1, int p2, int p3, EntityPlayerMP player) {
    	AmcPlayerStats stats = playerStats.get(player.username);
    	float inc;
    	switch (type) { }
    	PacketHandler.INSTANCES.auraUpdatePacket.send(type, p1, p2, p3, player);
    }
}
