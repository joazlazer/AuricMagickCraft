package joazlazer.mods.amc.common.tileentity;

import joazlazer.mods.amc.api.order.OrderBase;
import joazlazer.mods.amc.client.gui.GuiAwakeningScreen;
import joazlazer.mods.amc.common.handlers.NetworkHandler;
import joazlazer.mods.amc.common.network.MessageAwakeningControl;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IInteractionObject;

import java.util.Random;

/**
 * The floating book functionality is copied from TileEntityEnchantmentTable
 */
public class TileEntityAwakeningTable extends TileEntity implements ITickable, IInteractionObject {
    private static final double PLAYER_DISTANCE = 8.1d;
    public static final int AWAKENING_TICKS_MAX = 100;
    private static final int PARTICLE_SPAWN_FREQUENCY = 1;

    public int tickCount;
    public float pageFlip;
    public float pageFlipPrev;
    private float flipT;
    private float flipA;
    public float bookSpread;
    public float bookSpreadPrev;
    public float bookRotation;
    public float bookRotationPrev;
    private float tRot;
    public int awakeningTicks = -1;
    private static final Random rand = new Random();
    public OrderBase selectedOrder = null;

    public void awaken() {
        this.awakeningTicks = 0;
        NetworkHandler.INSTANCE.sendToServer(new MessageAwakeningControl(MessageAwakeningControl.ControlType.START));
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    public void update()
    {
        if(awakeningTicks >= AWAKENING_TICKS_MAX && this.world.isRemote) {
            if(Minecraft.getMinecraft().currentScreen instanceof GuiAwakeningScreen) ((GuiAwakeningScreen)Minecraft.getMinecraft().currentScreen).renderWhiteScreen();
            awakeningTicks = -1;
            NetworkHandler.INSTANCE.sendToServer(new MessageAwakeningControl(MessageAwakeningControl.ControlType.END, selectedOrder));
        }
        if(awakeningTicks != -1 && this.world.isRemote) {
            ++awakeningTicks;
            // Should particles be spawned on this tick and are we in the first 2/3 of the awakening ritual?
            if(awakeningTicks % PARTICLE_SPAWN_FREQUENCY == 0 && awakeningTicks <= (AWAKENING_TICKS_MAX - (AWAKENING_TICKS_MAX / 3))) {
                for (int i = -2; i <= 2; ++i) {
                    for (int j = -2; j <= 2; ++j) {
                        if (i > -2 && i < 2 && j == -1) {
                            j = 2;
                        }

                        if (rand.nextInt(16) == 0) {
                            for (int k = 0; k <= 1; ++k) {
                                if (!this.world.isAirBlock(pos.add(i / 2, 0, j / 2))){
                                    break;
                                }

                                this.world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, (double)pos.getX() + 0.5D, (double)pos.getY() + 2.0D, (double)pos.getZ() + 0.5D, (double)((float)i + rand.nextFloat()) - 0.5D, (double)((float)k - rand.nextFloat() - 1.0F), (double)((float)j + rand.nextFloat()) - 0.5D);
                            }
                        }
                    }
                }
                // Spawn particles
            }
        }

        this.bookSpreadPrev = this.bookSpread;
        this.bookRotationPrev = this.bookRotation;
        EntityPlayer entityplayer = this.world.getClosestPlayer((double)((float)this.pos.getX() + 0.5F), (double)((float)this.pos.getY() + 0.5F), (double)((float)this.pos.getZ() + 0.5F), PLAYER_DISTANCE, false);

        if (entityplayer != null) {
            double d0 = entityplayer.posX - (double)((float)this.pos.getX() + 0.5F);
            double d1 = entityplayer.posZ - (double)((float)this.pos.getZ() + 0.5F);
            this.tRot = (float) MathHelper.atan2(d1, d0);
            this.bookSpread += 0.1F;

            if (this.bookSpread < 0.5F || rand.nextInt(40) == 0) {
                float f1 = this.flipT;

                while (true) {
                    this.flipT += (float)(rand.nextInt(4) - rand.nextInt(4));

                    if (f1 != this.flipT) {
                        break;
                    }
                }
            }
        }
        else {
            this.tRot += 0.02F;
            this.bookSpread -= 0.1F;
        }

        while (this.bookRotation >= (float)Math.PI) {
            this.bookRotation -= ((float)Math.PI * 2F);
        }

        while (this.bookRotation < -(float)Math.PI) {
            this.bookRotation += ((float)Math.PI * 2F);
        }

        while (this.tRot >= (float)Math.PI) {
            this.tRot -= ((float)Math.PI * 2F);
        }

        while (this.tRot < -(float)Math.PI) {
            this.tRot += ((float)Math.PI * 2F);
        }

        float f2;

        for (f2 = this.tRot - this.bookRotation; f2 >= (float)Math.PI; f2 -= ((float)Math.PI * 2F)) {
        }

        while (f2 < -(float)Math.PI) {
            f2 += ((float)Math.PI * 2F);
        }

        this.bookRotation += f2 * 0.4F;
        this.bookSpread = MathHelper.clamp(this.bookSpread, 0.0F, 1.0F);
        ++this.tickCount;
        this.pageFlipPrev = this.pageFlip;
        float f = (this.flipT - this.pageFlip) * 0.4F;
        float f3 = 0.2F;
        f = MathHelper.clamp(f, -0.2F, 0.2F);
        this.flipA += (f - this.flipA) * 0.9F;
        this.pageFlip += this.flipA;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return null;
    }

    @Override
    public String getGuiID() {
        return "amc:awakening_table";
    }

    @Override
    public String getName() {
        return "amc:container.awakening_table";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }
}
