package joazlazer.mods.amc.common.order;

import joazlazer.mods.amc.api.order.OrderBase;
import joazlazer.mods.amc.client.reference.Textures;
import joazlazer.mods.amc.common.reference.Reference;
import java.awt.Color;

public class OrderArcana extends OrderBase {
    public OrderArcana() {
        super();
        this.setUnlocName("arcana");
        this.setRegistryName(Reference.MOD_ID, this.getUnlocName());
        this.setPracticer("elementalist");
        this.setTexture(Textures.Orders.ARCANA);
        this.setLargeTexture(Textures.Orders.ARCANA_LARGE);
        this.setColor(new Color(162, 96, 168, 255));
    }
}
