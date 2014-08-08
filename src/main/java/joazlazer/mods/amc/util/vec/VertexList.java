package joazlazer.mods.amc.util.vec;

import net.minecraft.client.renderer.Tessellator;

import java.awt.Color;
import java.util.ArrayList;

public class VertexList extends ArrayList<Vertex2D> {
    public VertexList() {
        super();
    }

    public VertexList copy() {
        VertexList copy = new VertexList();
        for(Vertex2D v : this) {
            copy.add(v.copy());
        }
        return copy;
    }

    public VertexList translate(int xDelta, int yDelta) {
        for(Vertex2D v : this) {
            v.translate(xDelta, yDelta);
        }
        return this;
    }

    public void primitiveDraw(int drawMode, boolean useMinecraftTess, Color color, float zLevel) {
        if(useMinecraftTess) {
            Tessellator tess = Tessellator.instance;
            tess.startDrawing(drawMode);
            tess.setColorRGBA(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
            for(Vertex2D v : this) {
                tess.addVertex(v.getX(), v.getY(), zLevel);
                System.out.println(v.getX() + ", " + v.getY());
            }
            tess.draw();
        }
    }
}
