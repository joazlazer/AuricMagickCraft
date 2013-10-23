package joazlazer.mods.amc.network.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.orders.OrderRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PacketAuraUpdate extends AmcClientPacket {
	@Override
	public void handle(DataInputStream iStream, EntityPlayer player) {
		// Create variables we will use.
		byte typeNumb;
		int data0;
		int data1;
		int data2;
		UpdateType type = null;
		
		// Safely,
		try {
			// Get the variables from the data stream.
			typeNumb = iStream.readByte();
			data0 = iStream.readInt();
			data1 = iStream.readInt();
			data2 = iStream.readInt();
		} catch (IOException e) {
			// Print an error and exit.
			System.out.println("[ERROR] " + e.getLocalizedMessage());
			return;
		}
		
		// Iterate through the types.
		UpdateType[] types = UpdateType.values();
		for (int i = 0; i < types.length; i++) {
			// Figure out the enum value for each number.
			if (types[i].ordinal() == (int) typeNumb) type = types[i];
		}
		
		// Switch through the different change types.
		switch (type) {
			case COUNT: {
				AuricMagickCraft.playerTracker.playerStats.get(Minecraft.getMinecraft().thePlayer.username).aura = data0;
				System.out.println("count:  " + data0);
				break;
			}
			case LEVEL: {
				AuricMagickCraft.playerTracker.playerStats.get(Minecraft.getMinecraft().thePlayer.username).auraLevel = data0;
				System.out.println("level = " + data0);
				break;
			}
			case COLOR: {
				AuricMagickCraft.playerTracker.playerStats.get(Minecraft.getMinecraft().thePlayer.username).auraColor = data0;
			}
			case SHOW_ROSARY: {
				if (data0 == 0) AuricMagickCraft.playerTracker.playerStats.get(Minecraft.getMinecraft().thePlayer.username).showAuraRosary = false;
				else AuricMagickCraft.playerTracker.playerStats.get(Minecraft.getMinecraft().thePlayer.username).showAuraRosary = true;
				break;
			}
			case IS_AWAKE: {
				if (data0 == 0) AuricMagickCraft.playerTracker.playerStats.get(Minecraft.getMinecraft().thePlayer.username).isAwake = false;
				else AuricMagickCraft.playerTracker.playerStats.get(Minecraft.getMinecraft().thePlayer.username).isAwake = true;
				break;
			}
			case ORDER_ID: {
				AuricMagickCraft.playerTracker.playerStats.get(Minecraft.getMinecraft().thePlayer.username).orderUnlocName = OrderRegistry.getOrder(data0).getUnlocName();
			}
			case AURA_INC_BUFF: { 
				AuricMagickCraft.playerTracker.playerStats.get(Minecraft.getMinecraft().thePlayer.username).auraIncBuffer = (float) data0 / 1000.0f;
				break;
			}
		}
	}
	
	public void send(UpdateType type, int data0, int data1, int data2, EntityPlayerMP player) {
		byte flag = (byte) type.ordinal();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(13);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
		        outputStream.writeByte(flag);
		        outputStream.writeInt(data0);
		        outputStream.writeInt(data1);
		        outputStream.writeInt(data2);
		} catch (Exception ex) {
		        ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "AmcAuraUpdate";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToPlayer(packet, (Player) player);
	}
}
