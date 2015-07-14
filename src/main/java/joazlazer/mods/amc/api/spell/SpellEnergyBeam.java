package joazlazer.mods.amc.api.spell;

import joazlazer.mods.amc.reference.Textures;
import joazlazer.mods.amc.util.Color;
import joazlazer.mods.amc.util.GuiColor;
import java.util.ArrayList;

public class SpellEnergyBeam extends SpellBase {
    public SpellEnergyBeam() {
        super();
        setUnlocName("spellEnergyBeam");
        setTextureLocation(Textures.Spells.ENERGY_BEAM);
        setLargeTexture(Textures.Spells.ENERGY_BEAM_LARGE);
        setColor(new Color(255, 220, 0));
        ArrayList<String> tt = new ArrayList<String>();
        populateTooltip(tt);
        this.setTooltip(tt);
    }

    public void populateTooltip(ArrayList<String> tt) {
        tt.add(GuiColor.MAGENTA + " - Energy Beam:");
        tt.add(GuiColor.LIGHTGRAY + "  Fires a small blast of arcane energy");
        tt.add(GuiColor.LIGHTGRAY + "  One of the most basic spells, this can be cast");
        tt.add(GuiColor.LIGHTGRAY + "  by most mages and is often a starting spell.");
        tt.add(GuiColor.LIGHTGRAY + "  Unlike the fireball, this has a very small effect.");
        tt.add("");
        tt.add(GuiColor.TURQUISE + " - Cooldown: " + GuiColor.LIGHTGRAY + "Average");
        tt.add(GuiColor.PINK + " - Mana Cost: " + GuiColor.LIGHTGRAY + "Low");
        tt.add(GuiColor.YELLOW + " - Specialty? " + GuiColor.LIGHTGRAY + this.isSpecialty());
    }
}
