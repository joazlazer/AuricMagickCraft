package joazlazer.mods.amc.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import joazlazer.mods.amc.casting.CastingManager;

public class MessageCastingControl implements IMessage {
    public enum ControlType {
        START,
        STOP,
        CANCEL,
        INTERRUPT;
        public static ControlType fromValue(int value) {
            return ControlType.values()[value];
        }
    }

    public ControlType type;
    public String username;

    public MessageCastingControl() {
        // Provide this for the reflection used in MCF/FML3
    }

    public MessageCastingControl(ControlType type, String user) {
        this.type = type;
        this.username = user;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int typeInt = buf.readInt();
        this.type = typeInt == 0 ? ControlType.START : typeInt == 1 ? ControlType.STOP : typeInt == 2 ? ControlType.CANCEL : ControlType.INTERRUPT;
        this.username = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.type.ordinal());
        ByteBufUtils.writeUTF8String(buf, username);
    }

    public static class Handler implements IMessageHandler<MessageCastingControl, IMessage> {

        @Override
        public IMessage onMessage(MessageCastingControl message, MessageContext ctx) {
            CastingManager.onCastControlPacket(message.type, message.username);
            return null;
        }
    }
}
