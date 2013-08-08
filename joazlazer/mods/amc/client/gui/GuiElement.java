package joazlazer.mods.amc.client.gui;

import joazlazer.mods.amc.client.gui.inventory.IGuiAmc;

public abstract class GuiElement {
	
		public abstract void drawForeground(IGuiAmc gui, int mouseX, int mouseY);
		
		public abstract void drawBackground(IGuiAmc gui, int mouseX, int mouseY);
		
		public void mouseClick(IGuiAmc gui, int mouseX, int mouseY, int button) {
			
		}
		public void mouseMoveClick(IGuiAmc gui, int mouseX, int mouseY, int button, double timeSinceClick) {
			
		}
}
