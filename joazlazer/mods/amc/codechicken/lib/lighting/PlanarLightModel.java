package joazlazer.mods.amc.codechicken.lib.lighting;

import net.minecraft.client.renderer.Tessellator;
import joazlazer.mods.amc.codechicken.lib.colour.Colour;
import joazlazer.mods.amc.codechicken.lib.colour.ColourRGBA;
import joazlazer.mods.amc.codechicken.lib.render.CCModel;
import joazlazer.mods.amc.codechicken.lib.render.CCRenderState;
import joazlazer.mods.amc.codechicken.lib.render.IVertexModifier;
import joazlazer.mods.amc.codechicken.lib.render.UV;
import joazlazer.mods.amc.codechicken.lib.vec.Vector3;

/**
 * Faster precomputed version of LightModel that only works for axis planar sides
 */
public class PlanarLightModel implements IVertexModifier
{
    public ColourRGBA[] colours;
    
    public PlanarLightModel(int[] colours)
    {
        this.colours = new ColourRGBA[6];
        for(int i = 0; i < 6; i++)
            this.colours[i] = new ColourRGBA(colours[i]);
    }

    @Override
    public void applyModifiers(CCModel m, Tessellator tess, Vector3 vec, UV uv, Vector3 normal, int i)
    {
        ColourRGBA light = colours[CCModel.findSide(normal)];
        int colour = (m == null || m.colours == null) ? -1 : m.colours[i];
        Colour res = new ColourRGBA(colour).multiply(light);
        CCRenderState.vertexColour(res.r&0xFF, res.g&0xFF, res.b&0xFF, res.a&0xFF) ;
    }

    @Override
    public boolean needsNormals()
    {
        return true;
    }
}
