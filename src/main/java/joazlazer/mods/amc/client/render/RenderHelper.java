package joazlazer.mods.amc.client.render;

import joazlazer.mods.amc.api.order.OrderBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import java.awt.*;

public class RenderHelper {
    private static final int ORDER_ICON_SIZE = 16;
    private static final int LARGE_ORDER_ICON_SIZE = 32;

    public static void drawOrderIcon(int x, int y, OrderBase order) {
        drawOrderIcon(x, y, order, GL11.GL_ONE_MINUS_SRC_ALPHA, 0.0D);
    }

    public static void drawOrderIcon(int x, int y, OrderBase order, int blend, double z) {
        drawOrderIcon((double) x, (double) y, order, blend, z);
    }

    public static void drawOrderIcon(double x, double y, OrderBase order, int blend, double z) {
        if (order == null) {
            return;
        }
        Minecraft mc = Minecraft.getMinecraft();
        Color color = order.getColor();

        GlStateManager.pushMatrix(); {
            GlStateManager.disableLighting();
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.003921569F);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, blend);
            GlStateManager.pushMatrix();
            mc.renderEngine.bindTexture(order.getTexture());
            Tessellator tess = Tessellator.getInstance();
            BufferBuilder buffer = tess.getBuffer();
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
            buffer.pos(x + 0.0D, y + ORDER_ICON_SIZE, z).tex(0.0D, 1.0D).color(color.getRed(), color.getGreen(),color.getBlue(), color.getAlpha()).endVertex();
            buffer.pos(x + ORDER_ICON_SIZE, y + ORDER_ICON_SIZE, z).tex(1.0D, 1.0D).color(color.getRed(), color.getGreen(),color.getBlue(), color.getAlpha()).endVertex();
            buffer.pos(x + ORDER_ICON_SIZE, y + 0.0D, z).tex(1.0D, 0.0D).color(color.getRed(), color.getGreen(),color.getBlue(), color.getAlpha()).endVertex();
            buffer.pos(x + 0.0D, y + 0.0D, z).tex(0.0D, 0.0D).color(color.getRed(), color.getGreen(),color.getBlue(), color.getAlpha()).endVertex();
            tess.draw();
            GlStateManager.popMatrix();
            GlStateManager.disableBlend();
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
            GlStateManager.enableLighting();
        }
        GlStateManager.popMatrix();
    }

    public static void drawLargeOrderIcon(int x, int y, OrderBase order) {
        drawLargeOrderIcon(x, y, order, GL11.GL_ONE_MINUS_SRC_ALPHA, 0.0D);
    }

    public static void drawLargeOrderIcon(int x, int y, OrderBase order, int blend, double z) {
        drawLargeOrderIcon((double) x, (double) y, order, blend, z);
    }

    public static void drawLargeOrderIcon(double x, double y, OrderBase order, int blend, double z) {
        if (order == null) {
            return;
        }
        Minecraft mc = Minecraft.getMinecraft();
        Color color = order.getColor();

        GlStateManager.pushMatrix(); {
            GlStateManager.disableLighting();
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.003921569F);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, blend);
            GlStateManager.pushMatrix();
            mc.renderEngine.bindTexture(order.getLargeTexture());
            Tessellator tess = Tessellator.getInstance();
            BufferBuilder buffer = tess.getBuffer();
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
            buffer.pos(x + 0.0D, y + LARGE_ORDER_ICON_SIZE, z).tex(0.0D, 1.0D).color(color.getRed(), color.getGreen(),color.getBlue(), color.getAlpha()).endVertex();
            buffer.pos(x + LARGE_ORDER_ICON_SIZE, y + LARGE_ORDER_ICON_SIZE, z).tex(1.0D, 1.0D).color(color.getRed(), color.getGreen(),color.getBlue(), color.getAlpha()).endVertex();
            buffer.pos(x + LARGE_ORDER_ICON_SIZE, y + 0.0D, z).tex(1.0D, 0.0D).color(color.getRed(), color.getGreen(),color.getBlue(), color.getAlpha()).endVertex();
            buffer.pos(x + 0.0D, y + 0.0D, z).tex(0.0D, 0.0D).color(color.getRed(), color.getGreen(),color.getBlue(), color.getAlpha()).endVertex();
            tess.draw();
            GlStateManager.popMatrix();
            GlStateManager.disableBlend();
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
            GlStateManager.enableLighting();
        }
        GlStateManager.popMatrix();
    }
}
