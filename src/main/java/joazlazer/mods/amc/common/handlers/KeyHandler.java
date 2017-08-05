package joazlazer.mods.amc.common.handlers;

import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.client.casting.ClientCastingManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class KeyHandler {
    public static DummyKeyBinding spellCast = new DummyKeyBinding(-99); // Default right click.
    public static DummyKeyBinding adj1 = new DummyKeyBinding(Keyboard.KEY_LEFT);
    public static DummyKeyBinding adj2 = new DummyKeyBinding(Keyboard.KEY_RIGHT);
    public static DummyKeyBinding adj3 = new DummyKeyBinding(Keyboard.KEY_UP);
    public static DummyKeyBinding adj4 = new DummyKeyBinding(Keyboard.KEY_DOWN);

    @SubscribeEvent
    public void handleKeyInput(InputEvent.KeyInputEvent e) {

    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (isKeyDown(spellCast)) {
            // cast spell
            if(!ClientCastingManager.getInstance().isCasting()) {
                ClientCastingManager.getInstance().startCasting();
            }
        } else {
            if(ClientCastingManager.getInstance().isCasting()) {
                ClientCastingManager.getInstance().stopCasting();
            }
        }
    }

    public static boolean isKeyDown(DummyKeyBinding bind) {
        return (bind.getKeyCode() != 0) && ((bind.getKeyCode() < 0) ? Mouse.isButtonDown(bind.getKeyCode() + 100) : Keyboard.isKeyDown(bind.getKeyCode()));
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void interceptMouseInput(MouseEvent event) {
        if (event.getDwheel() == 0) return;
        if (Minecraft.getMinecraft().world != null) {
            if(Minecraft.getMinecraft().player.isSneaking() && Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND) == null) {
                AuricMagickCraft.logger.info(String.format("MWheel event intercepted [getDwheel():%d], change spell", event.getDwheel()));
                event.setCanceled(true);
                // TODO spell change & stuff
            }
        }
    }

    public static class DummyKeyBinding {
        private int key;

        public DummyKeyBinding(int newKey) {
            key = newKey;
        }

        public int getKeyCode() {
            return key;
        }
    }
}
