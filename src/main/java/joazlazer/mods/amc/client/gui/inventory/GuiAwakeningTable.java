package joazlazer.mods.amc.client.gui.inventory;

import joazlazer.mods.amc.AMCLogger;
import joazlazer.mods.amc.client.gui.GuiElement;
import joazlazer.mods.amc.client.gui.container.ContainerAwakeningTable;
import joazlazer.mods.amc.lib.Textures;
import joazlazer.mods.amc.tileentity.TileEntityAwakeningTable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

public class GuiAwakeningTable extends GuiAmcContainer {

	public TileEntityAwakeningTable table;
	
	public GuiAwakeningTable(InventoryPlayer playerInv,
			TileEntityAwakeningTable table) {
		super(new ContainerAwakeningTable(playerInv, table));
		
		// Set the table to the current one.
		this.table = table;
		
		field_146999_f = 256;
		field_147000_g = 144;
	}

	public void func_146280_a(Minecraft p_146280_1_, int p_146280_2_, int p_146280_3_)
    {
		super.func_146280_a(p_146280_1_, p_146280_2_, p_146280_3_);
    }
	
	@Override
	protected void func_146976_a(float var1, int var2, int var3) {
		// Reset the current color.
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		// Bind the texture.
		Minecraft.getMinecraft().getTextureManager().bindTexture(Textures.GUIS.AWAKENING_TABLE_BACKGROUND);
		
		int l = (field_146294_l - field_146999_f) / 2;
		int i1 = (field_146295_m - field_147000_g) / 2;
		drawTexturedModalRect(l, i1, 0, 0, field_146999_f, field_147000_g);
	}

	@Override
	public void removeGuiElement(GuiElement elementClass, int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawText(String txt, int x, int y, int color, boolean shadow) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawTextWithAlpha(String txt, int x, int y, int color,
			int alpha, FontRenderer font, boolean shadow) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FontRenderer getFontRenderer() {
		// TODO Auto-generated method stub
		return null;
	}
}
