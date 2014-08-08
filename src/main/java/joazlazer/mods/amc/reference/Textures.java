package joazlazer.mods.amc.reference;

import net.minecraft.util.ResourceLocation;

public class Textures {
    public static final String RESOURCE_PREFIX = Reference.MOD_ID.toLowerCase() + ":";

    public static class Gui {
        public static final ResourceLocation AWAKENING_TABLE = new ResourceLocation(Reference.MOD_ID, "textures/gui/awakeningTable.png");
    }

    public static class Models {
        public static final ResourceLocation AWAKENING_TABLE = new ResourceLocation(Reference.MOD_ID, "textures/models/awakeningTable.png");
    }

    public static class Orders {
        public static final ResourceLocation ARCANA = new ResourceLocation(Reference.MOD_ID, "textures/orders/arcana.png");
        public static final ResourceLocation ALCHEMIA = new ResourceLocation(Reference.MOD_ID, "textures/orders/alchemia.png");
    }
}
