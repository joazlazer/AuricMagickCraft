package joazlazer.mods.amc.api.order;

import java.util.ArrayList;
import net.minecraft.util.ResourceLocation;

public class OrderBase {
	// The texture path for the icon.
	private ResourceLocation texture;
	private int u, v;
	private ArrayList<String> infoText;
	private ArrayList<String> tooltip;
	
	// The unlocalized name for the order.
	private String unlocName;
	private String practicer;
	private int id;
	
	public OrderBase()
	{
		infoText = new ArrayList<String>();
		tooltip = new ArrayList<String>();
	}

	public int getId() {
		return id;
	}

	public OrderBase setId(int id) {
		this.id = id;
        return this;
	}

	public String getPracticer() {
		return practicer;
	}

	public OrderBase setPracticer(String practicer) {
		this.practicer = practicer;
        return this;
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getUnlocName();
	}

	public ArrayList<String> getInfoText() {
		return infoText;
	}

	public OrderBase setInfoText(ArrayList<String> infoText) {
		this.infoText = infoText;
        return this;
	}

	public ArrayList<String> getTooltip() {
		return tooltip;
	}

	public OrderBase setTooltip(ArrayList<String> tooltip) {
		this.tooltip = tooltip;
        return this;
	}

	public String getUnlocName() {
		// Return the unlocalized name.
		return unlocName;
	}

	public OrderBase setUnlocName(String newName) {
		// Change the unlocalized name to the new one.
		unlocName = newName;
        return this;
	}

	public ResourceLocation getTexture() {
		// Return the texture location.
		return texture;
	}

	public OrderBase setTexture(ResourceLocation newLoc) {
		// Change the texture location to the new one.
		texture = newLoc;
        return this;
	}

	public OrderBase setUV(int u, int v) {
		// Change the u and v positions for the texture to the new ones.
		this.u = u;
		this.v = v;
        return this;
	}

	public int getU() {
		// Return the u sprite sheet location.
		return this.u;
	}

	public int getV() {
		// Return the v sprite sheet location.
		return this.v;
	}
}
