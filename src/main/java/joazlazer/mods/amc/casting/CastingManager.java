package joazlazer.mods.amc.casting;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import joazlazer.mods.amc.handlers.NetworkHandler;
import joazlazer.mods.amc.network.MessageCastingControl;
import joazlazer.mods.amc.network.MessageCastingUpdate;
import joazlazer.mods.amc.network.MessageServerEvent;
import joazlazer.mods.amc.util.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import org.lwjgl.Sys;

import java.util.HashMap;

public class CastingManager {

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        System.out.println("Player logged in");
        PlayerCasts.put(event.player.getDisplayName(), new CastingStatus());
        for (Object obj : MinecraftServer.getServer().getEntityWorld().playerEntities) {
            EntityPlayerMP player = (EntityPlayerMP) obj;
            System.out.println(player.getDisplayName());
            System.out.println(event.player.getDisplayName());
            if (player.getDisplayName() != event.player.getDisplayName()) {
                NetworkHandler.Network.sendTo(new MessageServerEvent(MessageServerEvent.EventType.CONNECT), player);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        System.out.println("Player logged out");
        for (Object obj : MinecraftServer.getServer().getEntityWorld().playerEntities) {
            EntityPlayerMP player = (EntityPlayerMP) obj;
            if (player.getDisplayName() != event.player.getDisplayName()) {
                NetworkHandler.Network.sendTo(new MessageServerEvent(MessageServerEvent.EventType.DISCONNECT), player);
            }
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent e) {
        updateContainerStatusOfAllPlayers();
        updateAllCastingSpells();
    }

    public static void updateContainerStatusOfAllPlayers() {
        for (String username : MinecraftServer.getServer().getAllUsernames()) {
            EntityPlayer player = MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(username);
            if (PlayerCasts.keySet().contains(username) && PlayerCasts.get(username).receiveTick) {
                if ((!(player.openContainer instanceof ContainerPlayer) && (player.openContainer != null))) {
                    CastingManager.updateCastingStatus(username, MessageCastingControl.ControlType.INTERRUPT);
                }
            }
        }
    }

    public static void updateAllCastingSpells() {
        World world = MinecraftServer.getServer().getEntityWorld();
        for (String string : PlayerCasts.keySet()) {
            //System.out.println(string);
            CastingStatus status = PlayerCasts.get(string);
            if (status.receiveTick) {
                EntityPlayer player = MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(string);
                status.selectedSpell.onServerCastTick(player, world, status);
            }
        }
    }

    public static void updateCastingStatus(String username, MessageCastingControl.ControlType type) {
        if (!PlayerCasts.containsKey(username)) PlayerCasts.put(username, new CastingStatus());
        CastingStatus status = PlayerCasts.get(username);
        EntityPlayer player = MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(username);
        World world = MinecraftServer.getServer().getEntityWorld();
        System.out.println("Player retrieved: " + player.getDisplayName() + "; Player Entity ID: " + player.getEntityId() + "; World: " + world.toString());
        System.out.println("Player casting status: " + status + "; receiveTick: " + status.receiveTick + "; Selected spell: " + status.selectedSpell.getUnlocName() + "; Custom NBT: " + status.customNBT.toString());
        switch (type) {
            case START: {
                status.selectedSpell.onStartCasting(player, world, status);
                break;
            }
            case STOP: {
                status.selectedSpell.onStopCasting(player, world, status);
                break;
            }
            case CANCEL: {
                status.selectedSpell.onCancelCasting(player, world, status);
                break;
            }
            case INTERRUPT: {
                status.selectedSpell.onInterruptCasting(player, world, status);
                break;
            }
            default: {
                return;
            }
        }
        PlayerCasts.put(username, status);
        System.out.println("NewCastingStatusAfter: " + status + "; receiveTick: " + status.receiveTick + "; Selected spell: " + status.selectedSpell.getUnlocName() + "; Custom NBT: " + status.customNBT.toString());
    }

    public static HashMap<String, CastingStatus> PlayerCasts = new HashMap<String, CastingStatus>();

    public static void onCastControlPacket(MessageCastingControl.ControlType controlType, String username) {
        System.out.println("Recieved onCastControlPacket(): " + controlType.toString() + "; Username: " + username + "; Player cast data exists? " + (PlayerCasts.containsKey(username) ? "true" : "false"));
        if (!PlayerCasts.containsKey(username)) PlayerCasts.put(username, new CastingStatus());
        CastingStatus status = PlayerCasts.get(username);
        EntityPlayer player = MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(username);

        switch (controlType) {
            case CANCEL: {
                if (!status.receiveTick) return;
                updateCastingStatus(username, MessageCastingControl.ControlType.CANCEL);
                break;
            }
            case START: {
                if (!(player.openContainer instanceof ContainerPlayer) && player.openContainer != null) return;
                updateCastingStatus(username, MessageCastingControl.ControlType.START);
                break;
            }
            case STOP: {
                if (!status.receiveTick) return;
                updateCastingStatus(username, MessageCastingControl.ControlType.STOP);
                break;
            }
            case INTERRUPT: {
                if (!status.receiveTick) return;
                updateCastingStatus(username, MessageCastingControl.ControlType.INTERRUPT);
                break;
            }
            default: {
                return;
            }
        }
    }

    public static void updateToAll(CastingStatus status, String username) {
        for(String u : MinecraftServer.getServer().getAllUsernames()) {
            NetworkHandler.Network.sendTo(new MessageCastingUpdate(status, username), (EntityPlayerMP) MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(u));
        }
    }

    public static void updateToAll(String username) {
        for(String u : MinecraftServer.getServer().getAllUsernames()) {
            NetworkHandler.Network.sendTo(new MessageCastingUpdate(PlayerCasts.get(username), username), (EntityPlayerMP) MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(u));
        }
    }
}
