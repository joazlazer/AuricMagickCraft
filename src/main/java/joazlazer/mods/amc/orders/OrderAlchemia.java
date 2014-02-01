package joazlazer.mods.amc.orders;

import java.util.ArrayList;

import joazlazer.mods.amc.client.gui.GuiTextFormat;
import joazlazer.mods.amc.lib.Textures;

public class OrderAlchemia  extends OrderBase{
	public OrderAlchemia()
	{
		super();
		this.setUnlocName("alchemia");
		this.setSpriteSheet(Textures.ORDERS.ORDERS_SPRITESHEET);
		this.setSpriteLoc(0, 0);
		ArrayList<String> lines = new ArrayList<String>();
		lines.add(GuiTextFormat.YELLOW.toString() + GuiTextFormat.ITALIC.toString() + "Order Alchemia");
		lines.add("Alchemyst");
		this.setTooltip(lines);
		ArrayList<String> info = new ArrayList<String>();
		info.add(GuiTextFormat.LIGHTGRAY + "The alchemyst is");
		info.add(GuiTextFormat.LIGHTGRAY + "a type of magician");
		info.add(GuiTextFormat.LIGHTGRAY + "that is part of");
		info.add(GuiTextFormat.LIGHTGRAY + "the " + GuiTextFormat.YELLOW + GuiTextFormat.ITALIC + "Order" + GuiTextFormat.RESET);
		info.add(GuiTextFormat.YELLOW.toString() + GuiTextFormat.ITALIC + "Alchemia " + GuiTextFormat.RESET + GuiTextFormat.LIGHTGRAY + "and");
		info.add(GuiTextFormat.LIGHTGRAY + "focuses mainly on");
		info.add(GuiTextFormat.LIGHTGRAY + "his/her ability to");
		info.add(GuiTextFormat.LIGHTGRAY + "change one thing");
		info.add(GuiTextFormat.LIGHTGRAY + "into another.");
		info.add(GuiTextFormat.LIGHTGRAY + "Unfortunately,");
		info.add(GuiTextFormat.LIGHTGRAY + "the alchemyst");
		info.add(GuiTextFormat.LIGHTGRAY + "isn't very strong");
		info.add(GuiTextFormat.LIGHTGRAY + "on his/her own in");
		info.add(GuiTextFormat.LIGHTGRAY + "in battle, but can");
		info.add(GuiTextFormat.LIGHTGRAY + "help from the side");
		info.add(GuiTextFormat.LIGHTGRAY + "lines. Additionally,");
		info.add(GuiTextFormat.LIGHTGRAY + "the alchemyst can");
		info.add(GuiTextFormat.LIGHTGRAY + "easily create");
		info.add(GuiTextFormat.LIGHTGRAY + "many potions and");
		info.add(GuiTextFormat.LIGHTGRAY + "gems from");
		info.add(GuiTextFormat.LIGHTGRAY + "mundane");
		info.add(GuiTextFormat.LIGHTGRAY + "ingredients.");
		this.setInfoText(info);
		this.setPracticer("Alchemyst");
		this.setId(1);
	}
}