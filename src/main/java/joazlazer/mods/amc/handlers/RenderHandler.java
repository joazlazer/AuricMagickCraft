package joazlazer.mods.amc.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import joazlazer.mods.amc.client.gui.GuiSpellSelectionOverlay;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class RenderHandler {
    public static GuiSpellSelectionOverlay spellSelection = new GuiSpellSelectionOverlay();

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if(!event.isCancelable() && event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
            Minecraft mc = Minecraft.getMinecraft();
            spellSelection.render(event);
        }
    }

    //Called when the client ticks.
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if(Minecraft.getMinecraft().theWorld != null) {
            spellSelection.update(event);
        }
    }
}
