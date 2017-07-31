package joazlazer.mods.amc.common.order;

import joazlazer.mods.amc.api.order.OrderBase;
import joazlazer.mods.amc.client.reference.Textures;
import joazlazer.mods.amc.common.reference.Reference;
import java.awt.Color;

public class OrderIracundia extends OrderBase {
    public OrderIracundia() {
        super();
        this.setUnlocName("iracundia");
        this.setRegistryName(Reference.MOD_ID, this.getUnlocName());
        this.setPracticer("warlock");
        this.setTexture(Textures.Orders.IRACUNDIA);
        this.setLargeTexture(Textures.Orders.IRACUNDIA_LARGE);
        this.setColor(new Color(168, 50, 53, 255));
    }
}
