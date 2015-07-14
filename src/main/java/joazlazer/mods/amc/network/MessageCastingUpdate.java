package joazlazer.mods.amc.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import joazlazer.mods.amc.casting.CastingStatus;
import joazlazer.mods.amc.casting.client.ClientCastingManager;
import joazlazer.mods.amc.casting.client.ClientCastingStatus;
import net.minecraft.nbt.NBTTagCompound;

public class MessageCastingUpdate implements IMessage {
    public CastingStatus status;
    public String username;
    public ClientCastingStatus ccstatus;

    public MessageCastingUpdate(CastingStatus cstatus, String username) {
        this.username = username;
        status = cstatus;
    }

    public MessageCastingUpdate() {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        NBTTagCompound nbt = ByteBufUtils.readTag(buf);
        if (nbt == null) return;
        String username = nbt.getString("username");
        ClientCastingStatus status = new ClientCastingStatus(username);
        status.readFromNBT(nbt);

        ccstatus = status;
        this.username = username;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        String str = username;
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("username", str);
        status.writeToNBT(nbt);

        ByteBufUtils.writeTag(buf, nbt);
        System.out.println(nbt.toString());
    }

    public static class Handler implements IMessageHandler<MessageCastingUpdate, IMessage> {
        @Override
        public IMessage onMessage(MessageCastingUpdate message, MessageContext ctx) {
            ClientCastingManager.PlayerCasts.put(message.username, message.ccstatus);
            System.out.println("Recieved with stage " + message.ccstatus.customNBT.getByte("stage"));
            return null;
        }
    }
}
