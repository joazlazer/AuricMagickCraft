package joazlazer.mods.amc.common.order;

import joazlazer.mods.amc.api.order.OrderBase;
import joazlazer.mods.amc.client.reference.Textures;
import joazlazer.mods.amc.common.reference.Reference;
import java.awt.Color;

public class OrderPraesidia extends OrderBase {
    public OrderPraesidia() {
        super();
        this.setUnlocName("praesidia");
        this.setRegistryName(Reference.MOD_ID, this.getUnlocName());
        this.setPracticer("warder");
        this.setTexture(Textures.Orders.PRAESIDIA);
        this.setLargeTexture(Textures.Orders.PRAESIDIA_LARGE);
        this.setColor(new Color(168, 86, 53, 255));
    }
}
