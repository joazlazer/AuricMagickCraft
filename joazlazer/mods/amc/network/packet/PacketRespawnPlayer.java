package joazlazer.mods.amc.network.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import joazlazer.mods.amc.AuricMagickCraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldServer;

public class PacketRespawnPlayer extends AmcServerPacket {
	@Override
	public void handle(DataInputStream iStream, EntityPlayer player) {
		
		int entityId;
        
        try {
                entityId = iStream.readInt();
        } catch (IOException e) {
                e.printStackTrace();
                return;
        }
		// We have received a packet to teleport the player.
		// Print debug text.
		if (AuricMagickCraft.debugMode)
		{
			System.out.println(
					"Recieved player respawn packet from client for entity with id " + 
			entityId + ".");
		}
		
		// Get the entity.
		EntityLivingBase entityLiving = (EntityLivingBase) MinecraftServer.getServer().func_130014_f_().getEntityByID(entityId);
		
		// Make sure the entity specified is a player.
		if (entityLiving instanceof EntityPlayerMP) {
			// Cast the entity to a player.
			EntityPlayerMP entityplayermp = (EntityPlayerMP) entityLiving;
			
			// Get the chunk coordinates of the bed int the specified dimension.
			ChunkCoordinates chunkcoordinates = entityplayermp.getBedLocation(entityplayermp.dimension);
			
			// Create integers to store the new, manipulated coordinates to respawn the player at.
			double newX = 0;
			double newY = 0;
			double newZ = 0;
			
			// Get the world server for the specified player's dimension.
			WorldServer worldserver = MinecraftServer.getServer().worldServerForDimension(entityplayermp.dimension);
			
			// Create a new chunk coordinate location.
			ChunkCoordinates chunkcoordinates1;
			
			// Create a flag to use.
			boolean flag = false;
			
			// If the player has a bed.
	        if (chunkcoordinates != null)
	        {
	        	// Set the second chunk coordinates to the verified respawn coordinates.
	            chunkcoordinates1 = EntityPlayer.verifyRespawnCoordinates(MinecraftServer.getServer().worldServerForDimension(entityplayermp.dimension), chunkcoordinates, flag);
	            
	            // If the second coordinates aren't null,
	            if (chunkcoordinates1 != null)
	            {
	            	// Set the spawn -2 (to counteract what's added later.)
	            	chunkcoordinates1.posY -= 2;
	            	
	            	// Set the spawn chunk.
	                entityplayermp.setSpawnChunk(chunkcoordinates, flag);
	            }
	            else
	            {
	            	// Get the spawn chunk.
 		        chunkcoordinates1 = worldserver.getSpawnPoint();
	            }
	        }
	        else
	        {
	        	// Get the spawn chunk.
	        chunkcoordinates1 = worldserver.getSpawnPoint();
	        }
	        
	        // Set the coordinates to the calculated coordinates.
        newX = (double)((float)chunkcoordinates1.posX + 0.5f);
        newY = (double)((float)chunkcoordinates1.posY + 0.5f);
        newZ = (double)((float)chunkcoordinates1.posZ + 0.5f);
	        
	        // Load the chunk.
	        worldserver.theChunkProviderServer.loadChunk((int)entityplayermp.posX >> 4, (int)entityplayermp.posZ >> 4);

        // Dismount the player.
        entityplayermp.mountEntity((Entity)null);
        
        // Print debug text.
		if (AuricMagickCraft.debugMode)
		{
			System.out.println(
					"Respawning player to " + 
			newX + ", " + newY + ", " + newZ + ".");
		}
        
        // Teleport the player's position to the new respawn location.
        entityplayermp.setPositionAndUpdate(newX, newY, newZ);
        
        // Make sure the player isn't gonna suffocate.
        while (!worldserver.getCollidingBoundingBoxes(entityplayermp, entityplayermp.boundingBox).isEmpty())
	        {
        	// Set the player's new position to one up from the old one.
	            entityplayermp.setPosition(entityplayermp.posX, entityplayermp.posY + 1.0D, entityplayermp.posZ);
	        }
		}
	}
	
	public void send(int entityId, EntityClientPlayerMP player) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(9);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
		        outputStream.writeInt(entityId);
		} catch (Exception ex) {
		        ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "AmcPlayerRespawn";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		player.sendQueue.addToSendQueue(packet);
		
		// Print debug text.
		if (AuricMagickCraft.debugMode)
		{
			System.out.println("Sent player respawn packet to server.");
		}
	}
}
