package joazlazer.mods.amc.common.handlers;

import joazlazer.mods.amc.client.gui.GuiAwakeningScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiHandler {
    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent e) {
        // Cancel close of GuiAwakeningScreen on teleport
        if(e.getGui() == null && Minecraft.getMinecraft().currentScreen instanceof GuiAwakeningScreen && !((GuiAwakeningScreen)Minecraft.getMinecraft().currentScreen).canClose) e.setCanceled(true);
    }
}
