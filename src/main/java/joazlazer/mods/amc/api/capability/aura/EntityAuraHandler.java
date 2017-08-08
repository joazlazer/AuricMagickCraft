package joazlazer.mods.amc.api.capability.aura;

import com.github.lzyzsd.randomcolor.RandomColor;
import net.minecraft.nbt.NBTBase;
import net.minecraftforge.common.util.INBTSerializable;
import javax.annotation.Nonnull;
import java.awt.Color;

/**
 * Default implementation of <code>IAuraHandler</code> intended for entity attachment
 */
public class EntityAuraHandler implements IAuraHandler, INBTSerializable<NBTBase> {
    private Color color;
    private float au;
    private float maxAu;

    public EntityAuraHandler() {
        color = generateColor();
        au = 4000.0f;
        maxAu = 4000.0f;
    }

    @Override
    public NBTBase serializeNBT() {
        return CapabilityAuraHandler.serialize(this);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        CapabilityAuraHandler.deserialize(this, nbt);
    }

    @Override
    @Nonnull
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(@Nonnull Color color) {
        this.color = color;
    }

    @Override
    public float getAuraUnits() {
        return au;
    }

    @Override
    public void setAuraUnits(float au) {
        this.au = au;
    }

    @Override
    public float getMaxAuraUnits() {
        return maxAu;
    }

    @Override
    public void setMaxAuraUnits(float maxAu) {
        this.maxAu = maxAu;
    }

    @Override
    public float transferAuraUnitsTo(float in, Color cIn) {
        return in;
    }

    @Override
    public float transferAuraUnitsFrom(float request) {
        return 0f;
    }

    @Override
    public String toString() {
        return String.format("EntityAuraHandler[auraUnits:%f,maxAuraUnits:%f,color:%s]", au, maxAu, String.valueOf(this.color));
    }


    public static Color generateColor() {
        int colorInt = (new RandomColor()).randomColor();
        return new Color(colorInt);
    }
}
