package joazlazer.mods.amc.common.item;

import joazlazer.mods.amc.common.block.ModBlocks;
import joazlazer.mods.amc.common.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {
    @GameRegistry.ObjectHolder("amc:chrysoprase_gem")
    public static final Item CHRYSOPRASE_GEM = null;

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event) {
            final Item[] items = {
                    new ItemChrysopraseGem()
            };

            final IForgeRegistry<Item> registry = event.getRegistry();
            for (final Item item : items) {
                registry.register(item);
            }

            // Register ItemBlocks
            for(final Block block : ModBlocks.RegistrationHandler.BLOCKS) {
                event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
            }
        }
    }
}