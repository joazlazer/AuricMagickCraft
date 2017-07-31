package joazlazer.mods.amc.common.item;

import joazlazer.mods.amc.common.creativetab.CreativeTabsAMC;
import joazlazer.mods.amc.common.reference.Reference;
import net.minecraft.item.Item;

public class ItemAMC extends Item {
    public ItemAMC(final String itemName) {
        setItemName(this, itemName);
        setCreativeTab(CreativeTabsAMC.AMC_TAB);
    }

    public static void setItemName(final Item item, final String itemName) {
        item.setRegistryName(Reference.MOD_ID, itemName);
        item.setUnlocalizedName(item.getRegistryName().toString());
    }
}
