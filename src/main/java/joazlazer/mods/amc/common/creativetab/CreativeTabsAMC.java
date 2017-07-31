package joazlazer.mods.amc.common.creativetab;

import joazlazer.mods.amc.common.block.ModBlocks;
import joazlazer.mods.amc.common.reference.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabsAMC {
    public static final CreativeTabs AMC_TAB = new CreativeTabs(Reference.MOD_ID) {

        @SideOnly(Side.CLIENT)
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(Item.getItemFromBlock(ModBlocks.AWAKENING_TABLE));
        }

        @Override
        public String getTranslatedTabLabel() {
            return I18n.format("itemGroup.amc");
        }
    };
}
