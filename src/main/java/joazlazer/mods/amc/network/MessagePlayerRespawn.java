package joazlazer.mods.amc.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.casting.CastingManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldServer;

public class MessagePlayerRespawn implements IMessage {
    public String username;

    public MessagePlayerRespawn() {
        // Provide this for the reflection used in MCF/FML3
    }

    public MessagePlayerRespawn(String user) {
        this.username = user;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.username = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, username);
    }

    public static class Handler implements IMessageHandler<MessagePlayerRespawn, IMessage> {

        @Override
        public IMessage onMessage(MessagePlayerRespawn message, MessageContext ctx) {
            // Get the entity.
            EntityLivingBase entityLiving = MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(message.username);

            // Make sure the entity specified is a player.
            if (entityLiving instanceof EntityPlayerMP) {
                // Cast the entity to a player.
                EntityPlayerMP entityplayermp = (EntityPlayerMP) entityLiving;

                // Get the chunk coordinates of the bed int the specified dimension.
                ChunkCoordinates chunkcoordinates = entityplayermp.getBedLocation(entityplayermp.dimension);

                // Create integers to store the new, manipulated coordinates to respawn the player at.
                double newX = 0;
                double newY = 0;
                double newZ = 0;

                // Get the world server for the specified player's dimension.
                WorldServer worldserver = MinecraftServer.getServer().worldServerForDimension(entityplayermp.dimension);

                // Create a new chunk coordinate location.
                ChunkCoordinates chunkcoordinates1;

                // Create a flag to use.
                boolean flag = false;

                // If the player has a bed.
                if (chunkcoordinates != null)
                {
                    // Set the second chunk coordinates to the verified respawn coordinates.
                    chunkcoordinates1 = EntityPlayer.verifyRespawnCoordinates(MinecraftServer.getServer().worldServerForDimension(entityplayermp.dimension), chunkcoordinates, flag);

                    // If the second coordinates aren't null,
                    if (chunkcoordinates1 != null)
                    {
                        // Set the spawn -2 (to counteract what's added later.)
                        chunkcoordinates1.posY -= 2;

                        // Set the spawn chunk.
                        entityplayermp.setSpawnChunk(chunkcoordinates, flag);
                    }
                    else
                    {
                        // Get the spawn chunk.
                        chunkcoordinates1 = worldserver.getSpawnPoint();
                    }
                }
                else
                {
                    // Get the spawn chunk.
                    chunkcoordinates1 = worldserver.getSpawnPoint();
                }

                // Set the coordinates to the calculated coordinates.
                newX = (double)((float)chunkcoordinates1.posX + 0.5f);
                newY = (double)((float)chunkcoordinates1.posY + 0.5f);
                newZ = (double)((float)chunkcoordinates1.posZ + 0.5f);

                // Load the chunk.
                worldserver.theChunkProviderServer.loadChunk((int)entityplayermp.posX >> 4, (int)entityplayermp.posZ >> 4);

                // Dismount the player.
                entityplayermp.mountEntity((Entity)null);

                // Teleport the player's position to the new respawn location.
                entityplayermp.setPositionAndUpdate(newX, newY, newZ);

                // Make sure the player isn't gonna suffocate.
                while (!worldserver.getCollidingBoundingBoxes(entityplayermp, entityplayermp.boundingBox).isEmpty())
                {
                    // Set the player's new position to one up from the old one.
                    entityplayermp.setPosition(entityplayermp.posX, entityplayermp.posY + 1.0D, entityplayermp.posZ);
                }
            }
            return null;
        }
    }
}
