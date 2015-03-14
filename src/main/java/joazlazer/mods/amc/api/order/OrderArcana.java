package joazlazer.mods.amc.api.order;

import joazlazer.mods.amc.reference.Textures;
import joazlazer.mods.amc.util.Color;
import joazlazer.mods.amc.util.GuiColor;

import java.util.ArrayList;

public class OrderArcana extends OrderBase {
    public OrderArcana() {
        super();
        this.setUnlocName("arcana");
        this.setId(0);
        this.setPracticer("Arcanist");
        ArrayList<String> tt = new ArrayList<String>();
        populateTooltip(tt);
        this.setTooltip(tt);
        ArrayList<String> infoText = new ArrayList<String>();
        populateInfoText(infoText);
        this.setInfoText(infoText);
        this.setTexture(Textures.Orders.ARCANA);
        this.setLargeTexture(Textures.Orders.ARCANA_LARGE);
        this.setColor(new Color(162, 96, 168, 255));
    }

    public void populateTooltip(ArrayList<String> tt) {
        tt.add(GuiColor.YELLOW + "\u00a7iOrder Arcana");
        tt.add("");
        tt.add(GuiColor.MAGENTA + " - The Arcanist:");
        tt.add(GuiColor.LIGHTGRAY + "  The arcanist uses the powers of the arcane");
        tt.add(GuiColor.LIGHTGRAY + "  to augment his spells and cast spells to manipulate ");
        tt.add(GuiColor.LIGHTGRAY + "  the world around him/her through the arcane arts.");
    }

    public void populateInfoText(ArrayList<String> infoText) {

    }
}
