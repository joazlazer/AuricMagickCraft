package joazlazer.mods.amc.playertracking;

import java.lang.ref.WeakReference;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import joazlazer.mods.amc.AuricMagickCraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.IPlayerTracker;

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

	public void savePlayerStats(EntityPlayer player, boolean clean) {
		if (player != null)
        {
            AmcPlayerStats stats = getPlayerStats(player.username);
            if (stats != null)
            {
            	// Save the stats.
            	stats.saveToNBT(player.getEntityData());
                if (clean)
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

        NBTTagCompound saves = player.getEntityData();
        stats.saveToNBT(saves);
	}
	
	/* Find the right player */
    public AmcPlayerStats getPlayerStats (String username)
    {
    	AmcPlayerStats stats = playerStats.get(username);
        System.out.println("Stats: " + stats);
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
}
