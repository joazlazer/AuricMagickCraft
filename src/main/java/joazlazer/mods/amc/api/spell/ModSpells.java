package joazlazer.mods.amc.api.spell;

public class ModSpells {
	public static class SPELLS {
		public static SpellBase blink;
		public static SpellBase arcaneBolt;
		public static SpellBase fireball;
		public static SpellBase iceBolt;
		public static SpellBase energyBeam;
		public static SpellBase fireBlast;
		public static SpellBase fireWave;
		public static SpellBase waterWave;
		public static SpellBase waterWhip;
	}
	
	public static void initSpells() {
        SPELLS.blink = new SpellBlink();
		SPELLS.arcaneBolt = new SpellArcaneBolt();
		SPELLS.fireball = new SpellFireball();
		SPELLS.iceBolt = new SpellIceBolt();
		SPELLS.energyBeam = new SpellEnergyBeam();
		SPELLS.fireBlast = new SpellFireBlast();
		SPELLS.fireWave = new SpellFireWave();
		SPELLS.waterWave = new SpellWaterWave();
		SPELLS.waterWhip = new SpellWaterWhip();
	}
	
	public static void registerSpells() {
        initSpells();
        SpellRegistry.registerGenericSpell(SPELLS.blink);
		SpellRegistry.registerGenericSpell(SPELLS.arcaneBolt);
		SpellRegistry.registerGenericSpell(SPELLS.fireball);
		SpellRegistry.registerGenericSpell(SPELLS.iceBolt);
		SpellRegistry.registerGenericSpell(SPELLS.energyBeam);
		SpellRegistry.registerGenericSpell(SPELLS.fireBlast);
		SpellRegistry.registerGenericSpell(SPELLS.fireWave);
		SpellRegistry.registerGenericSpell(SPELLS.waterWave);
		SpellRegistry.registerGenericSpell(SPELLS.waterWhip);
	}
}
