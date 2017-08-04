package joazlazer.mods.amc.client.gui;

import joazlazer.mods.amc.common.tileentity.TileEntityAwakeningTable;
import joazlazer.mods.amc.common.utility.EntityUtils;
import joazlazer.mods.amc.common.utility.MathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.IOException;

public class GuiAwakeningScreen extends GuiScreen {
    private static double Y_OFFSET = 0.725d;
    private static final float SHAKE_TICKS = 1f;
    private static final float SHAKE_MIN = 0.2f;
    private static final float SHAKE_MAX = 5f;

    private final float yawTarget;
    private final float pitchTarget;
    private final float yawInitial;
    private final float pitchInitial;
    private float yawShakeTarget;
    private float pitchShakeTarget;
    private float previousTickPosition = 50f;

    TileEntityAwakeningTable te = null;

    public GuiAwakeningScreen(TileEntityAwakeningTable te) {
        this.te = te;
        Vec3d vec = new Vec3d(te.getPos().getX() + 0.5d, te.getPos().getY() + 0.5d + Y_OFFSET, te.getPos().getZ() + 0.5d);
        EntityPlayer player = Minecraft.getMinecraft().player;
        yawTarget = EntityUtils.getYawTowards(player, vec);
        pitchTarget = EntityUtils.getPitchTowards(player, vec);
        yawInitial = player.rotationYaw;
        pitchInitial = player.rotationPitch;
        Minecraft.getMinecraft().mouseHelper.grabMouseCursor();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if(keyCode == 1) {
            // Esc pressed, kill player
            System.out.println("Dead");
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int awakeningTicks = te.awakeningTicks;
        final int ticksMax = TileEntityAwakeningTable.AWAKENING_TICKS_MAX;
        float currentAlpha = (awakeningTicks + partialTicks) / (float)ticksMax;

        float shakeAmount = MathHelper.lerp(SHAKE_MIN, SHAKE_MAX, currentAlpha);

        final float fadeInLimit = 0.5f;
        currentAlpha = 1.0f - MathHelper.clamp(currentAlpha, 0.0f, 1.0f);
        currentAlpha = 1.0f - MathHelper.limitedSinInterp(0.0f, 1.0f, fadeInLimit, currentAlpha);
        int color = new Color(255, 255, 255, (int)(currentAlpha * 255f)).getRGB();
        this.drawGradientRect(0, 0, this.width, this.height, color, color);

        // should be 25
        final int quarterMax = ticksMax / 4;
        float newYaw = yawTarget;
        float newPitch = pitchTarget;

        // [0,25] ->
        if(awakeningTicks != -1 && awakeningTicks <= quarterMax) {
            // [0.0f,1.0f] ->
            float mu = MathHelper.clamp(((float)awakeningTicks + partialTicks) / (float)quarterMax, 0.0f, 1.0f);
            // [0.0f,1.0f] -> along sine curve
            float sinMu = MathHelper.sinInterp(0.0f, 1.0f, mu);
            newYaw = MathHelper.lerpYaw(yawInitial, yawTarget, sinMu);
            newPitch = MathHelper.lerp(pitchInitial, pitchTarget, sinMu);
        }

        // Apply screen shake
        if(awakeningTicks != -1) {
            float position = ((float)awakeningTicks + partialTicks) % SHAKE_TICKS;
            if(previousTickPosition > position) {
                Point2D newRotation = MathHelper.randomPointInCircle(shakeAmount);
                yawShakeTarget = (float)newRotation.getX();
                pitchShakeTarget = (float)newRotation.getY();
            }

            float alpha = position / SHAKE_TICKS;
            float peakedMu = MathHelper.middlePeakInterp(0.0f, 1.0f, alpha);
            float yawOffset = MathHelper.lerpYaw(0.0f, yawShakeTarget, peakedMu);
            float pitchOffset = MathHelper.lerp(0.0f, pitchShakeTarget, peakedMu);
            newYaw += yawOffset;
            newPitch += pitchOffset;
            previousTickPosition = position;
        }

        // Apply new yaw and pitch to the player's camera
        EntityUtils.setRotation(Minecraft.getMinecraft().player, newYaw, newPitch);
    }


    public void finalizeAwakening() {
        // TODO Begin fadeout
        Minecraft.getMinecraft().displayGuiScreen(null);
    }
}
