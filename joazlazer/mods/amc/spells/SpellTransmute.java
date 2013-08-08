package joazlazer.mods.amc.spells;

import java.util.ArrayList;

import joazlazer.mods.amc.client.gui.GuiTextFormat;
import joazlazer.mods.amc.lib.Textures;

public class SpellTransmute extends SpellBase {
	public SpellTransmute()
	{
		super();
		this.setUnlocName("transmute");
		ArrayList<String> tooltip = new ArrayList<String>();
		tooltip.add(GuiTextFormat.LIGHTBLUE + "Transmutation Spell");
		tooltip.add("This spell changes blocks in the world.");
		tooltip.add("You choose the transmute by selecting it.");
		tooltip.add("");
		tooltip.add(GuiTextFormat.YELLOW + "Alchemyst");
		this.setTooltip(tooltip);
		this.setSpecialty(true);
		this.setTextureLocation(Textures.SPELLS.SPELLS_SPRITESHEET);
		this.setX(1);
		this.setY(0);
	}
}
