package joazlazer.mods.amc.orders;

import java.util.ArrayList;

import joazlazer.mods.amc.client.gui.GuiTextFormat;
import joazlazer.mods.amc.lib.Textures;
import net.minecraft.util.ResourceLocation;

public class OrderArcana extends OrderBase{
	public OrderArcana()
	{
		super();
		this.setUnlocName("arcana");
		this.setSpriteSheet(Textures.ORDERS.ORDERS_SPRITESHEET);
		this.setSpriteLoc(16, 0);
		ArrayList<String> lines = new ArrayList<String>();
		lines.add(GuiTextFormat.YELLOW.toString() + GuiTextFormat.ITALIC.toString() + "Order Arcana");
		lines.add("Arcanist");
		this.setTooltip(lines);
		ArrayList<String> info = new ArrayList<String>();
		info.add(GuiTextFormat.LIGHTGRAY + "The arcanist is a");
		info.add(GuiTextFormat.LIGHTGRAY + "type of magician");
		info.add(GuiTextFormat.LIGHTGRAY + "that is part of");
		info.add(GuiTextFormat.LIGHTGRAY + "the " + GuiTextFormat.YELLOW + GuiTextFormat.ITALIC + "Order Arcana" + GuiTextFormat.RESET);
		info.add(GuiTextFormat.LIGHTGRAY + "and mostly casts");
		info.add(GuiTextFormat.LIGHTGRAY + "spells that have");
		info.add(GuiTextFormat.LIGHTGRAY + "an immediate");
		info.add(GuiTextFormat.LIGHTGRAY + "effect. The");
		info.add(GuiTextFormat.LIGHTGRAY + "arcanist is");
		info.add(GuiTextFormat.LIGHTGRAY + "usually right in");
		info.add(GuiTextFormat.LIGHTGRAY + "the battle, casting");
		info.add(GuiTextFormat.LIGHTGRAY + "melee or ranged");
		info.add(GuiTextFormat.LIGHTGRAY + "spells to hurt the");
		info.add(GuiTextFormat.LIGHTGRAY + "enemy.");
		info.add(GuiTextFormat.LIGHTGRAY + "Unfortunately, the");
		info.add(GuiTextFormat.LIGHTGRAY + "arcanist uses up");
		info.add(GuiTextFormat.LIGHTGRAY + "his/her aura fast");
		info.add(GuiTextFormat.LIGHTGRAY + "in a battle.");
		this.setInfoText(info);
		this.setPracticer("Arcanist");
		this.setId(0);
	}
}
