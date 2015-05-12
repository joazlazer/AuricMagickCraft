package joazlazer.mods.amc.reference;

import net.minecraft.util.ResourceLocation;

public class Textures {
    public static final String RESOURCE_PREFIX = Reference.MOD_ID.toLowerCase() + ":";

    public static class Gui {
        public static final ResourceLocation AWAKENING_TABLE = new ResourceLocation(Reference.MOD_ID, "textures/gui/awakeningTable.png");
        public static final ResourceLocation SPELL_SELECT =  new ResourceLocation(Reference.MOD_ID, "textures/gui/spellSelect.png");
        public static final ResourceLocation ARROW = new ResourceLocation(Reference.MOD_ID, "textures/gui/arrow.png");
    }

    public static class Models {
        public static final ResourceLocation AWAKENING_TABLE = new ResourceLocation(Reference.MOD_ID, "textures/models/awakeningTable.png");
    }

    public static class Orders {
        public static final ResourceLocation ARCANA = new ResourceLocation(Reference.MOD_ID, "textures/orders/arcana.png");
        public static final ResourceLocation ARCANA_LARGE = new ResourceLocation(Reference.MOD_ID, "textures/orders/arcana_large.png");
        public static final ResourceLocation ALCHEMIA = new ResourceLocation(Reference.MOD_ID, "textures/orders/alchemia.png");
        public static final ResourceLocation ALCHEMIA_LARGE = new ResourceLocation(Reference.MOD_ID, "textures/orders/alchemia_large.png");
    }

    public static class Spells {
        public static final ResourceLocation BLINK = new ResourceLocation(Reference.MOD_ID, "textures/spells/blink.png");
        public static final ResourceLocation BLINK_LARGE = new ResourceLocation(Reference.MOD_ID, "textures/spells/blink_large.png");
    }
}
