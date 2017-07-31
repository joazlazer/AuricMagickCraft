package joazlazer.mods.amc.common.order;

import joazlazer.mods.amc.api.order.OrderBase;
import joazlazer.mods.amc.client.reference.Textures;
import joazlazer.mods.amc.common.reference.Reference;
import java.awt.Color;

public class OrderPraecanta extends OrderBase {
    public OrderPraecanta() {
        super();
        this.setUnlocName("praecanta");
        this.setRegistryName(Reference.MOD_ID, this.getUnlocName());
        this.setPracticer("sorcerer");
        this.setTexture(Textures.Orders.PRAECANTA);
        this.setLargeTexture(Textures.Orders.PRAECANTA_LARGE);
        this.setColor(new Color(132, 168, 78, 255));
    }
}
