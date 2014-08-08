package joazlazer.mods.amc.client.gui;

import joazlazer.mods.amc.client.gui.component.GuiButton;

import java.util.List;

public interface IGuiAccess {
    public int getGuiTop();
    public int getGuiLeft();
    public void renderRectangle(int x, int y, int u, int v, int w, int h);
    public void drawHoverString(List<String> text, int x, int y);
    public void onButtonClicked(GuiButton button, int mouseX, int mouseY, int mouseButton);
}
