package joazlazer.mods.amc.spells;

import joazlazer.mods.amc.lib.Reference;

public class ModSpells {
	public static class SPELLS {
		public static SpellBase basicBolt;
		public static SpellBase transmute;
	}
	
	public static void initSpells()
	{
		SPELLS.basicBolt = new SpellBolt();
		SPELLS.transmute = new SpellTransmute();
	}
	
	public static void registerSpells() {
		SpellRegistry.registerGenericSpell(SPELLS.basicBolt);
		SpellRegistry.registerSpell(SPELLS.transmute, Reference.MOD_ID, "alchemia");
	}
}
