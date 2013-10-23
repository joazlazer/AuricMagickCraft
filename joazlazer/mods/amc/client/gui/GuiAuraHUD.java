package joazlazer.mods.amc.client.gui;

import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.lib.Textures;
import joazlazer.mods.amc.playertracking.AmcPlayerStats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAuraHUD extends Gui {
	// Constants
	public static final int ROSARY_ICONS_X_START = 0;
	public static final int ROSARY_ICONS_Y_START = 0;
	public static final int ROSARY_ICONS_BUFF = 1;
	public static final int ROSARY_ICONS_NUMB_CLMNS = 6;
	public static final int ROSARY_ICONS_NUMB_ROWS = 2;
	public static final int ROSARY_ICONS_COUNT = 12;
	public static final int ROSARY_ICONS_SIZE = 33;
	
	// Variables.
	Minecraft mc;
	int auraColor;
	int auraCount;
	int auraLevel;
	float red;
	float green;
	float blue;
	
	public GuiAuraHUD(Minecraft minecraft)
	{
		this.mc = minecraft;
	}
	
	@ForgeSubscribe(priority = EventPriority.NORMAL)
	public void onRenderExperienceBar(RenderGameOverlayEvent e) {
		// Make sure we are rendering AFTER the CROSSHAIR and are AWAKE and we SHOULD render the rosary.
		if (e.isCancelable() || e.type != ElementType.CROSSHAIRS || 
				!AuricMagickCraft.playerTracker.getPlayerStats(this.mc.thePlayer.username).isAwake ||
				!AuricMagickCraft.playerTracker.getPlayerStats(this.mc.thePlayer.username).showAuraRosary)
		{
			// If not, then return.
			return;
		}
		
		this.auraColor = AuricMagickCraft.playerTracker.getPlayerStats(this.mc.thePlayer.username).auraColor;
		this.auraCount = AuricMagickCraft.playerTracker.getPlayerStats(this.mc.thePlayer.username).aura;
		this.auraLevel = AuricMagickCraft.playerTracker.getPlayerStats(this.mc.thePlayer.username).auraLevel;
		
		// Variables
		int screenX = 0;
		int screenY = 0;
		int screenW = mc.displayWidth;
		int screenH = mc.displayHeight;
		int screenCentX = screenW / 2;
		int screenCentY = screenH / 2;
		int rosaryCentX = ROSARY_ICONS_SIZE / 2;
		int rosaryCentY = rosaryCentX;
		int rosaryX;
		int rosaryY;
		
		// Calculate the new positions for the petals if the screen is in normal or large.
		ScaledResolution scaledresolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
		screenCentX /= scaledresolution.getScaleFactor();
		screenCentY /= scaledresolution.getScaleFactor();
		
		// Calculate the x and y positions for the rosary.
		rosaryX = screenCentX - rosaryCentX;
		rosaryY = screenCentY - rosaryCentY;
		
		// Calculate the percent of aura by using a fraction of current aura and max aura.
		float percent = ((float) auraCount) / ( (float) AmcPlayerStats.getMaxAura(auraLevel, AuricMagickCraft.playerTracker.getPlayerStats(this.mc.thePlayer.username).auraColor));
		
		// Calculate the number of rosaries to draw.
		int rosaryCount = (int) Math.nextUp(percent * ((float) ROSARY_ICONS_COUNT));
		
		// Make sure the number of rosary petals to draw isn't greater than the maximum.
		if (rosaryCount > ROSARY_ICONS_COUNT) rosaryCount = ROSARY_ICONS_COUNT;
		
		// Figure out the correct colors.
		this.red = (float)(auraColor >> 16 & 255) / 255.0F;
        this.blue = (float)(auraColor >> 8 & 255) / 255.0F;
        this.green = (float)(auraColor & 255) / 255.0F;
        
        // Set the color to be blended with to the current one.
        GL11.glColor4f(this.red, this.blue, this.green, 1.0f);
        
        // Bind the correct texture.
        Minecraft.getMinecraft().func_110434_K().func_110577_a(Textures.GUIS.AURA_HUD_ICONS);
		
		// Loop through the different rosaries.
		for (int i = 0; i < rosaryCount; i++)
		{
			// Create variables to determine the u and v for the current rosary petal.
			int rosaryTextX;
			int rosaryTextY;
			int srcX;
			int srcY;
			
			// Get the position of the current rosary.
			rosaryTextX = i % ROSARY_ICONS_NUMB_CLMNS;
			rosaryTextY = i < ROSARY_ICONS_COUNT / 2 ? 0 : 1;
			
			// Get the position in the texture sheet,
			srcX = rosaryTextX * (ROSARY_ICONS_SIZE + ROSARY_ICONS_BUFF);
			srcY = rosaryTextY * (ROSARY_ICONS_SIZE + ROSARY_ICONS_BUFF);
			
			// Draw the rosary in the correct position.
			this.drawTexturedModalRect(rosaryX, rosaryY, srcX, srcY, ROSARY_ICONS_SIZE, ROSARY_ICONS_SIZE);
		}
		
		// Reset the color.
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	}
}
