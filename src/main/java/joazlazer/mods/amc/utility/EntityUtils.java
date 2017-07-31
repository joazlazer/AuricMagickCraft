package joazlazer.mods.amc.utility;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityUtils {
    @SideOnly(Side.CLIENT)
    public static void lookAt(EntityPlayer player, Vec3d lookat) {
        player.setLocationAndAngles(player.posX, player.posY, player.posZ, getYawTowards(player, lookat), getPitchTowards(player, lookat));
    }

    public static float getYawTowards(EntityPlayer player, Vec3d towards) {
        Vec3d eyePos = player.getPositionVector().add(new Vec3d(0f, player.getEyeHeight(), 0f));
        Vec3d difference = towards.subtract(eyePos);
        Vec3d normal = difference.normalize();

        return (float) -Math.toDegrees(Math.atan2(normal.x, normal.z));
    }

    public static float getPitchTowards(EntityPlayer player, Vec3d towards) {
        Vec3d eyePos = player.getPositionVector().add(new Vec3d(0f, player.getEyeHeight(), 0f));
        Vec3d difference = towards.subtract(eyePos);
        Vec3d normal = difference.normalize();

        double xz = Math.sqrt(normal.x * normal.x + normal.z * normal.z);
        return (float) -Math.toDegrees(Math.atan2(normal.y, xz));
    }

    /**
     * Sets position, clamping and wrapping params to valid values.
     * Basically a slimmed down copy of Entity.setPositionAndRotation(...)
     */
    public static void setRotation(Entity entity, float yaw, float pitch)
    {
        pitch = MathHelper.clamp(pitch, -90.0F, 90.0F);
        entity.rotationYaw = yaw;
        entity.rotationPitch = pitch;
        entity.prevRotationYaw = entity.rotationYaw;
        entity.prevRotationPitch = entity.rotationPitch;
        double d0 = (double)(entity.prevRotationYaw - yaw);

        if (d0 < -180.0D)
        {
            entity.prevRotationYaw += 360.0F;
        }

        if (d0 >= 180.0D)
        {
            entity.prevRotationYaw -= 360.0F;
        }

        entity.rotationYaw = yaw % 360.0F;
        entity.rotationPitch = pitch % 360.0F;
    }
}
