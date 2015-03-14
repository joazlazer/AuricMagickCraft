package joazlazer.mods.amc.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class MessageCastingDataRequest implements IMessage {

    public MessageCastingDataRequest() {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        // Nothing; just a request packet.
        System.out.println("Recieved data request on server");
    }

    @Override
    public void toBytes(ByteBuf buf) {
        // Nothing; just a request packet.
        System.out.println("Launching soon");
    }

    public static class Handler implements IMessageHandler<MessageCastingDataRequest, IMessage> {
        @Override
        public IMessage onMessage(MessageCastingDataRequest message, MessageContext ctx) {
            System.out.println("Processing");
            return new MessageCastingDataReply();
        }
    }
}
