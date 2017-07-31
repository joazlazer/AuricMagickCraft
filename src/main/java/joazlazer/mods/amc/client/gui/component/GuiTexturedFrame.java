package joazlazer.mods.amc.client.gui.component;

import joazlazer.mods.amc.client.gui.IGuiAccess;
import joazlazer.mods.amc.common.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import static joazlazer.mods.amc.client.gui.component.GuiTexturedFrame.CONSTANTS.*;

public class GuiTexturedFrame {
    private class FrameComponent {
        public int x, y, w, h;
        public FrameComponent(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }
        public FrameComponent() {

        }
    }

    static class CONSTANTS {
        static final int EDGE_WIDTH = 15;
        static class VERTICES {
            static final int TOP_LEFT_U = 0;
            static final int TOP_LEFT_V = 0;
            static final int TOP_RIGHT_U = EDGE_WIDTH;
            static final int TOP_RIGHT_V = 0;
            static final int BOTTOM_RIGHT_U = EDGE_WIDTH;
            static final int BOTTOM_RIGHT_V = EDGE_WIDTH;
            static final int BOTTOM_LEFT_U = 0;
            static final int BOTTOM_LEFT_V = EDGE_WIDTH;
        }
    }

    private static final ResourceLocation component_textures = new ResourceLocation(Reference.MOD_ID, "textures/gui/gui_base.png");

    private boolean isValid = false;
    private FrameComponent topLeftVertex, topRightVertex, bottomRightVertex, bottomLeftVertex;
    private FrameComponent[] vertexBorderEdges, edges, insideBackgroundTiles, insideShadowCorners, insideShadowEdges;
    private int insideWidth, insideHeight;

    public GuiTexturedFrame(int insideWidth, int insideHeight) {
        this.insideWidth = insideWidth;
        this.insideHeight = insideHeight;
        rebuildComponents();
    }

    public void render(IGuiAccess gui, int x, int y) {
        if(!isValid()) rebuildComponents();
        Minecraft.getMinecraft().getTextureManager().bindTexture(component_textures);
        drawComp(topLeftVertex, gui, VERTICES.TOP_LEFT_U, VERTICES.TOP_LEFT_V);
        drawComp(topRightVertex, gui, VERTICES.TOP_RIGHT_U, VERTICES.TOP_RIGHT_V);
        drawComp(bottomRightVertex, gui, VERTICES.BOTTOM_RIGHT_U, VERTICES.BOTTOM_RIGHT_V);
        drawComp(bottomLeftVertex, gui, VERTICES.BOTTOM_LEFT_U, VERTICES.BOTTOM_LEFT_V);
    }

    private void drawComp(FrameComponent component, IGuiAccess gui, int u, int v) {
        gui.renderRectangle(component.x, component.y, u, v, component.w, component.h);
    }

    private void rebuildComponents() {
        rebuildVertices();
        rebuildVertexBorderEdges();
        rebuildEdges();
        rebuildInsideBackgroundTiles();
        rebuildInsideShadowCorners();
        rebuildInsideShadowEdges();
        isValid = true;
    }

    public void invalidate() {
        isValid = false;
    }

    public boolean isValid() {
        return this.isValid;
    }

    private void rebuildVertices() {
        int rightFarVertexX = EDGE_WIDTH + insideWidth;
        int bottomVertexY = EDGE_WIDTH + insideHeight;
        topLeftVertex = new FrameComponent(0, 0, EDGE_WIDTH, EDGE_WIDTH);
        topRightVertex = new FrameComponent(rightFarVertexX, 0, EDGE_WIDTH, EDGE_WIDTH);
        bottomRightVertex = new FrameComponent(rightFarVertexX, bottomVertexY, EDGE_WIDTH, EDGE_WIDTH);
        bottomLeftVertex = new FrameComponent(0, bottomVertexY, EDGE_WIDTH, EDGE_WIDTH);
    }

    private void rebuildVertexBorderEdges() {
        vertexBorderEdges = new FrameComponent[8];

    }

    private void rebuildInsideShadowEdges() {

    }

    private void rebuildInsideShadowCorners() {

    }

    private void rebuildInsideBackgroundTiles() {

    }

    private void rebuildEdges() {

    }
}
