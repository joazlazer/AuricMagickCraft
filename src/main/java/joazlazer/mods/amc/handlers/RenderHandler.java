package joazlazer.mods.amc.handlers;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.api.spell.SpellBase;
import joazlazer.mods.amc.api.spell.SpellRegistry;
import joazlazer.mods.amc.casting.client.ClientCastingManager;
import joazlazer.mods.amc.client.gui.GuiSpellSelectionOverlay;
import joazlazer.mods.amc.util.exception.MapNoContainsKeyException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldEvent;

public class RenderHandler {
    public static GuiSpellSelectionOverlay spellSelection = new GuiSpellSelectionOverlay();

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (!event.isCancelable() && event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
            Minecraft mc = Minecraft.getMinecraft();
            spellSelection.render(event);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
        if(Minecraft.getMinecraft().theWorld == null) return;
        try {
            if (player.getHeldItem() == null && AuricMagickCraft.PlayerTracker.isAwake(player.getDisplayName()) && !(AuricMagickCraft.PlayerTracker.getData(player).getString("selectedSpellName").isEmpty()) && AuricMagickCraft.PlayerTracker.getData(player).getString("selectedSpellName") != null) {
                Minecraft mc = Minecraft.getMinecraft();
                SpellBase spell = SpellRegistry.getSpell(AuricMagickCraft.PlayerTracker.getData(player).getString("selectedSpellName"));
                switch (mc.gameSettings.thirdPersonView) {
                    case 0: {
                        spell.renderFirstPerson(Minecraft.getMinecraft().theWorld, event.renderTickTime, ClientCastingManager.PlayerCasts.get(player.getDisplayName()));
                        break;
                    }
                    case 1:
                    case 2: {
                        spell.renderThirdPerson(Minecraft.getMinecraft().theWorld, event.renderTickTime, ClientCastingManager.PlayerCasts.get(player.getDisplayName()));
                        break;
                    }
                    default: {
                        return;
                    }
                }
            }
        } catch (MapNoContainsKeyException e) {
            e.printStackTrace();
        }
    }

    //Called when the client ticks.
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().theWorld != null) {
            spellSelection.update(event);
        }
    }
}
