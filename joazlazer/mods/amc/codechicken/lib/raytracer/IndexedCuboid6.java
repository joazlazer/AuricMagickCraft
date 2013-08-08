package joazlazer.mods.amc.codechicken.lib.raytracer;

import joazlazer.mods.amc.codechicken.lib.vec.Cuboid6;

public class IndexedCuboid6 extends Cuboid6
{
    public Object data;
    
    public IndexedCuboid6(Object data, Cuboid6 cuboid)
    {
        super(cuboid);
        this.data = data;
    }
}