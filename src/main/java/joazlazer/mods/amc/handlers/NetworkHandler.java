package joazlazer.mods.amc.handlers;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import joazlazer.mods.amc.network.PacketPlayerTrackerUpdate;
import joazlazer.mods.amc.reference.Channels;
import joazlazer.mods.amc.reference.Messages;
import scala.sys.process.ProcessBuilderImpl;

public class NetworkHandler {
    public static SimpleNetworkWrapper Network;
    public static void init() {
        Network = NetworkRegistry.INSTANCE.newSimpleChannel(Channels.MAIN);
        Network.registerMessage(PacketPlayerTrackerUpdate.Handler.class, PacketPlayerTrackerUpdate.class, Messages.SERVER_PLAYER_TRACKER_UPDATE_DISC, Side.SERVER);
        Network.registerMessage(PacketPlayerTrackerUpdate.Handler.class, PacketPlayerTrackerUpdate.class, Messages.CLIENT_PLAYER_TRACKER_UPDATE_DISC, Side.CLIENT);
    }
}
