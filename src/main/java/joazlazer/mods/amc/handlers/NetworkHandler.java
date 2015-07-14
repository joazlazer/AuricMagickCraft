package joazlazer.mods.amc.handlers;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import joazlazer.mods.amc.network.*;
import joazlazer.mods.amc.reference.Messages;

public class NetworkHandler {
    public static SimpleNetworkWrapper Network;
    public static void init() {
        Network = NetworkRegistry.INSTANCE.newSimpleChannel("amc");
        Network.registerMessage(MessageCastingControl.Handler.class, MessageCastingControl.class, Messages.Server.CASTING_CONTROL_DISC, Side.SERVER);
        Network.registerMessage(MessagePlayerTrackerUpdate.Handler.class, MessagePlayerTrackerUpdate.class, Messages.Client.PLAYER_TRACKER_UPDATE_DISC, Side.CLIENT);
        Network.registerMessage(MessagePlayerTrackerUpdate.Handler.class, MessagePlayerTrackerUpdate.class, Messages.Server.PLAYER_TRACKER_UPDATE_DISC, Side.SERVER);
        Network.registerMessage(MessageCastingDataReply.Handler.class, MessageCastingDataReply.class, Messages.Client.CAST_DATA_REPLY_DISC, Side.CLIENT);
        Network.registerMessage(MessageCastingDataRequest.Handler.class, MessageCastingDataRequest.class, Messages.Server.CAST_DATA_REQUEST_DISC, Side.SERVER);
        Network.registerMessage(MessageServerEvent.Handler.class, MessageServerEvent.class, Messages.Client.SERVER_EVENT_DISC, Side.CLIENT);
        Network.registerMessage(MessagePlayerRespawn.Handler.class, MessagePlayerRespawn.class, Messages.Server.PLAYER_RESPAWN_DISC, Side.SERVER);
        Network.registerMessage(MessageCastingUpdate.Handler.class, MessageCastingUpdate.class, Messages.Client.CAST_UPDATE, Side.CLIENT);
    }
}
