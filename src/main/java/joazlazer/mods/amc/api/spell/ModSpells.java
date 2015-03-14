package joazlazer.mods.amc.api.spell;

public class ModSpells {
	public static class SPELLS {
		public static SpellBase blink;
	}
	
	public static void initSpells() {
        SPELLS.blink = new SpellBlink();
	}
	
	public static void registerSpells() {
        initSpells();
        SpellRegistry.registerGenericSpell(SPELLS.blink);
	}
}
