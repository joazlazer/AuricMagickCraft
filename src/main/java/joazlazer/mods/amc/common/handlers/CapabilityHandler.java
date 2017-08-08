package joazlazer.mods.amc.common.handlers;

import joazlazer.mods.amc.api.capability.PlayerCapabilityProvider;
import joazlazer.mods.amc.api.capability.aura.CapabilityAuraHandler;
import joazlazer.mods.amc.api.capability.aura.EntityAuraHandler;
import joazlazer.mods.amc.api.capability.progression.CapabilityAmcProgressionHandler;
import joazlazer.mods.amc.api.capability.progression.EntityAmcProgressionHandler;
import joazlazer.mods.amc.common.reference.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityHandler {
    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> e) {
        if (e.getObject() instanceof EntityPlayer) {
            e.addCapability(new ResourceLocation(Reference.MOD_ID, "playerCapabilitiesBundle"), new PlayerCapabilityProvider());
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        if(event.getOriginal().hasCapability(CapabilityAmcProgressionHandler.AMC_PROGRESSION_HANDLER, null) && event.getEntityPlayer().hasCapability(CapabilityAmcProgressionHandler.AMC_PROGRESSION_HANDLER, null)) {
            NBTBase amcProgressionNBT = ((EntityAmcProgressionHandler)event.getOriginal().getCapability(CapabilityAmcProgressionHandler.AMC_PROGRESSION_HANDLER, null)).serializeNBT();
            NBTBase auraHandlerNBT = ((EntityAuraHandler)event.getOriginal().getCapability(CapabilityAuraHandler.AURA_HANDLER, null)).serializeNBT();

            ((EntityAmcProgressionHandler)event.getEntityPlayer().getCapability(CapabilityAmcProgressionHandler.AMC_PROGRESSION_HANDLER, null)).deserializeNBT(amcProgressionNBT);
            ((EntityAuraHandler)event.getEntityPlayer().getCapability(CapabilityAuraHandler.AURA_HANDLER, null)).deserializeNBT(auraHandlerNBT);
        }
    }

    public void register() {
        CapabilityAmcProgressionHandler.register();
        CapabilityAuraHandler.register();
        MinecraftForge.EVENT_BUS.register(this);
    }
}
