package joazlazer.mods.amc.api.order;

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
    }

    public void populateTooltip(ArrayList<String> tt) {

    }

    public void populateInfoText(ArrayList<String> infoText) {

    }
}
