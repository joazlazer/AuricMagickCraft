package joazlazer.mods.amc.network;

import com.sun.deploy.util.SessionState;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import joazlazer.mods.amc.casting.CastingManager;
import joazlazer.mods.amc.casting.CastingStatus;
import joazlazer.mods.amc.casting.client.ClientCastingManager;
import joazlazer.mods.amc.casting.client.ClientCastingStatus;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;

public class MessageCastingDataReply implements IMessage {

    public HashMap<String, ClientCastingStatus> received = new HashMap<String, ClientCastingStatus>();

    public MessageCastingDataReply() {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        NBTTagCompound wholeTag = ByteBufUtils.readTag(buf);
        if(wholeTag == null) return;
        int count = wholeTag.getInteger("count");
        for(int i = 0; i < count; i++) {
            NBTTagCompound nbt = wholeTag.getCompoundTag(Integer.toString(i));
            if(nbt != null) {
                String username = nbt.getString("username");
                ClientCastingStatus status = new ClientCastingStatus(username);
                status.readFromNBT(nbt);
                received.put(username, status);
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        HashMap<String, CastingStatus> stats = CastingManager.PlayerCasts;
        NBTTagCompound wholeTag = new NBTTagCompound();
        wholeTag.setInteger("count", stats.size());
        for(int i = 0; i < stats.size(); i++) {
            String str = (String)stats.keySet().toArray()[i];
            CastingStatus status = stats.get(str);
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("username", str);
            status.writeToNBT(nbt);
            wholeTag.setTag(Integer.toString(i), nbt);
        }

        ByteBufUtils.writeTag(buf, wholeTag);
    }

    public static class Handler implements IMessageHandler<MessageCastingDataReply, IMessage> {
        @Override
        public IMessage onMessage(MessageCastingDataReply message, MessageContext ctx) {
            // Pass on the data to the client casting manager.
            ClientCastingManager.receiveCastingData(message.received);

            // No packet needed for reply.
            return null;
        }
    }
}
