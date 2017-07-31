package joazlazer.mods.amc.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

import java.util.List;

public abstract class GuiContainerAMC extends GuiContainer implements IGuiAccess {

    public GuiContainerAMC(Container c, int xSize, int ySize) {
        super(c);
        this.guiLeft = (this.width - xSize) / 2;
        this.guiTop = (this.height - ySize) / 2;
        this.xSize = xSize;
        this.ySize = ySize;
    }

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
    public void renderRectangle(int x, int y, int u, int v, int w, int h) {
        this.drawTexturedModalRect(x, y, u, v, w, h);
    }

    @Override
    public void drawHoverString(List<String> text, int x, int y) {
        drawHoveringText(text, x, y, Minecraft.getMinecraft().fontRenderer);
    }
}
