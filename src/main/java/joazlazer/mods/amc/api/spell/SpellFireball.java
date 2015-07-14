package joazlazer.mods.amc.api.spell;

import joazlazer.mods.amc.reference.Textures;
import joazlazer.mods.amc.util.Color;
import joazlazer.mods.amc.util.GuiColor;

import java.util.ArrayList;

public class SpellFireball extends SpellBase {
    public SpellFireball() {
        super();
        setUnlocName("spellFireball");
        setTextureLocation(Textures.Spells.FIREBALL);
        setLargeTexture(Textures.Spells.FIREBALL_LARGE);
        setColor(new Color(250, 98, 32));
        ArrayList<String> tt = new ArrayList<String>();
        populateTooltip(tt);
        this.setTooltip(tt);
    }

    public void populateTooltip(ArrayList<String> tt) {
        tt.add(GuiColor.MAGENTA + " - Fireball:");
        tt.add(GuiColor.LIGHTGRAY + "  Fires a small blast of elemental fire energy.");
        tt.add(GuiColor.LIGHTGRAY + "  One of the most basic spells, this can be cast");
        tt.add(GuiColor.LIGHTGRAY + "  by most mages and is often a starting spell.");
        tt.add(GuiColor.LIGHTGRAY + "  However, it is quite potent and should be used sparingly.");
        tt.add("");
        tt.add(GuiColor.TURQUISE + " - Cooldown: " + GuiColor.LIGHTGRAY + "Average");
        tt.add(GuiColor.PINK + " - Mana Cost: " + GuiColor.LIGHTGRAY + "Average");
        tt.add(GuiColor.YELLOW + " - Specialty? " + GuiColor.LIGHTGRAY + this.isSpecialty());
    }
}
