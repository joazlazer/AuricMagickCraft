package joazlazer.mods.amc.api.spell;

import com.google.gson.JsonSyntaxException;
import joazlazer.mods.amc.api.order.OrderAlchemia;
import joazlazer.mods.amc.api.order.OrderArcana;
import joazlazer.mods.amc.casting.CastingManager;
import joazlazer.mods.amc.casting.CastingStatus;
import joazlazer.mods.amc.casting.client.ClientCastingStatus;
import joazlazer.mods.amc.client.render.RenderHelper;
import joazlazer.mods.amc.handlers.KeyHandler;
import joazlazer.mods.amc.reference.Shaders;
import joazlazer.mods.amc.reference.Textures;
import joazlazer.mods.amc.util.Color;
import joazlazer.mods.amc.util.GuiColor;
import joazlazer.mods.amc.util.LogHelper;
import joazlazer.mods.amc.util.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.util.JsonException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SpellBlink extends SpellBase {
    public static final String DURING_CAST_STATE_KEY = "duringCastStateKey";
    public static ShaderGroup grayscale;
    public static Minecraft mc;

    public static class BlinkStatus {
        private boolean casting;

        public BlinkStatus setCasting(boolean newValue) {
            casting = newValue;
            return this;
        }

        public boolean getCasting() {
            return casting;
        }
    }

    public static HashMap<String, BlinkStatus> Blinks = new HashMap<String, BlinkStatus>();

    public SpellBlink() {
        super();
        setUnlocName("spellBlink");
        setTextureLocation(Textures.Spells.BLINK);
        setLargeTexture(Textures.Spells.BLINK_LARGE);
        setColor(new Color(73, 150, 36));
        ArrayList<String> tt = new ArrayList<String>();
        populateTooltip(tt);
        this.setTooltip(tt);
        mc = Minecraft.getMinecraft();
        /* try {
            grayscale = new ShaderGroup(mc.getTextureManager(), (IResourceManager) ReflectionHelper.getPrivateField(EntityRenderer.class, mc.entityRenderer, "resourceManager"), Minecraft.getMinecraft().getFramebuffer(), Shaders.GRAYSCALE);
            grayscale.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
        } catch (JsonException e) {
            e.printStackTrace();
        } */
    }

    public void populateTooltip(ArrayList<String> tt) {
        tt.add(GuiColor.MAGENTA + " - Blink:");
        tt.add(GuiColor.LIGHTGRAY + "  A spell used for short-distance teleportation.");
        tt.add(GuiColor.LIGHTGRAY + "  One of the most basic spells, it is known by most ");
        tt.add(GuiColor.LIGHTGRAY + "  mages. It is cast by concentrating on where");
        tt.add(GuiColor.LIGHTGRAY + "  one wants to be and it will be so.");
        tt.add("");
        tt.add(GuiColor.TURQUISE + " - Cooldown: " + GuiColor.LIGHTGRAY + "Low");
        tt.add(GuiColor.PINK + " - Mana Cost: " + GuiColor.LIGHTGRAY + "Very Low");
        tt.add(GuiColor.YELLOW + " - Specialty? " + GuiColor.LIGHTGRAY + this.isSpecialty());
    }

    @Override
    public void onServerCastTick(EntityPlayer player, World world, CastingStatus status) {
        System.out.print(".");
    }

    @Override
    public void onStartCasting(EntityPlayer player, World world, CastingStatus status) {
        System.out.println("Blink onStartCasting(EntityPlayer, World, CastingStatus) called. player: " + player + "; username: " + player.getDisplayName() + "; world: " + world + "; status: " + status);
        if (Blinks.containsKey(player.getDisplayName()) && Blinks.get(player.getDisplayName()).getCasting()) {
            Blinks.put(player.getDisplayName(), new BlinkStatus().setCasting(false));
            System.out.println("We are casting, so turn it off");
        } else {
            Blinks.put(player.getDisplayName(), new BlinkStatus().setCasting(true));
            System.out.println("Set casting to true");
            System.out.println();
            status.receiveTick = true;
            NBTTagCompound nbt = status.customNBT;
            nbt.setByte("stage", (byte) 1);
            CastingManager.updateToAll(player.getDisplayName());
        }
    }

    @Override
    public void onCancelCasting(EntityPlayer player, World world, CastingStatus status) {
        Blinks.put(player.getDisplayName(), new BlinkStatus().setCasting(false));
        status.receiveTick = false;
        NBTTagCompound nbt = status.customNBT;
        nbt.setByte("stage", (byte) 0);
        CastingManager.updateToAll(player.getDisplayName());
    }

    @Override
    public void onInterruptCasting(EntityPlayer player, World world, CastingStatus status) {
        Blinks.put(player.getDisplayName(), new BlinkStatus().setCasting(false));
        status.receiveTick = false;
    }

    @Override
    public void onStopCasting(EntityPlayer player, World world, CastingStatus status) {
        if (!Blinks.get(player.getDisplayName()).getCasting()) {
            status.receiveTick = false;
            // Teleport the player here.
            NBTTagCompound nbt = status.customNBT;
            nbt.setByte("stage", (byte) 2);
            CastingManager.updateToAll(player.getDisplayName());
        }
    }

    @Override
    public void renderThirdPerson(World world, float partialTicks, ClientCastingStatus status) {
        RenderHelper.drawLargeOrderIcon(56, 56, new OrderArcana());
    }

    @Override
    public void renderFirstPerson(World world, float partialTicks, ClientCastingStatus status) {

        int stage = status.customNBT.getByte("stage");
        if (stage == 0) {
            RenderHelper.drawLargeOrderIcon(56, 56, new OrderAlchemia());
            if (grayscale != null) grayscale = null;
        } else if (stage == 1) {
            if(!KeyHandler.moving) {
                if (grayscale == null) {
                    try {
                        grayscale = new ShaderGroup(mc.getTextureManager(), (IResourceManager) ReflectionHelper.getPrivateField(EntityRenderer.class, mc.entityRenderer, "resourceManager"), Minecraft.getMinecraft().getFramebuffer(), Shaders.GRAYSCALE);
                    } catch (JsonException e) {
                        e.printStackTrace();
                    }
                    grayscale.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
                } else {
                    RenderHelper.drawLargeSpellIcon(56, 56, new SpellWaterWhip(), 255);
                    GL11.glMatrixMode(GL11.GL_TEXTURE);
                    GL11.glPushMatrix();
                    GL11.glLoadIdentity();
                    grayscale.loadShaderGroup(partialTicks);
                    GL11.glPopMatrix();
                    mc.getFramebuffer().bindFramebuffer(true);
                }
            } else {
                grayscale = null;
            }
        } else if (stage == 2) {
            if (grayscale != null) grayscale = null;
            RenderHelper.drawLargeSpellIcon(56, 56, new SpellEnergyBall(), 255);
        }
        /*Minecraft mc = Minecraft.getMinecraft();
        if (OpenGlHelper.shadersSupported) {
            try {

            } catch (JsonSyntaxException jsonsyntaxexception) {
                LogHelper.warn("Failed to load shader: " + Shaders.GRAYSCALE);
            }
        } */
    }

    @Override
    public void renderOtherPlayer(World world, float partialTicks, ClientCastingStatus status) {

    }
}
