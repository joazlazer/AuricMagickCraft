package joazlazer.mods.amc.handlers;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.api.order.OrderBase;
import joazlazer.mods.amc.reference.GuiId;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public class KeyHandler {

    /*
    ************************************
    * KEY BINDING INSTANCES
    * **********************************
    */

    public static KeyBinding techTreeOpen = new KeyBinding("key.amc.techTreeOpen", Keyboard.KEY_I, "key.categories.amc");

    public static void registerKeyHandlers() {
        // Register the key bindings in ClientRegistry.
        ClientRegistry.registerKeyBinding(techTreeOpen);
    }

    @SubscribeEvent
    public void handleKeyInput(InputEvent.KeyInputEvent e) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityClientPlayerMP player = mc.thePlayer;
        World world = mc.theWorld;
        int x = (int) player.posX;
        int y = (int) player.posY;
        int z = (int) player.posZ;


        if(techTreeOpen.isPressed()) {
            if(true/*AuricMagickCraft.PlayerTracker.isAwake(player.getDisplayName())*/) {
              player.openGui(AuricMagickCraft.instance, GuiId.TECH_TREE.ordinal(), world, x, y, z);
                AuricMagickCraft.PlayerTracker.awaken(player, new OrderBase() { @Override public String getUnlocName() { return "mahnameisbahb"; } });
            }
            else player.addChatMessage(new ChatComponentText(
                    ("§%cSeems §%chumanity §%chas §%clost §%cits §%cability §%cto §%cunderstand §%cmagic." +
                     " §%cMaybe §%cif §%cI §%cuse §%can §a[Awakening Table]§%c...").replaceAll("%c", "9")));
         }
    }
}
