package joazlazer.mods.amc.casting.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import joazlazer.mods.amc.handlers.NetworkHandler;
import joazlazer.mods.amc.network.MessageCastingDataRequest;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientCastingManager {
    public static HashMap<String, ClientCastingStatus> PlayerCasts = new HashMap<String, ClientCastingStatus>();

    @SubscribeEvent
    public void connectToServer(FMLNetworkEvent.ClientConnectedToServerEvent e) {
        System.out.println("Requesting");
        delayedRequest();
    }

    @SubscribeEvent
    public void disconnectFromServer(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        // Clear it.
        PlayerCasts = new HashMap<String, ClientCastingStatus>();
    }

    public void delayedRequest() {
        DelayRequester de = new DelayRequester();
        delays.add(de);
    }

    ArrayList<DelayRequester> delays = new ArrayList<DelayRequester>();

    public static class DelayRequester {
        public DelayRequester() {

        }

        public void reduce() {
            x--;
            if(x <= 0) {
                NetworkHandler.Network.sendToServer(new MessageCastingDataRequest());
                done = true;
            }
        }

        private int x = 20;
        boolean done = false;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        ArrayList<DelayRequester> toBeRemoved = new ArrayList<DelayRequester>();
        for(DelayRequester de : delays) {
            de.reduce();
            if(de.done) {
                toBeRemoved.add(de);
            }
        }

        for(DelayRequester de : toBeRemoved) {
            delays.remove(de);
        }
    }

    public static void onPlayerConnectsToServer() {
        System.out.println("SOMEBODY JOINED MWGAGAGAGA");
        NetworkHandler.Network.sendToServer(new MessageCastingDataRequest());
    }

    public static void onPlayerDisconnectsFromServer() {
        System.out.println("SOMEBOEDY LEFT NOOOOO");
    }

    public static void receiveCastingData(HashMap<String, ClientCastingStatus> data) {
        System.out.println("Recieved Answer");
        for(Object obj : Minecraft.getMinecraft().theWorld.playerEntities) {
            EntityPlayer player = (EntityPlayer) obj;
            String username = player.getDisplayName();
            if(data.containsKey(username) && data.get(username) != null) {
                ClientCastingStatus status = data.get(username);
                PlayerCasts.put(username, status);
            }
            else {
                PlayerCasts.put(username, new ClientCastingStatus(username));
            }
        }
    }
}
