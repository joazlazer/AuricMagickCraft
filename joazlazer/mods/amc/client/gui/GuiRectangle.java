package joazlazer.mods.amc.client.gui;

import java.util.Arrays;
import java.util.List;

import joazlazer.mods.amc.client.gui.inventory.GuiAmcContainer;
import joazlazer.mods.amc.client.gui.inventory.IGuiAmc;

public class GuiRectangle {

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the w
	 */
	public int getW() {
		return w;
	}

	/**
	 * @param w the w to set
	 */
	public void setW(int w) {
		this.w = w;
	}

	/**
	 * @return the h
	 */
	public int getH() {
		return h;
	}

	/**
	 * @param h the h to set
	 */
	public void setH(int h) {
		this.h = h;
	}

	private int x;
	private int y;
	private int w;
	private int h;
	
	public GuiRectangle(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public boolean inRect(IGuiAmc gui, int mouseX, int mouseY) {
		mouseX -= gui.getGuiLeft();
		mouseY -= gui.getGuiTop();
		
		return x <= mouseX && mouseX <= x + w && y <= mouseY && mouseY <= y + h;
	}
	
	
	public void draw(IGuiAmc gui, int srcX, int srcY) {
		gui.drawTexturedModalRectAmc(gui.getGuiLeft() + x, gui.getGuiTop() + y, srcX, srcY, w, h);
	}
	
	public void drawString(IGuiAmc gui, int mouseX, int mouseY, List<String> arrayList) {
		if (inRect(gui, mouseX, mouseY)) {
			gui.drawHoverString(arrayList, mouseX - gui.getGuiLeft(), mouseY - gui.getGuiTop());
		}
	}
	
	public void drawString(GuiAmcContainer gui, int mouseX, int mouseY, String str) {
		if (inRect(gui, mouseX, mouseY)) {
			gui.drawHoverString(Arrays.asList(str.split("\n")), mouseX - gui.getGuiLeft(), mouseY - gui.getGuiTop());
		}
	}
}

