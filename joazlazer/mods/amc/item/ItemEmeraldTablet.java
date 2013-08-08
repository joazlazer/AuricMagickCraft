package joazlazer.mods.amc.item;

import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.lib.ItemIds;
import joazlazer.mods.amc.lib.Strings;

public class ItemEmeraldTablet extends ItemAmc
{
    public ItemEmeraldTablet()
    {
        super(ItemIds.EMERALD_TABLET);
        this.func_111206_d("AMC:EmeraldTablet");
        this.setNoRepair();
        this.setCreativeTab(AuricMagickCraft.tabsAMC);
        this.setUnlocalizedName(Strings.EMERALD_TABLET_NAME);
    }
}
