package joazlazer.mods.amc.api.capability.progression;

import joazlazer.mods.amc.api.order.OrderBase;
import net.minecraft.nbt.NBTBase;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * Default implementation of <code>IAmcProgressionHandler</code> intended for entity attachment
 */
public class EntityAmcProgressionHandler implements IAmcProgressionHandler, INBTSerializable<NBTBase> {
    private boolean isAwakened;
    private OrderBase orderBase;
    public EntityAmcProgressionHandler() {
        isAwakened = false;
        orderBase = null;
    }

    @Override
    public NBTBase serializeNBT() {
        return CapabilityAmcProgressionHandler.serialize(this);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        CapabilityAmcProgressionHandler.deserialize(this, nbt);
    }

    @Override
    public boolean getIsAwakened() {
        return isAwakened;
    }

    @Override
    public OrderBase getOrder() {
        return orderBase;
    }

    @Override
    public void setAwakened(boolean awakened) {
        isAwakened = awakened;
    }

    @Override
    public void setOrder(OrderBase ob) {
        orderBase = ob;
    }

    @Override
    public String toString() {
        return String.format("EntityAmcProgressionHandler[isAwakened:%s,order:%s]", String.valueOf(getIsAwakened()), String.valueOf(this.orderBase));
    }
}
