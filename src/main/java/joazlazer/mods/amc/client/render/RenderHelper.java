package joazlazer.mods.amc.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.shader.TesselatorVertexState;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import java.awt.Color;

import java.util.Random;

public class RenderHelper {
    // Static Variables.
    public static ResourceLocation placeholder;
    public static DynamicTexture viewTexture;
    public static Tessellator tess;
    public static Random random;

    // Constants
    private static final int zLevel = 0;

    static {
        // Initialize.x
        random = new Random();
        viewTexture = new DynamicTexture(256, 256);
    }

    public static boolean blurScreen(int times, int startX, int startY, int endX, int endY) {
        // Get the current instance of Minecraft.
        Minecraft mc = Minecraft.getMinecraft();

        // Set the placeholder texture.
        placeholder = mc.renderEngine.getDynamicTextureLocation("blur_background_amc", viewTexture);

        // Set the viewport.
        GL11.glViewport(0, 0, 256, 256);

        // Iterate through the blurring (Do it 4 times for each blur that is called).
        for (int i = 0; i < times * 4 - 1; i++) {
            // Blur the screen once,
            blurScreenOnce(startX, startY, endX, endY, mc);
        }

        GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);

        tess.startDrawingQuads();
        float f1 = mc.displayWidth > mc.displayHeight ? 120.0F / (float)mc.displayWidth : 120.0F / (float)mc.displayHeight;
        float f2 = (float)mc.displayHeight * f1 / 256.0F;
        float f3 = (float)mc.displayWidth * f1 / 256.0F;
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
        int k = mc.displayWidth;
        int l = mc.displayHeight;
        tess.addVertexWithUV(0.0D, (double) l, (double) zLevel, (double) (0.5F - f2), (double) (0.5F + f3));
        tess.addVertexWithUV((double) k, (double) l, (double) zLevel, (double) (0.5F - f2), (double) (0.5F - f3));
        tess.addVertexWithUV((double) k, 0.0D, (double) zLevel, (double) (0.5F + f2), (double) (0.5F - f3));
        tess.addVertexWithUV(0.0D, 0.0D, (double) zLevel, (double) (0.5F + f2), (double) (0.5F + f3));
        tess.draw();


        return true;
    }

    private static void blurScreenOnce(int startX, int startY, int endX, int endY, Minecraft mc) {
        // Bind the texture.
        mc.renderEngine.bindTexture(placeholder);

        // Copies the screen.
        GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, (int)(startX * 256.0F / mc.displayWidth), (int)(startY * 256.0F / mc.displayHeight), (int)((endX - startX) * 256.0F / mc.displayWidth), (int)((endY - startY) * 256.0F / mc.displayHeight), 256, 256);

        // Enable the blend function.
        GL11.glEnable(GL11.GL_BLEND);

        // Process the blend function.
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // Set it so that alpha isn't written to the frame buffer.
        GL11.glColorMask(true, true, true, false);

        // Start drawing quadrilaterals.
        tess.startDrawingQuads();

        // Constant
        byte var3 = 3;

        for (int i = 0; i < var3; ++i)
        {
            tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F / (float)(i + 1));
            int j = mc.displayWidth;
            int k = mc.displayHeight;
            float f1 = (float)(i - var3 / 2) / 256.0F;
            tess.addVertexWithUV((double) j, (double) k, (double) zLevel, (double) (0.0F + f1), 0.0D);
            tess.addVertexWithUV((double) j, 0.0D, (double) zLevel, (double) (1.0F + f1), 0.0D);
            tess.addVertexWithUV(0.0D, 0.0D, (double) zLevel, (double) (1.0F + f1), 1.0D);
            tess.addVertexWithUV(0.0D, (double) k, (double) zLevel, (double) (0.0F + f1), 1.0D);
        }

        tess.draw();
        GL11.glColorMask(true, true, true, true);
    }

    public static void blurEntireScreen() {
        // Set the placeholder texture.
        placeholder = Minecraft.getMinecraft().renderEngine.getDynamicTextureLocation("blur_background_amc", viewTexture);

        for(int i0 = 0; i0 < 7; i0++) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(placeholder);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
            GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, 256, 256);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glColorMask(true, true, true, false);
            tess.startDrawingQuads();
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            byte b0 = 3;

            for (int i = 0; i < b0; ++i)
            {
                tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F / (float)(i + 1));
                int j = Minecraft.getMinecraft().displayWidth;
                int k = Minecraft.getMinecraft().displayHeight;
                float f1 = (float)(i - b0 / 2) / 256.0F;
                tess.addVertexWithUV((double)j, (double)k, (double)zLevel, (double)(0.0F + f1), 1.0D);
                tess.addVertexWithUV((double)j, 0.0D, (double)zLevel, (double)(1.0F + f1), 1.0D);
                tess.addVertexWithUV(0.0D, 0.0D, (double)zLevel, (double)(1.0F + f1), 0.0D);
                tess.addVertexWithUV(0.0D, (double)k, (double)zLevel, (double)(0.0F + f1), 0.0D);
            }

            tess.draw();
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glColorMask(true, true, true, true);
        }
    }

    public static void drawHollowCircle(float cx, float cy, float r, int num_segments, float zLevel, Color color) {

        // Disclaimer: I found this algorithm on the web and it uses all of this high-math shiz-waz and some physics crap so I have no idea what it does :P
        float theta = 2 * 3.141592654F / (float) num_segments;
        float tangetial_factor = (float) Math.tan(theta);
        float radial_factor = (float) Math.cos(theta);
        float x = r;
        float y = 0;

        // Start drawing a line loop.
        tess.instance.startDrawing(2);

        // Color the circle.
        tess.instance.setColorRGBA(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());

        for(int ii = 0; ii < num_segments; ii++) {

            // Add a point.
            tess.instance.addVertex(x + cx, y + cy, zLevel);

            float tx = -y;
            float ty = x;

            x += tx * tangetial_factor;
            y += ty * tangetial_factor;

            x *= radial_factor;
            y *= radial_factor;
        }

        GL11.glColor4f(1.0f, 1.0f, 0.6f, 1.0f);

        // Draw the line loop.
        tess.instance.draw();
    }

    public static void drawCircle(float x, float y, float radius, int num_segments, float z_level, Color color, boolean hollow) {
        if(hollow) {
            drawHollowCircle(x, y, radius, num_segments, z_level, color);
            return;
        }

        drawCircle(x, y, radius, num_segments, z_level, color);
    }

    public static void drawCircle(float cx, float cy, float radius, int num_segments, float zLevel, Color color) {
        // Disclaimer: I found this algorithm on the web and it uses all of this high-math shiz-waz and some physics crap so I have no idea what it does :P
        float theta = 2 * 3.141592654F / (float) num_segments;
        float tangetial_factor = (float) Math.tan(theta);
        float radial_factor = (float) Math.cos(theta);
        float x = radius;
        float y = 0;

        // Start drawing a line loop.
        tess.startDrawing(9);

        // Color the circle.
        tess.setColorRGBA(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel(GL11.GL_SMOOTH);

        for(int ii = 0; ii < num_segments; ii++) {

            // Add a point.
            tess.addVertex(x + cx, y + cy, zLevel);

            float tx = -y;
            float ty = x;

            x += tx * tangetial_factor;
            y += ty * tangetial_factor;

            x *= radial_factor;
            y *= radial_factor;
        }

        // Draw the line loop.
        tess.draw();

        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public static int randomColorInt() { return random.nextInt(255); }
    public static Color randomOpaqueColor() { return new Color(randomColorInt(), randomColorInt(), randomColorInt(), 255); }
}
