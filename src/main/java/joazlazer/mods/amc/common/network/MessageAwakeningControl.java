package joazlazer.mods.amc.common.network;

import io.netty.buffer.ByteBuf;
import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.api.capability.aura.CapabilityAuraHandler;
import joazlazer.mods.amc.api.capability.aura.IAuraHandler;
import joazlazer.mods.amc.api.capability.progression.CapabilityAmcProgressionHandler;
import joazlazer.mods.amc.api.capability.progression.IAmcProgressionHandler;
import joazlazer.mods.amc.api.order.OrderBase;
import joazlazer.mods.amc.client.gui.GuiAwakeningScreen;
import joazlazer.mods.amc.client.gui.GuiAwakeningTable;
import joazlazer.mods.amc.common.utility.EntityUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
* This class implements the packets necessary for the Awakening ritual
* Upon opening the GUI, the client sends a CAN_AWAKEN packet, and gets a REPLY packet sent from the server with the answer (canAwaken)
* Upon activating the Awakening ritual via the GUI, the client sends a START packet
* After the ritual has completed, the client sends an END packet with the new order, and gets a FINALIZE packet sent from the server telling it to close GuiAwakeningScreen
*/
public class MessageAwakeningControl implements IMessage {
    public enum ControlType {
        CAN_AWAKEN,
        REPLY,
        START,
        END,
        FINALIZE
    }

    public MessageAwakeningControl() { }
    public MessageAwakeningControl(ControlType type) {
        this.type = type;
    }
    public MessageAwakeningControl(ControlType type, OrderBase order) {
        this.type = type;
        this.orderBase = order;
    }
    public MessageAwakeningControl(ControlType type, OrderBase order, boolean bool) {
        this.type = type;
        this.orderBase = order;
        this.canAwaken = bool;
    }


    private ControlType type;
    private OrderBase orderBase;
    private boolean canAwaken;

    @Override
    public void fromBytes(ByteBuf buf) {
        int ordinal = buf.readByte();
        this.type = MessageAwakeningControl.ControlType.values()[ordinal];
        if(type == ControlType.END) {
            String domain = ByteBufUtils.readUTF8String(buf);
            String path = ByteBufUtils.readUTF8String(buf);
            ResourceLocation resourceLocation = new ResourceLocation(domain, path);
            OrderBase order = OrderBase.registry.getValue(resourceLocation);
            this.orderBase = order;
        } else if(type == ControlType.REPLY) {
            this.canAwaken = buf.readBoolean();
            String domain = ByteBufUtils.readUTF8String(buf);
            String path = ByteBufUtils.readUTF8String(buf);
            ResourceLocation resourceLocation = new ResourceLocation(domain, path);
            OrderBase order = (OrderBase.registry.containsKey(resourceLocation)) ? OrderBase.registry.getValue(resourceLocation) : null;
            this.orderBase = order;
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(type.ordinal());
        if(type == ControlType.END) {
            ByteBufUtils.writeUTF8String(buf, orderBase.getRegistryName().getResourceDomain());
            ByteBufUtils.writeUTF8String(buf, orderBase.getRegistryName().getResourcePath());
        } else if(type == ControlType.REPLY) {
            buf.writeBoolean(this.canAwaken);
            ByteBufUtils.writeUTF8String(buf, (orderBase != null) ? orderBase.getRegistryName().getResourceDomain() : "null");
            ByteBufUtils.writeUTF8String(buf, (orderBase != null) ? orderBase.getRegistryName().getResourcePath() : "null");
        }
    }

    public static class MessageAwakeningControlHandler implements IMessageHandler<MessageAwakeningControl, IMessage> {
        @Override
        public IMessage onMessage(MessageAwakeningControl message, MessageContext ctx) {
            if(message.type == ControlType.FINALIZE || message.type == ControlType.REPLY) {
                // Client-side
                GuiScreen currentScreen = Minecraft.getMinecraft().currentScreen;
                if(message.type == ControlType.FINALIZE) {
                    if(currentScreen instanceof GuiAwakeningScreen) {
                        Minecraft.getMinecraft().addScheduledTask(((GuiAwakeningScreen) currentScreen)::finalizeAwakening);
                    }
                } else if(message.type == ControlType.REPLY) {
                    if(currentScreen instanceof GuiAwakeningTable && currentScreen != null) {
                        ((GuiAwakeningTable)currentScreen).canAwaken = message.canAwaken;
                        ((GuiAwakeningTable)currentScreen).currentPlayerOrder = message.orderBase;
                    }
                    return null;
                }
                return null;
            } else {
                // Server-side
                final EntityPlayerMP player = ctx.getServerHandler().player;
                final IAmcProgressionHandler amcProgressionHandler = player.getCapability(CapabilityAmcProgressionHandler.AMC_PROGRESSION_HANDLER, null);
                if(message.type == ControlType.CAN_AWAKEN) {
                    boolean canAwaken = false;
                    OrderBase order = null;
                    if(amcProgressionHandler != null) {
                        canAwaken = !amcProgressionHandler.getIsAwakened();
                        order = amcProgressionHandler.getOrder();
                    }
                    return new MessageAwakeningControl(ControlType.REPLY, order, canAwaken);
                } else if(message.type == ControlType.START) {
                    // TODO I want to set player invulnerable: how to prevent exploitation? Container check?
                } else if(message.type == ControlType.END) {
                    final IAuraHandler auraHandler = player.getCapability(CapabilityAuraHandler.AURA_HANDLER, null);
                    player.getServerWorld().addScheduledTask(() -> {
                        if(amcProgressionHandler != null && auraHandler != null) {
                            amcProgressionHandler.setAwakened(true);
                            auraHandler.setAuraUnits(auraHandler.getMaxAuraUnits() / 4f);
                            if(EntityUtils.respawnPlayerToBed(player)) {
                                // Respawned to bed
                                player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("nausea"), 36));

                            } else {
                                // Did not return to bed
                                player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("nausea"), 100));
                            }
                            if(message.orderBase != null) amcProgressionHandler.setOrder(message.orderBase);
                            // TODO Spawn Guidebook
                            else AuricMagickCraft.logger.error(String.format("[MessageAwakeningEndHandler.onMessage(...)] Received invalid order message from Player '%s'.", player.getDisplayNameString()));
                        }
                        else AuricMagickCraft.logger.error(String.format("[MessageAwakeningEndHandler.onMessage(...)] Player '%s' does not have full AMC Player capability data (this should have been attached on spawn).", player.getDisplayNameString()));
                    });
                    return new MessageAwakeningControl(ControlType.FINALIZE);
                }
                return null;
            }
        }
    }
}
