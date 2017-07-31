package joazlazer.mods.amc.common.order;

import joazlazer.mods.amc.api.order.OrderBase;
import joazlazer.mods.amc.client.reference.Textures;
import joazlazer.mods.amc.common.reference.Reference;
import java.awt.Color;

public class OrderAlchimia extends OrderBase {
    public OrderAlchimia() {
        super();
        this.setUnlocName("alchimia");
        this.setRegistryName(Reference.MOD_ID, this.getUnlocName());
        this.setPracticer("Alchemist");
        this.setTexture(Textures.Orders.ALCHIMIA);
        this.setLargeTexture(Textures.Orders.ALCHIMIA_LARGE);
        this.setColor(new Color(72, 197, 193, 255));
    }
}
