package joazlazer.mods.amc.api.capability.progression;

import joazlazer.mods.amc.api.order.OrderBase;

public interface IAmcProgressionHandler {
    boolean getIsAwakened();
    OrderBase getOrder();
    void setAwakened(boolean b);
    void setOrder(OrderBase ob);
}
