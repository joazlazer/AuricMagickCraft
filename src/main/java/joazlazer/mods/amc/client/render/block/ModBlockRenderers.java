package joazlazer.mods.amc.client.render.block;

import joazlazer.mods.amc.common.block.BlockAwakeningTable;
import joazlazer.mods.amc.common.block.ModBlocks;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ModBlockRenderers {
    @SubscribeEvent
    public static void registerItemRenderers(ModelRegistryEvent e) {
        ((BlockAwakeningTable)ModBlocks.AWAKENING_TABLE).initModel();
    }
}
