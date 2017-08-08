package joazlazer.mods.amc.api.capability.aura;

import javax.annotation.Nonnull;
import java.awt.Color;

public interface IAuraHandler {
    @Nonnull
    Color getColor();

    void setColor(Color color);
    float getAuraUnits();
    void setAuraUnits(float au);
    float getMaxAuraUnits();
    void setMaxAuraUnits(float maxAu);
    float transferAuraUnitsTo(float in, Color cIn);
    float transferAuraUnitsFrom(float request);
}
