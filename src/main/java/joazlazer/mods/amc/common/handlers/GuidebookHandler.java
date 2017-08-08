package joazlazer.mods.amc.common.handlers;

import gigaherz.guidebook.client.BookRegistryEvent;
import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.common.reference.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = AuricMagickCraft.MODID)
public class GuidebookHandler {
    @Optional.Method(modid="gbook")
    @SubscribeEvent
    public static void registerBook(BookRegistryEvent event) {
        event.register(new ResourceLocation(Reference.MOD_ID + ":xml/guidebook.xml"));
    }
}
