package joazlazer.mods.amc.client.gui;


import java.util.List;

public interface IGuiAccess {
    int getGuiTop();
    int getGuiLeft();
    void renderRectangle(int x, int y, int u, int v, int w, int h);
    void drawHoverString(List<String> text, int x, int y);
}
