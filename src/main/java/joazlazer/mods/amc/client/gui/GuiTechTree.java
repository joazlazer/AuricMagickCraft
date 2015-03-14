package joazlazer.mods.amc.client.gui;

import joazlazer.mods.amc.client.gui.component.GuiButton;
import joazlazer.mods.amc.client.render.RenderHelper;
import joazlazer.mods.amc.util.MathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import java.awt.Color;

import javax.vecmath.Color4b;
import java.util.Random;
/*import joazlazer.mods.amc.client.render.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;*/

public class GuiTechTree extends GuiAMC {
    Color windowColor;
    int supposedBoundsColor;

    public GuiTechTree() {
        windowColor = RenderHelper.randomOpaqueColor();
        supposedBoundsColor = RenderHelper.randomOpaqueColor().getRGB();
    }

    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        this.drawDefaultBackground();
        //this.drawGradientRect(0, 0, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, windowColor, windowColor);
        //this.drawGradientRect(this.guiLeft, this.guiTop, this.xSize, this.ySize, supposedBoundsColor, supposedBoundsColor);
        //MathHelper.circle(16, 16, 8).primitiveDraw(GL11.GL_LINE_LOOP, true, windowColor, zLevel);
        //RenderHelper.drawHollowCircle(16, 32, 8, MathHelper.getEstimatedSegmentCount("circle", 8), zLevel, windowColor);
        //this.drawGradientRect(this.mc.displayWidth / 2 - 8, this.mc.displayHeight - 16, this.mc.displayWidth / 2 + 8, this.mc.displayHeight, supposedBoundsColor, supposedBoundsColor);
    }

    @Override
    public void onButtonClicked(GuiButton button, int mouseX, int mouseY, int mouseButton) {

    }
}
