package joazlazer.mods.amc.client.gui.inventory;

import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public abstract class GuiAmcContainer extends GuiContainer implements IGuiAmc{

	public static final float NEW_TEXT_CHANCE = 15.0f;
	
	public GuiAmcContainer(Container par1Container) {
		super(par1Container);
	}
	
	@Override
	public int getGuiTop()
	{
		return field_147009_r;
	}
	
	@Override
	public int getGuiLeft()
	{
		return field_147003_i;
	}
	
	@Override
	public void drawHoverString(@SuppressWarnings("rawtypes") List lst, int x, int y) {
		drawHoveringText(lst, x, y, field_146289_q);
	}	
	
	@Override
	public void drawTexturedModalRectAmc(int i, int j, int srcX, int srcY, int w, int h)
	{
		this.drawTexturedModalRect(i, j, srcX, srcY, w, h);
	}
}