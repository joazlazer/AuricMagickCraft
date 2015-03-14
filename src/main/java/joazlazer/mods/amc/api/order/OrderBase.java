package joazlazer.mods.amc.api.order;

import joazlazer.mods.amc.api.spell.SpellBase;
import joazlazer.mods.amc.util.Color;
import net.minecraft.util.ResourceLocation;
import java.util.ArrayList;

public class OrderBase {
    public ArrayList<Integer> specialtyIndexes;
    // The texture path for the icon.
    private ResourceLocation texture;
    private ResourceLocation largeTexture;
    private ArrayList<String> infoText;
    private ArrayList<String> tooltip;
    private ArrayList<SpellBase> spells;
    // The unlocalized name for the order.
    private String unlocName;
    private String practicer;
    private int id;
    private Color color;

    public OrderBase() {
        infoText = new ArrayList<String>();
        tooltip = new ArrayList<String>();
        specialtyIndexes = new ArrayList<Integer>();
        spells = new ArrayList<SpellBase>();
        color = new Color();
    }

    public ArrayList<SpellBase> getSpells() {
        return spells;
    }

    public void setSpells(ArrayList<SpellBase> spells) {
        this.spells = spells;
    }

    public ResourceLocation getLargeTexture() {
        return largeTexture;
    }

    public void setLargeTexture(ResourceLocation largeTexture) {
        this.largeTexture = largeTexture;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public OrderBase setId(int id) {
        this.id = id;
        return this;
    }

    public String getPracticer() {
        return practicer;
    }

    public OrderBase setPracticer(String practicer) {
        this.practicer = practicer;
        return this;
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.getUnlocName();
    }

    public ArrayList<String> getInfoText() {
        return infoText;
    }

    public OrderBase setInfoText(ArrayList<String> infoText) {
        this.infoText = infoText;
        return this;
    }

    public ArrayList<String> getTooltip() {
        return tooltip;
    }

    public OrderBase setTooltip(ArrayList<String> tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    public String getUnlocName() {
        // Return the unlocalized name.
        return unlocName;
    }

    public OrderBase setUnlocName(String newName) {
        // Change the unlocalized name to the new one.
        unlocName = newName;
        return this;
    }

    public ResourceLocation getTexture() {
        // Return the texture location.
        return texture;
    }

    public OrderBase setTexture(ResourceLocation newLoc) {
        // Change the texture location to the new one.
        texture = newLoc;
        return this;
    }

    public void addSpecialties() {
        // Iterate through the different spells.
        for (int i = 0; i < spells.size(); i++) {
            // If the current spell is a specialty,
            if (spells.get(i).isSpecialty()) {
                // Then add its index to the specialties array.
                specialtyIndexes.add(i);
            }
        }
    }
}
