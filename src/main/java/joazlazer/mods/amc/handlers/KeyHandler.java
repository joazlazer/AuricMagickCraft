package joazlazer.mods.amc.handlers;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.api.order.OrderBase;
import joazlazer.mods.amc.client.gui.GuiSpellSelectionOverlay;
import joazlazer.mods.amc.network.MessageCastingControl;
import joazlazer.mods.amc.reference.GuiId;
import joazlazer.mods.amc.util.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.client.event.MouseEvent;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class KeyHandler {

    /*
    ************************************
    * KEY BINDING INSTANCES
    * **********************************
    */

    public static class DummyKeyBinding {

        public int key;

        public DummyKeyBinding(int newKey) {
            key = newKey;
        }

        public void setKeyCode(int newCode) {
            key = newCode;
        }

        public int getKeyCode() {
            return key;
        }
    }

    public static KeyBinding techTreeOpen = new KeyBinding("key.amc.techTreeOpen", Keyboard.KEY_I, "key.categories.amc");
    public static DummyKeyBinding spellCast = new DummyKeyBinding(-99); // Default right click.
    public static DummyKeyBinding cancelSpellCastMod = new DummyKeyBinding(Keyboard.KEY_LSHIFT);
    public static DummyKeyBinding switchSpellKey = new DummyKeyBinding(Keyboard.KEY_Q);
    public static DummyKeyBinding adj1 = new DummyKeyBinding(Keyboard.KEY_LEFT);
    public static DummyKeyBinding adj2 = new DummyKeyBinding(Keyboard.KEY_RIGHT);
    public static DummyKeyBinding adj3 = new DummyKeyBinding(Keyboard.KEY_UP);
    public static DummyKeyBinding adj4 = new DummyKeyBinding(Keyboard.KEY_DOWN);
    public static Minecraft mc;
    public static boolean moving;

    public static void registerKeyHandlers() {
        // Register the key bindings in ClientRegistry.
        ClientRegistry.registerKeyBinding(techTreeOpen);
        mc = Minecraft.getMinecraft();
    }

    @SubscribeEvent
    public void handleKeyInput(InputEvent.KeyInputEvent e) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityClientPlayerMP player = mc.thePlayer;
        World world = mc.theWorld;
        int x = (int) player.posX;
        int y = (int) player.posY;
        int z = (int) player.posZ;

        if (techTreeOpen.isPressed()) {
            if (AuricMagickCraft.PlayerTracker.isAwake(player.getDisplayName())) {
                player.openGui(AuricMagickCraft.instance, GuiId.TECH_TREE.ordinal(), world, x, y, z);
                AuricMagickCraft.PlayerTracker.awaken(player, new OrderBase() {
                    @Override
                    public String getUnlocName() {
                        return "mahnameisbahb";
                    }
                });
            } else player.addChatMessage(new ChatComponentText(
                    ("§%cSeems §%chumanity §%chas §%clost §%cits §%cability §%cto §%cunderstand §%cmagic." +
                            " §%cMaybe §%cif §%cI §%cuse §%can §a[Awakening Table]§%c...").replaceAll("%c", "9")));
        } else if (isKeyDown(cancelSpellCastMod) && isKeyDown(switchSpellKey)) {
            RenderHandler.spellSelection.nextSpell();
        }


    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        exportQueueHeartbeat();

        if (isKeyDown(spellCast)) {
            if (isKeyDown(cancelSpellCastMod) && !holdingSpellCastCancel) {
                notifyServerCastCancel();
                holdingSpellCastCancel = true;
                duringSpellCast = false;
                justCanceled = true;
                return;
            }

            if (!holdingSpellCast && !justCanceled) {
                notifyServerCastStart();
                holdingSpellCast = true;
                duringSpellCast = true;
            }
        } else {
            if (holdingSpellCast) {
                if (duringSpellCast) {
                    notifyServerCastStop();
                }
                holdingSpellCast = false;
                duringSpellCast = false;
            }
            justCanceled = false;
        }

        if (!isKeyDown(cancelSpellCastMod)) holdingSpellCastCancel = false;
        GameSettings gs = Minecraft.getMinecraft().gameSettings;
        if(!gs.keyBindForward.getIsKeyPressed() && !gs.keyBindBack.getIsKeyPressed() && !gs.keyBindLeft.getIsKeyPressed() && !gs.keyBindRight.getIsKeyPressed() && !gs.keyBindJump.getIsKeyPressed() && !gs.keyBindSneak.getIsKeyPressed()) moving = false;
        else moving = true;
        System.out.println(moving);
    }

    public static void notifyServerCastCancel() {
        if (mc.theWorld != null && mc.thePlayer != null) {
            enqueueMessage(MessageCastingControl.ControlType.CANCEL);
            System.out.println("notifyServerCastCancel");
        }
    }

    public static void notifyServerCastStop() {
        if (mc.theWorld != null && mc.thePlayer != null) {
            enqueueMessage(MessageCastingControl.ControlType.STOP);
            System.out.println("notifyServerCastStop");
        }
    }

    public static void notifyServerCastStart() {
        if (mc.theWorld != null && mc.thePlayer != null && mc.thePlayer.getHeldItem() == null) {
            enqueueMessage(MessageCastingControl.ControlType.START);
            System.out.println("notifyServerCastStart");
        }
    }

    public static boolean holdingSpellCast;
    public static boolean holdingSpellCastCancel;
    public static boolean duringSpellCast;
    public static boolean justCanceled;

    public static boolean isKeyDown(DummyKeyBinding bind) {
        return (bind.getKeyCode() != 0) && ((bind.getKeyCode() < 0) ? Mouse.isButtonDown(bind.getKeyCode() + 100) : Keyboard.isKeyDown(bind.getKeyCode()));
    }

    public static class QueueItem {
        public static final int TICK_DELAY = 8;
        public int ticksRemaining;
        public MessageCastingControl.ControlType type;

        public QueueItem(MessageCastingControl.ControlType type) {
            this.type = type;
            ticksRemaining = TICK_DELAY;
        }
    }

    public static ArrayList<QueueItem> queue = new ArrayList<QueueItem>();

    public void exportQueueHeartbeat() {
        if (queue.isEmpty()) return;
        ArrayList<Integer> idsToRemove = new ArrayList<Integer>();
        for (QueueItem item : queue) {
            item.ticksRemaining--;
            if (item.ticksRemaining <= 0) {
                idsToRemove.add(queue.indexOf(item));
                exportAndShip(item.type);
            }
        }

        for (int i : idsToRemove) {
            queue.remove(i);
        }
    }

    public void exportAndShip(MessageCastingControl.ControlType type) {
        NetworkHandler.Network.sendToServer(new MessageCastingControl(type, Minecraft.getMinecraft().thePlayer.getDisplayName()));
    }

    public static void enqueueMessage(MessageCastingControl.ControlType controlType) {
        queue.add(new QueueItem(controlType));
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void interceptMouseInput(MouseEvent event) {
        if (event.dwheel == 0) return;
        if (Minecraft.getMinecraft().theWorld != null) {
            System.out.println(event.dwheel);
            if(Minecraft.getMinecraft().thePlayer.isSneaking() && Minecraft.getMinecraft().thePlayer.getHeldItem() == null) {
                event.setCanceled(true);
                if(event.dwheel < 0) {
                    GuiSpellSelectionOverlay.previousSpell();
                } else GuiSpellSelectionOverlay.nextSpell();
            }
        }
    }
}
