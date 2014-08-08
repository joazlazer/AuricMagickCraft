package joazlazer.mods.amc.creativetab;

import joazlazer.mods.amc.block.ModBlocks;
import joazlazer.mods.amc.reference.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;

public class CreativeTabAMC {
    public static final CreativeTabs AMC_TAB = new CreativeTabs(Reference.MOD_ID) {

        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(ModBlocks.BLOCKS.awakeningTable);
        }

        @Override
        public String getTranslatedTabLabel() {
            return StatCollector.translateToLocal("tab.amc");
        }
    };
}
