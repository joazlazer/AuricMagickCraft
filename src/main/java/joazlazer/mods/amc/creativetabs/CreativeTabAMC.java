package joazlazer.mods.amc.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTabAMC extends CreativeTabs {

	public CreativeTabAMC(int par1, String par2Str) {
		super(par1, par2Str);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		
		// Return the item id for a blaze rod (temp).
		// TODO Change this once blocks/items have been made!
		return Items.blaze_rod;
	}
}
