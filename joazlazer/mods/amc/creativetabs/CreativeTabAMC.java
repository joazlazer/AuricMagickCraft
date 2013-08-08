package joazlazer.mods.amc.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.block.ModBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTabAMC extends CreativeTabs
{
    public CreativeTabAMC(int par1, String par2Str) 
    {
        super(par1, par2Str);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack() 
    {
    	// Return the item id for the basic wand.
        return new ItemStack(ModBlocks.BLOCKS.awakeningTable, 1, 0);
    }
}
