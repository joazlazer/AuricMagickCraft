package joazlazer.mods.amc.client.render.block;

import joazlazer.mods.amc.common.item.ModItems;
import joazlazer.mods.amc.common.tileentity.TileEntityAwakeningTable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import java.awt.Color;
import java.util.Random;

public class AwakeningTableTESR extends TileEntitySpecialRenderer<TileEntityAwakeningTable> {

    /** The texture for the book above the enchantment table. */
    private static final ResourceLocation TEXTURE_BOOK = new ResourceLocation("textures/entity/enchanting_table_book.png");
    private final ModelBook modelBook = new ModelBook();
    private final Color BEAM_COLOR = new Color(0, 230, 60, 255);
    private final Color BEAM_ORIGIN_COLOR = new Color(255, 255, 255, 255);

    @Override
    public void render(TileEntityAwakeningTable te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        // Translate to the location of our tile entity
        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();

        // Render the floating book
        renderBook(te, partialTicks);

        // Render the floating gem
        float yOffset = renderItem(te, partialTicks);

        // Render the beams
        renderBeams(te, partialTicks, yOffset);

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    public void renderBeams(TileEntityAwakeningTable te, float partialTicks, float yOffset) {
        if(te.awakeningTicks == -1) return;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        RenderHelper.disableStandardItemLighting();
        float f = ((float)te.awakeningTicks + partialTicks) / 200.0F;
        //float f = ((float)57 + partialTicks) / 200.0F;
        float f1 = 0.0F;

        if (f > 0.8F)
        {
            f1 = (f - 0.8F) / 0.2F;
        }

        Random random = new Random(432L);
        GlStateManager.disableTexture2D();
        GlStateManager.shadeModel(7425);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        GlStateManager.disableAlpha();
        GlStateManager.enableCull();
        GlStateManager.depthMask(false);
        GlStateManager.pushMatrix();
        GlStateManager.translate(.5, .5 + yOffset, .5);
        final float beamScale = 0.25f;
        GlStateManager.scale(beamScale, beamScale, beamScale);

        for (int i = 0; (float)i < (f + f * f) / 2.0F * 60.0F; ++i)
        {
            GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            float f2 = random.nextFloat() * 20.0F + 5.0F + f1 * 10.0F;
            float f3 = random.nextFloat() * 2.0F + 1.0F + f1 * 2.0F;
            bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
            bufferbuilder.pos(0.0D, 0.0D, 0.0D).color(BEAM_ORIGIN_COLOR.getRed(), BEAM_ORIGIN_COLOR.getGreen(), BEAM_ORIGIN_COLOR.getBlue(), (int)(((float)BEAM_ORIGIN_COLOR.getAlpha()) * (1.0F))).endVertex();
            bufferbuilder.pos(-0.866D * (double)f3, (double)f2, (double)(-0.5F * f3)).color(BEAM_COLOR.getRed(), BEAM_COLOR.getGreen(), BEAM_COLOR.getBlue(), 0).endVertex();
            bufferbuilder.pos(0.866D * (double)f3, (double)f2, (double)(-0.5F * f3)).color(BEAM_COLOR.getRed(), BEAM_COLOR.getGreen(), BEAM_COLOR.getBlue(), 0).endVertex();
            bufferbuilder.pos(0.0D, (double)f2, (double)(1.0F * f3)).color(BEAM_COLOR.getRed(), BEAM_COLOR.getGreen(), BEAM_COLOR.getBlue(), 0).endVertex();
            bufferbuilder.pos(-0.866D * (double)f3, (double)f2, (double)(-0.5F * f3)).color(BEAM_COLOR.getRed(), BEAM_COLOR.getGreen(), BEAM_COLOR.getBlue(), 0).endVertex();
            tessellator.draw();
        }

        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        GlStateManager.shadeModel(7424);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        RenderHelper.enableStandardItemLighting();
    }

    public float renderItem(TileEntityAwakeningTable te, float partialTicks) {
        // Calculate the mu for the interp'd animations
        float bookSpreadInterp = MathHelper.clamp(te.bookSpreadPrev + (te.bookSpread - te.bookSpreadPrev) * partialTicks, 0.0f, 1.0f);

        // If the item isn't visible, no point rendering
        if(bookSpreadInterp == 0.0f) return 0.0f;

        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableLighting();
        float rise;

        GlStateManager.pushMatrix();
        {
            { // Rising out of book animation
                // calculate rise
                final float riseAmount = 0.4f;
                final float completed = 0.75f;

                // Interpolate between 0 and the max rise amount
                rise = joazlazer.mods.amc.common.utility.MathHelper.limitedSinInterp(0.0f, riseAmount, completed, bookSpreadInterp);

                // Translate to the center of the block and .9 points higher
                GlStateManager.translate(.5, .9 + rise, .5);
            }

            { // Bobbing
                final float baseBookBobAmount = 0.01f;
                final float bobAmount = 0.05f;
                final float bobSpeed = 0.1f;
                float limbSwing = (float)te.tickCount + partialTicks;
                float actualBobAmount = (float)MathHelper.clampedLerp(baseBookBobAmount, bobAmount, bookSpreadInterp);
                float bobOffset = 0.1F + MathHelper.sin(limbSwing * bobSpeed) * actualBobAmount;
                if(te.awakeningTicks == -1) {
                    GlStateManager.translate(0.0F, bobOffset, 0.0F);
                    rise += bobOffset;
                }
            }

            { // base spinning
                float f1;
                for (f1 = te.bookRotation - te.bookRotationPrev; f1 >= (float) Math.PI; f1 -= ((float) Math.PI * 2F)) ;

                while (f1 < -(float) Math.PI) {
                    f1 += ((float) Math.PI * 2F);
                }

                float f2 = te.bookRotationPrev + f1 * partialTicks;
                GlStateManager.rotate(-f2 * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
            }

            // Turn the item on the Z axis by 80 degrees.
            GlStateManager.rotate(80.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.scale(0.43f, 0.43f, 0.43f);
            GlStateManager.translate(0.0f, -0.05f,0.0f);

            { // Rotating into view animation
                float angle;
                final float angleMax = 90.0f;
                final float completed = 0.75f;

                // Interpolate between 0 and the max angle
                angle = joazlazer.mods.amc.common.utility.MathHelper.limitedSinInterp(0.0f, angleMax, completed, bookSpreadInterp);

                // Rotate the rendering origin according to the angle
                GlStateManager.rotate(angle, -1.0f, 0.0f, 0.0f);
            }

            ItemStack stack = new ItemStack(ModItems.CHRYSOPRASE_GEM);
            GlStateManager.rotate(-45.0f, 0.0f, 0.0f, 1.0f);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE);
        }
        GlStateManager.popMatrix();

        return 0.4f + rise;
    }

    public void renderBook(TileEntityAwakeningTable te, float partialTicks) {
        GlStateManager.pushMatrix();
        {
            // Center the render origin to the center of the block
            GlStateManager.translate( 0.5F,  0.75F, 0.5F);

            float limbSwing = (float)te.tickCount + partialTicks;
            GlStateManager.translate(0.0F, 0.1F + MathHelper.sin(limbSwing * 0.1F) * 0.01F, 0.0F);
            float f1;

            for (f1 = te.bookRotation - te.bookRotationPrev; f1 >= (float)Math.PI; f1 -= ((float)Math.PI * 2F));

            while (f1 < -(float)Math.PI) {
                f1 += ((float)Math.PI * 2F);
            }

            float f2 = te.bookRotationPrev + f1 * partialTicks;
            GlStateManager.rotate(-f2 * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);

            // Turn the book on the Z axis by 80 degrees.
            GlStateManager.rotate(80.0F, 0.0F, 0.0F, 1.0F);

            // Bind the book's texture sheet
            this.bindTexture(TEXTURE_BOOK);

            float limbSwingAmount = te.pageFlipPrev + (te.pageFlip - te.pageFlipPrev) * partialTicks + 0.25F;
            float ageInTicks = te.pageFlipPrev + (te.pageFlip - te.pageFlipPrev) * partialTicks + 0.75F;
            limbSwingAmount = (limbSwingAmount - (float)MathHelper.fastFloor((double)limbSwingAmount)) * 1.6F - 0.3F;
            ageInTicks = (ageInTicks - (float)MathHelper.fastFloor((double)ageInTicks)) * 1.6F - 0.3F;

            MathHelper.clamp(limbSwingAmount, 0.0f, 1.0f);
            MathHelper.clamp(ageInTicks, 0.0f, 1.0f);

            float netHeadYaw = te.bookSpreadPrev + (te.bookSpread - te.bookSpreadPrev) * partialTicks;

            GlStateManager.enableCull();

            // Finally, render the book using the specified rotations
            this.modelBook.render(null, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, 0.0F, 0.0625F);
        }
        GlStateManager.popMatrix();
    }
}
