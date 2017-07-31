package joazlazer.mods.amc.common.block;

import joazlazer.mods.amc.common.reference.Reference;
import joazlazer.mods.amc.common.tileentity.TileEntityAwakeningTable;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashSet;
import java.util.Set;

public class ModBlocks {
    @GameRegistry.ObjectHolder("amc:awakening_table")
    public static final Block AWAKENING_TABLE = null;

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class RegistrationHandler {
        public static final Set<Block> BLOCKS = new HashSet<>();

        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> event) {
            final Block[] blocks = {
                    new BlockAwakeningTable()
            };

            final IForgeRegistry<Block> registry = event.getRegistry();
            for (final Block block : blocks) {
                registry.register(block);
                BLOCKS.add(block);
            }

            GameRegistry.registerTileEntity(TileEntityAwakeningTable.class, Reference.MOD_ID + ":awakening_table");
        }
    }
}
