package joazlazer.mods.amc.api.order;

import joazlazer.mods.amc.reference.Textures;
import joazlazer.mods.amc.util.Color;
import joazlazer.mods.amc.util.GuiColor;

import java.util.ArrayList;

public class OrderAlchemia extends OrderBase {
    public OrderAlchemia() {
        super();
        this.setUnlocName("alchemia");
        this.setId(1);
        this.setPracticer("Alchemist");
        ArrayList<String> tt = new ArrayList<String>();
        populateTooltip(tt);
        this.setTooltip(tt);
        ArrayList<String> infoText = new ArrayList<String>();
        populateInfoText(infoText);
        this.setInfoText(infoText);
        this.setTexture(Textures.Orders.ALCHEMIA);
        this.setLargeTexture(Textures.Orders.ALCHEMIA_LARGE);
        this.setColor(new Color(72, 197, 193, 255));
    }

    public void populateTooltip(ArrayList<String> tt) {
        tt.add(GuiColor.YELLOW + "\u00a7iOrder Alchemia");
        tt.add("");
        tt.add(GuiColor.MAGENTA + " - The Alchemist:");
        tt.add(GuiColor.LIGHTGRAY + "  The alchemist transmutes the world around him/her");
        tt.add(GuiColor.LIGHTGRAY + "  and is highly skilled in the creation and brewing of");
        tt.add(GuiColor.LIGHTGRAY + "  potions, explosives, and all sorts of concoctions.");
    }

    public void populateInfoText(ArrayList<String> infoText) {

    }
}
