package joazlazer.mods.amc.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import joazlazer.mods.amc.casting.client.ClientCastingManager;

public class MessageServerEvent implements IMessage {

    // Used by FML and Reflection.
    public MessageServerEvent() {

    }

    public MessageServerEvent(EventType type) {
        this.type = type;
    }

    public enum EventType {
        CONNECT,
        DISCONNECT;

        public int toInt() {
            if(this == CONNECT) return 0;
            if(this == DISCONNECT) return 1;
            else return -1;
        }

        public static EventType fromInt(int i) {
            if(i == 0) return CONNECT;
            if(i == 1) return DISCONNECT;
            else return null;
        }
    }

    public EventType type;

    @Override
    public void fromBytes(ByteBuf buf) {
        type = EventType.fromInt(buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(type.toInt());
    }

    public static class Handler implements IMessageHandler<MessageServerEvent, IMessage> {

        @Override
        public IMessage onMessage(MessageServerEvent message, MessageContext ctx) {
            if(message.type == EventType.CONNECT) ClientCastingManager.onPlayerConnectsToServer();
            if(message.type == EventType.DISCONNECT) ClientCastingManager.onPlayerDisconnectsFromServer();

            System.out.println(message.type.toInt());

            // Nothing to reply
            return null;
        }
    }
}
