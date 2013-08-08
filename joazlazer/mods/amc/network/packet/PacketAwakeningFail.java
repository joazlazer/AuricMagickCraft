package joazlazer.mods.amc.network.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;
import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.lib.Strings;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatMessageComponent;

public class PacketAwakeningFail extends AmcServerPacket {
	
public static Random RANDOM = new Random();

	@Override
	public void handle(DataInputStream iStream, EntityPlayer entityPlayer) {
        int entityId;
        int health;
        
        try {
                entityId = iStream.readInt();
                health = iStream.readInt();
        } catch (IOException e) {
                e.printStackTrace();
                return;
        }
		
		// Print debug text.
		if (AuricMagickCraft.debugMode)
		{
			System.out.println(
					"Recieved entity health change packet from client for entity with id " + 
			entityId + " setting health to " + health + ".");
		}
		
		// Get the entity from the specified id.
		EntityLivingBase entity = (EntityLivingBase) MinecraftServer.getServer().func_130014_f_().getEntityByID(entityId);
		
		// As long as the entity exists in the world,
		if (entity != null)
		{
			if (!(entity instanceof EntityPlayerMP)) {
				// Set the entity's health to the specified number.
				entity.setEntityHealth((float) health);
			}
			else {
				// Create a player by safely casting the entity.
				EntityPlayerMP player = (EntityPlayerMP) entity;
				
				// Create an inventory from the player.
				IInventory playerInv = player.inventory;
				
				// Iterate through the whole inventory.
				for (int i = 0; i < playerInv.getSizeInventory(); i++) {
					// Get the item stack in the current slot.
					ItemStack currentStack = playerInv.getStackInSlot(i);
					
					// If there actually is an item stack there,
					if (currentStack != null) {
						// Create the new spawn coordinates.
						float spawnX = (float) (player.posX + RANDOM.nextFloat());
						float spawnY = (float) (player.posY + RANDOM.nextFloat());
						float spawnZ = (float) (player.posZ + RANDOM.nextFloat());
						
						// Create a new Entity for the item.
						EntityItem itemEntity = new EntityItem(MinecraftServer.getServer()
								.func_130014_f_(), spawnX, spawnY, spawnZ, currentStack);
						
						// Create a multiplier variable.
						float mult = 0.05F;
						
						// Change the motion of it to be random.
						itemEntity.motionX = (-1.5F + RANDOM.nextFloat() + RANDOM.nextInt(2)) * mult;
						itemEntity.motionY = (4 + RANDOM.nextFloat()) * mult;
						itemEntity.motionZ = (-1.5F + RANDOM.nextFloat() + RANDOM.nextInt(2)) * mult;
						
						// Spawn the entity in the world.
						MinecraftServer.getServer().func_130014_f_().spawnEntityInWorld(itemEntity);
						
						// Remove the player's item.
						playerInv.setInventorySlotContents(i, null);
					}
				}
				
				MinecraftServer.getServer().getConfigurationManager().sendChatMsg(
						ChatMessageComponent.func_111077_e(player.username + Strings.AWAKEN_EXIT_DEATH_MESSAGE));
				
				// Some of the player's death code.
		        player.addStat(StatList.deathsStat, 1);
				
				// Set the entity's health to the specified number.
				player.setEntityHealth((float) health);
			}
		}
	}
	
	public void send(int entityId, int health, EntityClientPlayerMP player) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(9);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
		        outputStream.writeInt(entityId);
		        outputStream.writeInt(health);
		} catch (Exception ex) {
		        ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "AwakenFail";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		player.sendQueue.addToSendQueue(packet);
		
		// Print debug text.
		if (AuricMagickCraft.debugMode)
		{
			System.out.println("Sent awaken fail packet to server.");
		}
	}
}
