package joazlazer.mods.amc.client.gui;

import cpw.mods.fml.common.gameevent.TickEvent;
import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.api.spell.*;
import joazlazer.mods.amc.client.render.RenderHelper;
import joazlazer.mods.amc.entity.player.PlayerData;
import joazlazer.mods.amc.reference.Textures;
import joazlazer.mods.amc.util.exception.MapNoContainsKeyException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

public class GuiSpellSelectionOverlay {
    public static boolean fadingIn;
    public static int opacity = 0;
    public static int fadingOutTimer = 0;
    public static int selectedSpell;

    public void render(RenderGameOverlayEvent event) {
        // Grab an instance of Minecraft.
        Minecraft mc = Minecraft.getMinecraft();

        // Determine position of the circle.
        int posX = event.resolution.getScaledWidth() / 2 - 128;
        int posY = event.resolution.getScaledHeight() / 2 - 128;

        // Reset the color.
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        // Draw the required 2D Elements.
        GL11.glPushMatrix();
        {
            // Configure OpenGL Options.
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            // Draw the circle.
            GL11.glPushMatrix();
            {
                // The size of the rendered shape.
                double size = 256.0D;

                // Bind the texture.
                mc.renderEngine.bindTexture(Textures.Gui.SPELL_SELECT);

                // Grab the tessellator.
                Tessellator tessellator = Tessellator.instance;

                // Start drawing a quad, and make sure the color is white.
                tessellator.startDrawingQuads();
                tessellator.setColorRGBA(255, 255, 255, opacity);

                // Add the four vertices.
                tessellator.addVertexWithUV(posX + 0.0D, posY + size, 0.0D, 0.0D, 1.0D);
                tessellator.addVertexWithUV(posX + size, posY + size, 0.0D, 1.0D, 1.0D);
                tessellator.addVertexWithUV(posX + size, posY + 0.0D, 0.0D, 1.0D, 0.0D);
                tessellator.addVertexWithUV(posX + 0.0D, posY + 0.0D, 0.0D, 0.0D, 0.0D);

                // Finally, draw.
                tessellator.draw();
            }
            GL11.glPopMatrix();

            // Draw the circle.
            GL11.glPushMatrix();
            {
                // The size of the rendered shape.
                double size = 12.0D;

                // Bind the texture.
                mc.renderEngine.bindTexture(Textures.Gui.ARROW);

                // Grab the tessellator.
                Tessellator tessellator = Tessellator.instance;

                // Offset the center.
                posX += 128.0D;
                posY += 128.0D;

                // Rotate the matrix.
                GL11.glTranslatef(posX, posY, 0.0f);
                GL11.glRotatef(selectedSpell * 30, 0.0f, 0.0f, 1.0f);
                GL11.glTranslatef(0.0f, -78.0f, 0.0f);

                // Start drawing a quad, and make sure the color is white.
                tessellator.startDrawingQuads();
                tessellator.setColorRGBA(255, 255, 255, opacity);

                // Add the four vertices.
                tessellator.addVertexWithUV(+0.0D, +size, 0.0D, 0.0D, 1.0D);
                tessellator.addVertexWithUV(+size, +size, 0.0D, 1.0D, 1.0D);
                tessellator.addVertexWithUV(+size, +0.0D, 0.0D, 1.0D, 0.0D);
                tessellator.addVertexWithUV(+0.0D, +0.0D, 0.0D, 0.0D, 0.0D);

                // Finally, draw.
                tessellator.draw();

                // Un-Offset the center.
                posX -= 122.0D;
                posY -= 122.0D;
            }
            GL11.glPopMatrix();

            // Reset the OpenGL Options.
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            GL11.glEnable(GL11.GL_LIGHTING);
        }
        GL11.glPopMatrix();

        renderSpellIconInPosition(0, false, new SpellBlink(), event, opacity);
        renderSpellIconInPosition(1, false, new SpellArcaneBolt(), event, opacity);
        renderSpellIconInPosition(2, false, new SpellFireball(), event, opacity);
        renderSpellIconInPosition(3, false, new SpellIceBolt(), event, opacity);
        renderSpellIconInPosition(4, false, new SpellEnergyBeam(), event, opacity);
        renderSpellIconInPosition(5, false, new SpellFireWave(), event, opacity);
        renderSpellIconInPosition(6, false, new SpellFireBlast(), event, opacity);
        renderSpellIconInPosition(7, false, new SpellWaterWhip(), event, opacity);
        renderSpellIconInPosition(8, false, new SpellWaterWave(), event, opacity);
        renderSpellIconInPosition(9, false, new SpellEnergyBall(), event, opacity);
        renderSpellIconInPosition(10, false, new SpellTransmute(), event, opacity);
        renderSpellIconInPosition(11, false, new SpellIceSpear(), event, opacity);
    }

    public void update(TickEvent.ClientTickEvent event) {
        if (fadingIn && opacity <= 255) opacity += 16;

        if (!fadingIn && opacity >= 0) opacity -= 16;

        if (opacity > 255) opacity = 255;
        if (opacity < 0) opacity = 0;

        if (fadingOutTimer > 0) {
            fadingOutTimer -= 10;
        }

        if (fadingOutTimer <= 0) {
            fadingOutTimer = 0;
            fadeOut();
        }
    }

    public static void nextSpell() {
        fadingOutTimer = 560;
        fadeIn();
        selectedSpell -= 1;
        if (selectedSpell < 0) selectedSpell = 11;
        try {
            PlayerData data = AuricMagickCraft.PlayerTracker.getData(Minecraft.getMinecraft().thePlayer);
            data.setInteger("selectedSpell", selectedSpell);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void previousSpell() {
        fadingOutTimer = 560;
        fadeIn();
        selectedSpell += 1;
        if (selectedSpell > 11) selectedSpell = 0;
        try {
            PlayerData data = AuricMagickCraft.PlayerTracker.getData(Minecraft.getMinecraft().thePlayer);
            data.setInteger("selectedSpell", selectedSpell);
        } catch (MapNoContainsKeyException e) {
            e.printStackTrace();
        }
    }

    public static void fadeIn() {
        fadingIn = true;
    }

    public static void fadeOut() {
        fadingIn = false;
    }

    public void renderSpellIconInPosition(int pos, boolean exploded, SpellBase spell, RenderGameOverlayEvent event, int opacity) {
        // Determine position of the circle.
        int posX = event.resolution.getScaledWidth() / 2 - 128;
        int posY = event.resolution.getScaledHeight() / 2 - 128;

        switch (pos) {
            case 0: {
                RenderHelper.drawLargeSpellIcon(posX + 116, posY + 18, spell, opacity);
                break;
            }
            case 1: {
                RenderHelper.drawLargeSpellIcon(posX + 165, posY + 31, spell, opacity);
                break;
            }
            case 2: {
                RenderHelper.drawLargeSpellIcon(posX + 201, posY + 67, spell, opacity);
                break;
            }
            case 3: {
                RenderHelper.drawLargeSpellIcon(posX + 214, posY + 116, spell, opacity);
                break;
            }
            case 4: {
                RenderHelper.drawLargeSpellIcon(posX + 201, posY + 165, spell, opacity);
                break;
            }
            case 5: {
                RenderHelper.drawLargeSpellIcon(posX + 165, posY + 201, spell, opacity);
                break;
            }
            case 6: {
                RenderHelper.drawLargeSpellIcon(posX + 116, posY + 214, spell, opacity);
                break;
            }
            case 7: {
                RenderHelper.drawLargeSpellIcon(posX + 67, posY + 201, spell, opacity);
                break;
            }
            case 8: {
                RenderHelper.drawLargeSpellIcon(posX + 31, posY + 165, spell, opacity);
                break;
            }
            case 9: {
                RenderHelper.drawLargeSpellIcon(posX + 18, posY + 116, spell, opacity);
                break;
            }
            case 10: {
                RenderHelper.drawLargeSpellIcon(posX + 31, posY + 67, spell, opacity);
                break;
            }
            case 11: {
                RenderHelper.drawLargeSpellIcon(posX + 67, posY + 31, spell, opacity);
                break;
            }
        }
    }
}

