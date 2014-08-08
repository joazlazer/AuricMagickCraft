package joazlazer.mods.amc.client.render;

import cpw.mods.fml.client.FMLClientHandler;
import joazlazer.mods.amc.model.ModelAwakeningTable;
import joazlazer.mods.amc.reference.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemAwakeningTableRenderer implements IItemRenderer {

    private ModelAwakeningTable model;

    public ItemAwakeningTableRenderer() {
        model = new ModelAwakeningTable();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        Minecraft mc = Minecraft.getMinecraft();
        Float scale;
        switch (type) {

            case EQUIPPED:
                scale = 1f;
                GL11.glPushMatrix();
                GL11.glDisable(GL11.GL_LIGHTING);

                // Scale, Translate, Rotate
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(0.55F, 1.5F, 0.55F);
                GL11.glRotatef(180F, 1F, 0, 0);
                GL11.glRotatef(0F, 0, 1F, 0);
                GL11.glRotatef(0F, 0, 0, 1F);

                // Bind the texture.
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.Models.AWAKENING_TABLE);

                // Render
                model.renderModel(0.0625f);

                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glPopMatrix();
                break;

            case EQUIPPED_FIRST_PERSON:
                scale = 0.9f;
                GL11.glPushMatrix();
                GL11.glDisable(GL11.GL_LIGHTING);

                // Scale, Translate, Rotate
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(0F, 1.78F, 0.55F);
                GL11.glRotatef(0F, 1F, 0, 0);
                GL11.glRotatef(90F, 0, 1F, 0);
                GL11.glRotatef(180F, 0, 0, 1F);

                // Bind the texture.
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.Models.AWAKENING_TABLE);

                // Render
                model.renderModel(0.0625f);

                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glPopMatrix();

                break;

            case ENTITY:
                GL11.glPushMatrix();
                GL11.glDisable(GL11.GL_LIGHTING);

                // Scale, Translate, Rotate
                GL11.glScalef(1F, 1F, 1F);
                GL11.glTranslatef(0, 1.05f, 0);
                GL11.glRotatef(0, 1F, 0, 0);
                GL11.glRotatef(180f, 0, 0, 1f);

                // Bind the texture.
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.Models.AWAKENING_TABLE);

                // Render
                model.renderModel(0.0625f);

                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glPopMatrix();
                break;

            case INVENTORY:
                GL11.glPushMatrix();
                GL11.glDisable(GL11.GL_LIGHTING);

                // Scale, Translate, Rotate
                GL11.glScalef(1f, 1F, 1F);
                GL11.glTranslatef(0, 1.0f, 0);
                GL11.glRotatef(180F, 1F, 0, 0);

                // Bind the texture.
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.Models.AWAKENING_TABLE);

                // Render
                model.renderModel(0.0625f);

                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glPopMatrix();
                break;

            default:
                break;
        }

    }
}
