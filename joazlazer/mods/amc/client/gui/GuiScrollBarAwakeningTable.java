package joazlazer.mods.amc.client.gui;

import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.client.gui.inventory.GuiAmcContainer;
import joazlazer.mods.amc.client.gui.inventory.GuiAwakeningTable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiScrollBarAwakeningTable {
	
	public static final GuiRectangle slider = new GuiRectangle(230, 18, 12, 14);
	public static final int HEIGHT_MAX = 107;
	
	public int height;
	public boolean isDragging;
	
	public void draw(GuiAwakeningTable gui, boolean disabled)
	{
		slider.setY(18 + height);
		if (disabled == true) slider.draw(gui, 12, 228);
		else slider.draw(gui, 0, 228);
	}
	
	public void mouseClick(GuiAwakeningTable gui, int x, int y, int button, boolean dis)
	{
		if (slider.inRect(gui, x, y) && !dis)
		{
			isDragging = true;
		}
	}
	
	public void mouseClickMove(GuiAwakeningTable gui, int x, int y, int button, long timeSinceClicked, boolean dis)
	{
		if (isDragging && !dis)
		{
			height = y - gui.getGuiTop() - 18 - slider.getH() / 2;
		}
		
		if (height < 0) {
			height = 0;
		}else if(height > HEIGHT_MAX - slider.getH()) {
			height = HEIGHT_MAX - slider.getH();
		}
	}
	
	public void mouseRelease(GuiAwakeningTable gui, int x, int y, int button, boolean dis)
	{
		if (isDragging)
		{
			isDragging = false;
		}
		if (dis)
		{
			height = 0;
		}
	}
}
