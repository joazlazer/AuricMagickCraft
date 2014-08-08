package joazlazer.mods.amc.client.gui.component;

import joazlazer.mods.amc.client.gui.IGuiAccess;
import net.minecraft.client.gui.GuiScreen;

import java.util.Arrays;
import java.util.List;

public class GuiRectangle {
    public int x;
    public int y;
    public int w;
    public int h;

    public GuiRectangle(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void draw(IGuiAccess gui, int u, int v) {

        gui.renderRectangle(gui.getGuiLeft() + x, gui.getGuiTop() + y, u, v, w, h);
    }

    public boolean inRect(IGuiAccess gui, int mouseX, int mouseY) {
        mouseX -= gui.getGuiLeft();
        mouseY -= gui.getGuiTop();

        return x <= mouseX && mouseX <= x + w && y <= mouseY && mouseY <= y + h;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public GuiRectangle setX(int x) {
        this.x = x;
        return this;
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
    public GuiRectangle setY(int y) {
        this.y = y;
        return this;
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
    public GuiRectangle setW(int w) {
        this.w = w;
        return this;
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
    public GuiRectangle setH(int h) {
        this.h = h;
        return this;
    }

    public void drawString(IGuiAccess gui, int mouseX, int mouseY, List<String> arrayList) {
        if (inRect(gui, mouseX, mouseY)) {
            gui.drawHoverString(arrayList, mouseX - gui.getGuiLeft(), mouseY - gui.getGuiTop());
        }
    }

    public void drawString(IGuiAccess gui, int mouseX, int mouseY, String str) {
        if (inRect(gui, mouseX, mouseY)) {
            gui.drawHoverString(Arrays.asList(str.split("\n")), mouseX - gui.getGuiLeft(), mouseY - gui.getGuiTop());
        }
    }
}
