package joazlazer.mods.amc.spells;

import java.util.ArrayList;

import joazlazer.mods.amc.client.gui.GuiTextFormat;
import joazlazer.mods.amc.lib.Textures;

public class SpellBolt extends SpellBase{
	public SpellBolt()
	{
		super();
		this.setUnlocName("basicBolt");
		ArrayList<String> tooltip = new ArrayList<String>();
		tooltip.add(GuiTextFormat.LIGHTBLUE + "Basic Auric Bolt");
		tooltip.add("This spell casts a small bolt that");
		tooltip.add("deals 1-20 damage depending on cast time.");
		tooltip.add("");
		tooltip.add(GuiTextFormat.YELLOW + "Generic");
		this.setTooltip(tooltip);
		this.setTextureLocation(Textures.SPELLS.SPELLS_SPRITESHEET);
		this.setX(0);
		this.setY(0);
	}
}
