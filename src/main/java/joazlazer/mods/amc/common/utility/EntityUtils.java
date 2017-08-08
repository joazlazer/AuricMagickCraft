package joazlazer.mods.amc.common.utility;

import net.minecraft.command.server.CommandTeleport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.*;

public class EntityUtils {
    public static boolean respawnPlayerToBed(EntityPlayerMP player) {
        int dim = player.dimension;
        World world = player.getServer().getWorld(dim);

        if(!world.provider.canRespawnHere()) {
            dim = world.provider.getRespawnDimension(player);
        }

        BlockPos bedPos = player.getBedLocation(dim);
        boolean flag = player.isSpawnForced(dim);
        player.dimension = dim;

        if (bedPos != null && EntityPlayer.getBedSpawnLocation(player.getServer().getWorld(dim), bedPos, flag) != null) {
            BlockPos bedSpawnLoc = EntityPlayer.getBedSpawnLocation(player.getServer().getWorld(dim), bedPos, flag);
            if (bedSpawnLoc != null) {
                EntityUtils.teleportPlayer(player, MathHelper.toVec3d(bedSpawnLoc).add(new Vec3d(0.5d, 0.1d, 0.5d)), player.rotationYaw, 0);
                return true;
            }
        }

        return false;
    }


    public static void teleportPlayer(EntityPlayer player, Vec3d to) {
        teleportPlayer(player, to, player.rotationYaw, player.rotationPitch);
    }


    public static void teleportPlayer(EntityPlayer player, Vec3d to, float yaw, float pitch) {
        if (player instanceof EntityPlayerMP) {
            Set<SPacketPlayerPosLook.EnumFlags> set = EnumSet.<SPacketPlayerPosLook.EnumFlags>noneOf(SPacketPlayerPosLook.EnumFlags.class);
            player.dismountRidingEntity();
            ((EntityPlayerMP)player).connection.setPlayerLocation(to.x, to.y, to.z, MathHelper.wrapScale(yaw, -180f, 180f), MathHelper.clamp(pitch, -90f, 90f), set);
            player.setRotationYawHead(yaw);
        } else {
            player.setLocationAndAngles(to.x, to.y, to.z, MathHelper.wrapScale(yaw, -180f, 180f), MathHelper.clamp(pitch, -90f, 90f));
            player.setRotationYawHead(yaw);
        }

        if (!(player instanceof EntityLivingBase) || !((EntityLivingBase)player).isElytraFlying()) {
            player.motionY = 0.0D;
            player.onGround = true;
        }
    }

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
    public static void setRotation(Entity entity, float yaw, float pitch) {
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
