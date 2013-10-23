package joazlazer.mods.amc.network.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.orders.OrderBase;
import joazlazer.mods.amc.orders.OrderRegistry;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PacketAwaken extends AmcServerPacket {

	@Override
	public void handle(DataInputStream iStream, EntityPlayer entityPlayer) {
		// Create variables.
		int entityId;
		int orderId;
		String username = "";
		String orderUnlocName = "";
		ArrayList<Character> characters = new ArrayList<Character>();
		ArrayList<Character> unlocCharacters = new ArrayList<Character>();
		int usernameLength;
		
		// Get the data safely.
		try {
			entityId = iStream.readInt();
			orderId = iStream.readInt();
		} 
		catch (IOException e) {
            e.printStackTrace();
            return;
		}
		
		// Use the entity id for getting the player's username.
		Entity entity = MinecraftServer.getServer().func_130014_f_().getEntityByID(entityId);
		
		// Safely create a player variable.
		if (entity instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) entity;
			
			// Make the username equal to the player's username.
			username = player.username;
		}
		
		OrderBase order = OrderRegistry.getOrder(orderId);
		
		// Safely get the unlocalized name of the order.
		if (order != null) {
			orderUnlocName = order.getUnlocName();
		}
		
		// Print debug text.
		if (AuricMagickCraft.debugMode) {
			System.out.println("Received awaken packet for " + username + 
					" being awakened into order " + orderUnlocName + ".");
		}
		
		// Awaken the player with the new order.
		AuricMagickCraft.playerTracker.awaken(username, orderUnlocName);
	}
	
	public void send(int entityId, int orderId, EntityClientPlayerMP player) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(100);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
		        outputStream.writeInt(entityId);
		        outputStream.writeInt(orderId);
		} catch (Exception ex) {
		        ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "AmcAwaken";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		player.sendQueue.addToSendQueue(packet);
		
		// Print debug text.
		if (AuricMagickCraft.debugMode)
		{
			System.out.println("Sent awaken packet to server.");
		}
	}
}
