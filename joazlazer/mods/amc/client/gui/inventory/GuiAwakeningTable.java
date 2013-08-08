package joazlazer.mods.amc.client.gui.inventory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.client.gui.GuiElement;
import joazlazer.mods.amc.client.gui.GuiRectangle;
import joazlazer.mods.amc.client.gui.GuiScrollBarAwakeningTable;
import joazlazer.mods.amc.client.gui.GuiTextFormat;
import joazlazer.mods.amc.client.gui.container.ContainerAwakeningTable;
import joazlazer.mods.amc.lib.GuiIds;
import joazlazer.mods.amc.lib.Resources;
import joazlazer.mods.amc.lib.Textures;
import joazlazer.mods.amc.lib.Textures.GUIS;
import joazlazer.mods.amc.network.PacketHandler;
import joazlazer.mods.amc.orders.OrderBase;
import joazlazer.mods.amc.orders.OrderRegistry;
import joazlazer.mods.amc.spells.SpellBase;
import joazlazer.mods.amc.tileentity.TileEntityAwakeningTable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Resource;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.potion.Potion;

import org.apache.commons.io.Charsets;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAwakeningTable extends GuiAmcContainer {
	// Constants
	public static final int ORDER_ICON_SIZE = 16;
	public static final int ORDER_ICON_X_BUFF = 4;
	public static final int ORDER_X_START = 8;
	public static final int ORDER_Y_START = 19;
	public static final int ORDER_SELECT_ICON_SRC_X = 24;
	public static final int ORDER_SELECT_ICON_SRC_Y  = 228;
	public static final int SELECT_ICON_SIZE = 20;
	public static final int INFO_PANEL_BUFF = 3;
	public static final int INFO_PANEL_X_START = 130 + INFO_PANEL_BUFF;
	public static final int INFO_PANEL_Y_START = 18 + INFO_PANEL_BUFF;
	public static final int INFO_PANEL_WIDTH = 95 - INFO_PANEL_BUFF;
	public static final int INFO_PANEL_HEIGHT = 107 - INFO_PANEL_BUFF - INFO_PANEL_BUFF;
	public static final int LINE_HEIGHT = 10;
	public static final int INFO_PANEL_MAX_LINES = (int) (Math.nextUp((double)INFO_PANEL_HEIGHT) / ((double)LINE_HEIGHT));
	public static final int SPELL_Y_START = 54;
	public static final int SPELL_X_START = 8;
	public static final int SPELL_BUFF = 4;
	
	// This means a new text will have a i in _ chance to appear.
	public static final int TABLET_TEXT_CHANCE = 40;
	public static final int TABLET_X = 14;
	public static final int TABLET_Y = 87;
	public static final int TABLET_W = 105;
	public static final int TABLET_H = 40;
	public static final int TABLET_TEXT_MAX = 6;
	public static final float AWAKEN_SPEED = 3.0f;
	public static final float AWAKEN_OPACITY_FINISH = 180.0f;
	
	// Static variables.
	public static GuiScrollBarAwakeningTable scroll;
	
	// Variables
	public boolean isScrollBarDisabled = false;
	public GuiRectangle[] orders;
	public Object[] orderObjs;
	public int selectedOrderIndex = -1;
	public GuiRectangle selection;
	public int infoPos;
	public int orderInfoLineCount;
	public int linesToScrollDown;
	public SpellBase[] specialtyObjs;
	public GuiRectangle[] specialties;
	public GuiRectangle emeraldTablet;
	public TileEntityAwakeningTable table;
	public boolean isAwakening;
	public boolean closeSoon;
	public float progress;
	public OrderBase awakenOrder;
	
	static {
		// Initialize the scroll bar.
		scroll = new GuiScrollBarAwakeningTable();
	}
	public GuiAwakeningTable(InventoryPlayer playerInv, TileEntityAwakeningTable table) {
		super(new ContainerAwakeningTable(playerInv, table));
		
		// Set the x size and the y size to the correct sizes.
		xSize = 256;
		ySize = 227;
		
		// Set the table to the current one.
		this.table = table;
		
		// Reset the scroll bar's position.
		scroll.height = 0;
		
		// Change the orders objects array to the list of orders.
		orderObjs = OrderRegistry.getOrders().toArray();
		
		// Initialize the orders.
		orders = new GuiRectangle[orderObjs.length];

		// Add rectangles for each of the orders.
		for (int i = 0; i < orderObjs.length; i++)
		{
			// Initialize that specific order.
			orders[i] = new GuiRectangle(ORDER_X_START + i * (ORDER_ICON_SIZE + ORDER_ICON_X_BUFF), ORDER_Y_START, 
					ORDER_ICON_SIZE, ORDER_ICON_SIZE);
		}
		
		// Initialize the selection rectangle.
		selection = new GuiRectangle(0, 0, SELECT_ICON_SIZE, SELECT_ICON_SIZE);
		
		// Initialize the emerald tablet rectangle.
		emeraldTablet = new GuiRectangle(TABLET_X, TABLET_Y, TABLET_W, TABLET_H);
		
		// Reset the awakening variable.
		isAwakening = false;
		
		// Reset the progress of the awakening.
		progress = 0.0f;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		if (!isAwakening) {
			// Reset the current color.
			GL11.glColor4f(1, 1, 1, 1);
			
			// Bind the texture.
			Minecraft.getMinecraft().func_110434_K().func_110577_a(Textures.GUIS.AWAKENING_TABLE_BACKGROUND);
			
			// Draw the background rectangle.
			drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
			
			// Make sure the scroll bar shouldn't be disabled.
			isScrollBarDisabled = selectedOrderIndex == -1;
			
			// Draw the scroll bar.
			scroll.draw(this, isScrollBarDisabled);
			
			for (int i = 0; i < orderObjs.length; i++)
			{
				// Get the current order base.
				OrderBase order = (OrderBase) orderObjs[i];
				
				// Bind the correct texture.
				Minecraft.getMinecraft().func_110434_K().func_110577_a(
						order.getSpriteSheet());
				
				// Draw the rectangle.
				orders[i].draw(this, order.getX(), order.getY());
			}
			
			// If there even is one selected,
			if (selectedOrderIndex != -1)
			{	
				// Bind the correct texture.
				Minecraft.getMinecraft().func_110434_K().func_110577_a(
						Textures.GUIS.AWAKENING_TABLE_BACKGROUND);
				
				// Then draw the selection image.
				selection.draw(this, ORDER_SELECT_ICON_SRC_X, ORDER_SELECT_ICON_SRC_Y);
				
				// Loop through the specialties
				for (int i = 0; i < specialties.length; i++)
				{
					// Bind the correct texture.
					Minecraft.getMinecraft().func_110434_K().func_110577_a(
							specialtyObjs[i].getTextureLocation());
					
					// Draw the current spell.
					specialties[i].draw(this, specialtyObjs[i].getX() * ORDER_ICON_SIZE, 
							specialtyObjs[i].getY() * ORDER_ICON_SIZE);
				}
			}
		}
	}

	/**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
	@Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		if (!isAwakening) {
			// Draw the 'orders' string.
	        this.fontRenderer.drawString(I18n.func_135053_a("Orders"), 10, 6, 4210752);
	        
	        // Draw the 'information' string.
	        this.fontRenderer.drawString(I18n.func_135053_a("Information"), 130, 6, 4210752);
	        
	        // Draw the 'specialties' string.
	        this.fontRenderer.drawString(I18n.func_135053_a("Specialties"), 10, 40, 4210752);
	        
	        // Draw the 'specialties' string.
	        this.fontRenderer.drawString(I18n.func_135053_a("Awaken"), 15, 75, 4210752);
	        
	        // Draw the 'inventory' string.
	        this.fontRenderer.drawString(I18n.func_135053_a("Inventory"), 47, 133, 4210752);
	        
			if (scroll.isDragging)
			{
				// Update the information panel's scrolling position.
				double percent = (double) scroll.height / (double) GuiAwakeningTable.INFO_PANEL_HEIGHT;
				
				// Multiply that by the line count.
				this.linesToScrollDown = (int) (this.orderInfoLineCount * percent);
			}
	        
	        // If the user hasn't selected any order.
	        if (selectedOrderIndex == -1)
	        {
	        	this.fontRenderer.drawSplitString(GuiTextFormat.LIGHTGRAY + "Please select an order to begin.", 
	        			INFO_PANEL_X_START, INFO_PANEL_Y_START, 
	        			INFO_PANEL_WIDTH, 0xEEEEEE);
	        }
	        // If the user has selected an order,
	        else
	        {
	        	ArrayList<String> lines = new ArrayList<String>();
				lines = ((OrderBase)orderObjs[selectedOrderIndex]).getInfoText();
				
	        	// Loop through the different lines that can be rendered.
	        	for (int i = 0; i < INFO_PANEL_MAX_LINES; i++)
	        	{
	        		// Create a variable for the index to use.
	        		int index = i + linesToScrollDown;
	        		
	        		// Make sure there is a line to render at this position.
	        		if (lines.size() <= index) break;
	        		
	        		// Create a variable for the line that should be rendered.
	        		String text = lines.get(index);
	        		
	        		// Render the string.
	        		this.fontRenderer.drawString(text, INFO_PANEL_X_START, INFO_PANEL_Y_START + (i * LINE_HEIGHT), 0xEEEEEE);
	        	}
	        	
	        	// Loop through the specialties
	    		for (int i = 0; i < specialties.length; i++)
	    		{
					// Draw the current spell's tooltip.
					specialties[i].drawString(this, mouseX, mouseY, specialtyObjs[i].getTooltip());
				}
	        }
	        
	        // Loop through the different orders.
	        for (int i = 0; i < orderObjs.length; i++)
	        {
	        	// Draw the hover text.
	        	orders[i].drawString(this, mouseX, mouseY, ((OrderBase)orderObjs[i]).getTooltip());
	        }
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button) {
		if (!isAwakening) {
			// Pass on the info scroll bar and trigger that event.
			scroll.mouseClick(this, mouseX, mouseY, button, isScrollBarDisabled);
			
			// Iterate through the different orders.
			for (int i = 0; i < orderObjs.length; i++)
			{
				// If the mouse is over the order in question,
				if (orders[i].inRect(this, mouseX, mouseY))
				{
					// Change the selected order.
					if (selectedOrderIndex == i)
					{
						selectedOrderIndex = -1;
						
						// Print debug text.
						if (AuricMagickCraft.debugMode)
						{
							System.out.println("The user has just deselected " + ((OrderBase)orderObjs[i]).getUnlocName());
						}
						
						// Make sure the scroll bar shouldn't be disabled.
						isScrollBarDisabled = selectedOrderIndex == -1;
						
						// Nullify the specialties and their corresponding objects;
						specialtyObjs = null;
						specialties = null;
					}
					else 
					{
						selectedOrderIndex = i;
						
						int specialtyCount = ((OrderBase)orderObjs[i]).getSpecialtyIndexes().size();
						
						// Print debug text.
						if (AuricMagickCraft.debugMode)
						{
							System.out.println("The user has just selected " + ((OrderBase)orderObjs[i]).getUnlocName() + ".");
						}
					
						
						ArrayList<String> lines = new ArrayList<String>();
						lines = ((OrderBase)orderObjs[i]).getInfoText();
						
						
						ArrayList<Integer> spellIndexes = new ArrayList<Integer>();
						spellIndexes = ((OrderBase)orderObjs[i]).getSpecialtyIndexes();
						
						// Make sure the scroll bar shouldn't be disabled.
						isScrollBarDisabled = selectedOrderIndex == -1;
						
						orderInfoLineCount = lines.size();
						
						// Initialize the specialty objects array of spells.
						specialtyObjs = new SpellBase[spellIndexes.size()];
						
						// Initialize the specialties array of rectangles.
						specialties = new GuiRectangle[spellIndexes.size()];
						
						// Loop through the specialties.
						for (int j = 0; j < specialtyCount; j++)
						{
							// Set the current spell to the correct one.
							specialtyObjs[j] = (((OrderBase)orderObjs[i]).getSpells().get(spellIndexes.get(j)));
						
							// Set each specialties spell gui rectangle to be proper.
							specialties[j] = new GuiRectangle(SPELL_X_START + j * (ORDER_ICON_SIZE + SPELL_BUFF),
									SPELL_Y_START, ORDER_ICON_SIZE, ORDER_ICON_SIZE);
						}
					}
					
					// Change the selection's position.
					selection.setX(orders[i].getX() - 2);
					selection.setY(orders[i].getY() - 2);
					
					// Print debug text.
					if (AuricMagickCraft.debugMode)
					{
						System.out.println("The selected order index is " + selectedOrderIndex);
					}
					
					// Reset the scroll bar's position.
					scroll.height = 0;
					
					// Reset the text box's scroll position.
					this.linesToScrollDown = 0;
				}
			}
			
			if (emeraldTablet.inRect(this, mouseX, mouseY) && selectedOrderIndex != -1)
			{
				// Change the type of gui.
				isAwakening = true;
			}
		}
	}

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int button, long timeSinceClicked) {
		if (!isAwakening) {
			// Pass on the info scroll bar and trigger that event.
			scroll.mouseClickMove(this, mouseX, mouseY, button, timeSinceClicked, isScrollBarDisabled);
		}
	}

	@Override
	protected void mouseMovedOrUp(int mouseX, int mouseY, int button) {
		if (!isAwakening) {
			// Pass on the info scroll bar and trigger that event.
			scroll.mouseRelease(this, mouseX, mouseY, button, isScrollBarDisabled);
		}
	}

	/* (non-Javadoc)
	 * @see net.minecraft.client.gui.GuiScreen#doesGuiPauseGame()
	 */
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.client.gui.GuiScreen#updateScreen()
	 */
	@Override
	public void updateScreen() {
		if (isAwakening) {
			progress += AWAKEN_SPEED;
			
			if (progress > AWAKEN_OPACITY_FINISH - (AWAKEN_OPACITY_FINISH * 0.1f))
			{
				closeSoon = true;
			}
			
			if (progress >= AWAKEN_OPACITY_FINISH)
			{
				// Teleport the player to the respawn location.
				PacketHandler.INSTANCES.respawnPlayerPacket.send(this.mc.thePlayer.entityId, this.mc.thePlayer);
				
				// Add console text.
				System.out.println(Minecraft.getMinecraft().thePlayer.username + 
						" has just been magically awakened into an " + 
						((OrderBase)orderObjs[selectedOrderIndex]).getPracticer().toLowerCase() + ".");
							
				
				// Send a message to the player.
				this.mc.thePlayer.addChatMessage(GuiTextFormat.ITALIC + "Your awakened senses are too much for you to handle.");
				this.mc.thePlayer.addChatMessage(GuiTextFormat.ITALIC + "You have been returned to your hearth");
				
				// Send a packet of awakening.
				PacketHandler.INSTANCES.awakenPacket.send(this.mc.thePlayer.entityId, ((OrderBase)orderObjs[selectedOrderIndex]).getId(), this.mc.thePlayer);
				
				// Close the gui.
				Minecraft.getMinecraft().displayGuiScreen((GuiScreen) null);
			}
		}
		else {
			super.updateScreen();
		}
	}

	/* (non-Javadoc)
	 * @see net.minecraft.client.gui.GuiScreen#drawScreen(int, int, float)
	 */
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		if (isAwakening) this.drawBackground(0);
		else super.drawScreen(par1, par2, par3);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.client.gui.GuiScreen#drawBackground(int)
	 */
	@Override
	public void drawBackground(int par1) {
		if (isAwakening) {
			// Get the correct scaled width and height.
			ScaledResolution scaledresolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
	        int width = scaledresolution.getScaledWidth();
	        int height = scaledresolution.getScaledHeight();
			
	        // Disable the unwanted OpenGL effects.
			GL11.glDisable(GL11.GL_DEPTH_TEST);
	        GL11.glDisable(GL11.GL_ALPHA_TEST);
	        
	        // Get the current progress.
	        float currProgress = this.progress;
	        
	        // Calculate the percent of opacity to render;
	        float percent = (float)currProgress / AWAKEN_OPACITY_FINISH;
	        
	        // Create a color using the base color and bit shift the opacity into it.
	        int j1 = (int)(220.0F * percent) << 24 | 1052704;
	        
	        // Draw the rectangle.
	        drawRect(0, 0, width, height, j1);
	        
	        // Enable the unwanted OpenGL effects we disabled earlier.
	        GL11.glEnable(GL11.GL_ALPHA_TEST);
	        GL11.glEnable(GL11.GL_DEPTH_TEST);
		}
		else super.drawBackground(0);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.client.gui.GuiScreen#onGuiClosed()
	 */
	@Override
	public void onGuiClosed() {
		if (isAwakening) {
			if (!closeSoon)
			{
				// Send the packet to kill the player.
				PacketHandler.INSTANCES.awakenStopPacket.send(this.mc.thePlayer.entityId, 0, this.mc.thePlayer);
				
				// Update it immediately on the client side.
				this.mc.thePlayer.setEntityHealth(0.0f);
			}
		}
	}

	@Override
	public void drawText(String txt, int x, int y, int color, boolean shadow) {
		fontRenderer.drawString(txt, x, y, color, shadow);
	}

	@Override
	public FontRenderer getFontRenderer() {
		return fontRenderer;
	}

	@Override
	public void removeGuiElement(GuiElement elementClass, int index) {
		
	}

	@Override
	public void drawTextWithAlpha(String txt, int x, int y, int color,
			int alpha, FontRenderer font, boolean shadow) {
	}
}
