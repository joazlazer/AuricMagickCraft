package joazlazer.mods.amc.client.gui.inventory;

import java.util.List;

import joazlazer.mods.amc.client.gui.GuiElement;
import net.minecraft.client.gui.FontRenderer;

public interface IGuiAmc {
	public int getGuiTop();
	
	public int getGuiLeft();

	public void drawHoverString(@SuppressWarnings("rawtypes") List lst, int x, int y);

	public void drawTexturedModalRectAmc(int i, int j, int srcX, int srcY, int w,
			int h);
	
	public void removeGuiElement(GuiElement elementClass, int index);
	
	public void drawText(String txt, int x, int y, int color, boolean shadow);
	
	public void drawTextWithAlpha(String txt, int x, int y, int color, int alpha, FontRenderer font, boolean shadow);
	
	public FontRenderer getFontRenderer();
}
