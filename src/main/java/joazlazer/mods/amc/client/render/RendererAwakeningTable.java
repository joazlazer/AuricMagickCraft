package joazlazer.mods.amc.client.render;

import joazlazer.mods.amc.model.ModelAwakeningTable;
import joazlazer.mods.amc.reference.Textures;
import joazlazer.mods.amc.tileentity.TileEntityAwakeningTable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class RendererAwakeningTable extends TileEntitySpecialRenderer {

    private ModelAwakeningTable model;
    private ModelBook hoveringBook = new ModelBook();
    private static final ResourceLocation bookTexture = new ResourceLocation("textures/entity/enchanting_table_book.png");

    public RendererAwakeningTable() {
        // Initialize the model object.
        this.model = new ModelAwakeningTable();
    }

    @Override
    public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {

        // ******************
        // RENDER TABLE
        // ******************

        // Push a new matrix to the stack.
        GL11.glPushMatrix();

        // Prepare the parent matrix.
        {
            // Translate the rendering position by 1/2 block in each axis, plus the correct rendering position.
            GL11.glTranslatef((float) x + 0.5f, (float) y + 0.5f, (float) z + 0.5f);

            // Rotate the rendering position for the model and flip it around.
            GL11.glRotatef(180f, 0f, 0f, 1f);

            // Translate the rendering position by 1 block up.
            GL11.glTranslatef(0.0f, -1.0f, 0.0f);

            // Bind the current rendering texture to the resource location of the model texture.
            this.bindTexture(Textures.Models.AWAKENING_TABLE);

            // Push a new matrix to the stack.
            GL11.glPushMatrix();;

            // Render the awakening table.
            {
                this.model.renderModel(0.0625f);
            }

            // Pop the child matrix from the stack.
            GL11.glPopMatrix();
        }

        // Pop the matrix from the stack.
        GL11.glPopMatrix();

        // ****************
        // RENDER BOOK
        // ****************

        // Grab the block above.
        World world = Minecraft.getMinecraft().theWorld;
        int blockX = entity.xCoord;
        int blockY = entity.yCoord + 1;
        int blockZ = entity.zCoord;

        // Determine whether to render the book.
        boolean shouldRenderBook = !world.getBlock(blockX, blockY, blockZ).isBlockSolid(world, blockX, blockY, blockZ, 0) && !world.getBlock(blockX, blockY, blockZ).isOpaqueCube();

        // If we should, render the book.
        if(shouldRenderBook) {

            // Grab an instance of the tile entity.
            TileEntityAwakeningTable te = (TileEntityAwakeningTable) entity;

            // Push a new matrix on to the stack.
            GL11.glPushMatrix();

            // Render the book (Source copied from RenderEnchantmentTable.java).
            {
                // Translate the rendering position by 1/2 block in each axis, plus the correct rendering position.
                GL11.glTranslatef((float) x + 0.5F, (float) y + 1.0F, (float) z + 0.5F);

                // Calculate the number used to calculate the hovering position (the input for the Sine function).
                float f1 = (float) te.field_145926_a + f;

                // Translate to the correct hovering position.
                GL11.glTranslatef(0.0F, 0.1F + MathHelper.sin(f1 * 0.1F) * 0.01F, 0.0F);

                // Create a variable used to *calculate rotation?
                float f2;

                // I can think of a hundred better ways to do this.
                for (f2 = te.field_145928_o - te.field_145925_p; f2 >= (float) Math.PI; f2 -= ((float) Math.PI * 2F)) {
                    ;
                }

                // Calculate rotation?
                while (f2 < -(float) Math.PI) {
                    f2 += ((float) Math.PI * 2F);
                }

                // More rotation.
                float f3 = te.field_145925_p + f2 * f;

                // glRotate by the calculated value.
                GL11.glRotatef(-f3 * 180.0F / (float) Math.PI, 0.0F, 1.0F, 0.0F);

                // Turn the book on the Z axis by 80 degrees.
                GL11.glRotatef(80.0F, 0.0F, 0.0F, 1.0F);

                // Bind the correct texture.
                this.bindTexture(bookTexture);

                // Idk
                float f4 = te.field_145931_j + (te.field_145933_i - te.field_145931_j) * f + 0.25F;
                float f5 = te.field_145931_j + (te.field_145933_i - te.field_145931_j) * f + 0.75F;

                // Wtf is truncate?
                f4 = (f4 - (float) MathHelper.truncateDoubleToInt((double) f4)) * 1.6F - 0.3F;
                f5 = (f5 - (float) MathHelper.truncateDoubleToInt((double) f5)) * 1.6F - 0.3F;

                // :P
                if (f4 < 0.0F) {
                    f4 = 0.0F;
                }

                if (f5 < 0.0F) {
                    f5 = 0.0F;
                }

                if (f4 > 1.0F) {
                    f4 = 1.0F;
                }

                if (f5 > 1.0F) {
                    f5 = 1.0F;
                }

                // Still, idk.
                float f6 = te.field_145927_n + (te.field_145930_m - te.field_145927_n) * f;

                // Enable face culling! (Wtf is face culling?!?)
                GL11.glEnable(GL11.GL_CULL_FACE);

                // Finally render the book, with all of the crazily, unnecessary values! :D
                this.hoveringBook.render((Entity) null, f1, f4, f5, f6, 0.0F, 0.0625F);
            }

            // Pop the matrix from the stack.
            GL11.glPopMatrix();

        }
    }
}
