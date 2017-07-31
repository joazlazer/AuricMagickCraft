package joazlazer.mods.amc.client.render.item;

import joazlazer.mods.amc.common.item.ModItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ModItemRenderers {
    @SubscribeEvent
    public static void registerItemRenderers(ModelRegistryEvent e) {
        register(ModItems.CHRYSOPRASE_GEM, "amc:chrysoprase_gem");
    }

    public static void register(Item item, String modelLoc) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(modelLoc, "inventory"));
    }
}
