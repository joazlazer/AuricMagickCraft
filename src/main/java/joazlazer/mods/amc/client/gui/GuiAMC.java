package joazlazer.mods.amc.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.util.List;

public abstract class GuiAMC extends GuiScreen implements IGuiAccess {

    public GuiAMC() {
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
    }

    /**
     * The X size of the gui window in pixels.
     */
    protected int xSize = 176;
    /**
     * The Y size of the gui window in pixels.
     */
    protected int ySize = 166;
    /**
     * Starting X position for the Gui. Inconsistent use for Gui backgrounds.
     */
    protected int guiLeft;
    /**
     * Starting Y position for the Gui. Inconsistent use for Gui backgrounds.
     */
    protected int guiTop;

    @Override
    public int getGuiTop()
    {
        return guiTop;
    }

    @Override
    public int getGuiLeft()
    {
        return guiLeft;
    }

    @Override
    public void drawHoverString(@SuppressWarnings("rawtypes") List lst, int x, int y) {
        drawHoveringText(lst, x, y, Minecraft.getMinecraft().fontRenderer);
    }

    @Override
    public void renderRectangle(int i, int j, int srcX, int srcY, int w, int h)
    {
        this.drawTexturedModalRect(i, j, srcX, srcY, w, h);
    }
}
