package joazlazer.mods.amc.orders;

import java.util.ArrayList;

import joazlazer.mods.amc.spells.SpellBase;
import net.minecraft.util.ResourceLocation;

public class OrderBase {
	// The texture path for the icon.
	private ResourceLocation spriteSheet;
	private int xPos, yPos;
	private ArrayList<String> infoText;
	private ArrayList<String> tooltip;
	
	// The unlocalized name for the order.
	private String unlocName;
	private ArrayList<SpellBase> spells;
	private ArrayList<Integer> specialtyIndexes;
	private String practicer;
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the practicer
	 */
	public String getPracticer() {
		return practicer;
	}

	/**
	 * @param practicer the practicer to set
	 */
	public void setPracticer(String practicer) {
		this.practicer = practicer;
	}

	public OrderBase()
	{
		spells = new ArrayList<SpellBase>();
		infoText = new ArrayList<String>();
		tooltip = new ArrayList<String>();
		specialtyIndexes = new ArrayList<Integer>();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getUnlocName();
	}

	/**
	 * @return the spells
	 */
	public ArrayList<SpellBase> getSpells() {
		return spells;
	}
	
	/**
	 * Used in the spell API to add spells.
	 * @param spell The spell to add.
	 */
	public void addSpell(SpellBase spell) {
		spells.add(spell);
	}
	
	/**
	 * @param spells the spells to set
	 */
	public void setSpells(ArrayList<SpellBase> spells) {
		this.spells = spells;
	}
	/**
	 * @return the infoText
	 */
	public ArrayList<String> getInfoText() {
		return infoText;
	}
	/**
	 * @param infoText the infoText to set
	 */
	public void setInfoText(ArrayList<String> infoText) {
		this.infoText = infoText;
	}
	/**
	 * @return the tooltip
	 */
	public ArrayList<String> getTooltip() {
		return tooltip;
	}
	/**
	 * @param tooltip the tooltip to set
	 */
	public void setTooltip(ArrayList<String> tooltip) {
		this.tooltip = tooltip;
	}
	// Getters and setters for the global variables. These
	// act as method wrappers for them.
	public String getUnlocName()
	{
		// Return the unlocalized name.
		return unlocName;
	}
	public void setUnlocName(String newName)
	{
		// Change the unlocalized name to the new one.
		unlocName = newName;
	}
	public ResourceLocation getSpriteSheet()
	{
		// Return the sprite sheet location.
		return spriteSheet;
	}
	public void setSpriteSheet(ResourceLocation newLoc)
	{
		// Change the sprite sheet location to the new one.
		spriteSheet = newLoc;
	}
	public void setSpriteLoc(int x, int y)
	{
		// Change the x and y positions for the texture to the new ones.
		this.xPos = x;
		this.yPos = y;
	}
	public int getX()
	{
		// Return the u sprite sheet location.
		return this.xPos;
	}
	public int getY()
	{
		// Return the v sprite sheet location.
		return this.yPos;
	}
	public void addSpecialties()
	{
		// Iterate through the different spells.
		for (int i = 0; i < spells.size(); i++)
		{
			// If the current spell is a specialty,
			if (spells.get(i).isSpecialty())
			{
				// Then add its index to the specialties array.
				specialtyIndexes.add(i);
			}
		}
	}
	/**
	 * @return the specialtyIndexes
	 */
	public ArrayList<Integer> getSpecialtyIndexes() {
		return specialtyIndexes;
	}
	/**
	 * @param specialtyIndexes the specialtyIndexes to set
	 */
	public void setSpecialtyIndexes(ArrayList<Integer> specialtyIndexes) {
		this.specialtyIndexes = specialtyIndexes;
	}
}
