package joazlazer.mods.amc.common.handlers;

import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.api.capability.PlayerCapabilityProvider;
import joazlazer.mods.amc.api.capability.progression.CapabilityAmcProgressionHandler;
import joazlazer.mods.amc.common.reference.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityHandler {
    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> e) {
        if (e.getObject() instanceof EntityPlayer) {
            e.addCapability(new ResourceLocation(Reference.MOD_ID, "playerCapabilitiesBundle"), new PlayerCapabilityProvider());
        }
    }

    public void register() {
        CapabilityAmcProgressionHandler.register();
    }
}
