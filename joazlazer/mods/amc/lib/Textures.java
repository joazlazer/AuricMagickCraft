package joazlazer.mods.amc.lib;

import net.minecraft.util.ResourceLocation;

public final class Textures
{
	// Texture paths used throughout the mod.
	public static final String FORGE_BELLOWS = "/mods/amc/model textures/forgeBellowsModel.png";
	public static final String FORGE_VENT = "mods/amc/model textures/forgeVentModel.png";
	
	public static class BLOCKS
	{
	    // Textures for blocks used throughout the mod.
	    public static final String AWAKENING_TABLE_TOP = "amc:awakeningTable_TOP";
	}
	
	public static class GUIS
	{
		// ResourceLocations used for guis throughout this mod.
		public static final ResourceLocation AWAKENING_TABLE_BACKGROUND = 
				new ResourceLocation("amc", "textures/gui/GuiAwakeningTable.png");
		public static final ResourceLocation AURA_HUD_ICONS =
				new ResourceLocation("amc", "textures/gui/aura.png");
	}
	
	public static class ORDERS
	{
		// ResourceLocations used for orders throughout this mod.
		public static final ResourceLocation ORDERS_SPRITESHEET = 
				new ResourceLocation("amc", "textures/order/SymbolSpriteSheet.png");
	}
	
	public static class SPELLS
	{
		// ResourceLocations used for spells throughout this mod.
		public static final ResourceLocation SPELLS_SPRITESHEET = 
				new ResourceLocation("amc", "textures/spell/SpellSpriteSheet.png");
	}
}
