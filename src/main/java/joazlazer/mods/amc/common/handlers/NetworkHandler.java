package joazlazer.mods.amc.common.handlers;

import joazlazer.mods.amc.common.network.MessageAwakeningControl;
import joazlazer.mods.amc.common.reference.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
    private static int id = 0;
    public static void registerPackets() {
        INSTANCE.registerMessage(MessageAwakeningControl.MessageAwakeningControlHandler.class, MessageAwakeningControl.class, id++, Side.SERVER);
        INSTANCE.registerMessage(MessageAwakeningControl.MessageAwakeningControlHandler.class, MessageAwakeningControl.class, id++, Side.CLIENT);
    }
}
