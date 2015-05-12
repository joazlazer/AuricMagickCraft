package joazlazer.mods.amc.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.entity.player.PlayerData;
import joazlazer.mods.amc.entity.player.PlayerDataFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class MessagePlayerTrackerUpdate implements IMessage {

    public Object arg;
    public String username;
    public String id;
    public String type;

    public MessagePlayerTrackerUpdate() {

    }

    public MessagePlayerTrackerUpdate(String username, String id, Object arg) {
        this.username = username;
        this.id = id;
        this.arg = arg;
        System.out.println("INITIALIZED PACKET WITH: { username=" + username + ", id=" + id +  ", arg=" + arg + " }");
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        username = ByteBufUtils.readUTF8String(buf);
        id = ByteBufUtils.readUTF8String(buf);
        type = ByteBufUtils.readUTF8String(buf);
        if(type.equalsIgnoreCase("string")) {
            arg = ByteBufUtils.readUTF8String(buf);
        }
        else if(type.equalsIgnoreCase("boolean")) {
            arg = buf.readBoolean();
        }
        else if(type.equalsIgnoreCase("integer")) {
            arg = buf.readInt();
        }
        System.out.println("RECEIVED PACKET WITH: { username=" + username + ", id=" + id +  ", arg=" + arg + ", type=" + type + " }");
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, username);
        ByteBufUtils.writeUTF8String(buf, id);
        if (arg instanceof String) {
            ByteBufUtils.writeUTF8String(buf, "string");
            ByteBufUtils.writeUTF8String(buf, (String) arg);
            System.out.println("SENT PACKET WITH: { username=" + username + ", id=" + id +  ", arg=" + arg + ", type=string }");
        }
        else if (arg instanceof Boolean) {
            ByteBufUtils.writeUTF8String(buf, "boolean");
            buf.writeBoolean((Boolean) arg);
            System.out.println("SENT PACKET WITH: { username=" + username + ", id=" + id +  ", arg=" + arg + ", type=boolean }");
        }
        else if (arg instanceof Integer) {
            ByteBufUtils.writeUTF8String(buf, "integer");
            buf.writeInt((Integer) arg);
            System.out.println("SENT PACKET WITH: { username=" + username + ", id=" + id +  ", arg=" + arg + ", type=integer }");
        }

}

    public static class Handler implements IMessageHandler<MessagePlayerTrackerUpdate, IMessage> {

        @Override
        public IMessage onMessage(MessagePlayerTrackerUpdate message, MessageContext ctx) {
            World world = FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT ? ClientWrapper.getTheWorld() : MinecraftServer.getServer().getEntityWorld();
            EntityPlayer player = world.getPlayerEntityByName(message.username);
            if(!AuricMagickCraft.PlayerTracker.hasData(player)) {
                PlayerData data = PlayerDataFactory.newData(player);
                AuricMagickCraft.PlayerTracker.playerStats.put(message.username, data);
            }

            try {
                if(message.type.equalsIgnoreCase("string")) {
                    AuricMagickCraft.PlayerTracker.getData(message.username).setString(message.id, (String) message.arg);
                }
                else if(message.type.equalsIgnoreCase("boolean")) {
                    AuricMagickCraft.PlayerTracker.getData(message.username).setBoolean(message.id, (Boolean) message.arg);
                }
                else if(message.type.equalsIgnoreCase("integer")) {
                    AuricMagickCraft.PlayerTracker.getData(message.username).setBoolean(message.id, (Boolean) message.arg);
                }
            }
            catch(Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }

            System.out.println("HANDLER SET NEW STATS FOR: username=" + message.username + " Stat ID:" + message.id + " New Value: " + message.arg);
            return null;
        }
    }
}
