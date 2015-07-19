package joazlazer.mods.amccore;

import joazlazer.mods.amc.sevencommons.internal.transformers.VisitorBasedTransformer;
import joazlazer.mods.amccore.tweaks.IntegratedServerPauseFix;

public class AMCCCVisitorTransformerWrapper extends VisitorBasedTransformer {
    @Override
    protected void addEntries() {
        // Only save the game if the game was paused by [Esc]
        addEntry(IntegratedServerPauseFix.class, "net/minecraft/server/integrated/IntegratedServer");
    }
}
