package joazlazer.mods.amc.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiAmcIngame {
    public GuiAmcIngame() {
        this.mc = Minecraft.getMinecraft();
    }
    Minecraft mc;
    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Pre event){
        if(event.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS && mc.currentScreen instanceof GuiAwakeningScreen) {
            if(((GuiAwakeningScreen)mc.currentScreen).shouldHideCrosshairs()) event.setCanceled(true);
        }
    }
}
