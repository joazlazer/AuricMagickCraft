package joazlazer.mods.amc.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.entity.player.ServerPlayerTracker;
import joazlazer.mods.amc.lib.Strings;
import joazlazer.mods.amc.network.packet.AmcClientPacket;
import joazlazer.mods.amc.network.packet.AmcServerPacket;
import joazlazer.mods.amc.network.packet.PacketAwaken;
import joazlazer.mods.amc.network.packet.PacketAwakeningFail;
import joazlazer.mods.amc.network.packet.PacketRespawnPlayer;
import joazlazer.mods.amc.orders.OrderBase;
import joazlazer.mods.amc.orders.OrderRegistry;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler implements IPacketHandler {

	private static Map<String, AmcClientPacket> clientMap = new Hashtable<String, AmcClientPacket>();
    private static Map<String, AmcServerPacket> serverMap = new Hashtable<String, AmcServerPacket>();
	
    public static class INSTANCES {
    	public static PacketAwaken awakenPacket;
    	public static PacketRespawnPlayer respawnPlayerPacket;
    	public static PacketAwakeningFail awakenStopPacket;
    	
    	static {
    		awakenPacket = new PacketAwaken();
    		respawnPlayerPacket = new PacketRespawnPlayer();
    		awakenStopPacket = new PacketAwakeningFail();
    	}
    }
    
    static {
    	serverMap.put("AmcAwaken", INSTANCES.awakenPacket);
    	serverMap.put("AmcPlayerRespawn", INSTANCES.respawnPlayerPacket);
    	serverMap.put("AwakenFail", INSTANCES.awakenStopPacket);
    }

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player par3Player) {
        EntityPlayer player= (EntityPlayer)par3Player;
        DataInputStream iStream = new DataInputStream(new ByteArrayInputStream(packet.data));
            
        // Determine what side we're on.
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
        	clientMap.get(packet.channel).handle(iStream, player);
        }
        else {
        	serverMap.get(packet.channel).handle(iStream, player);
        }
    }
}