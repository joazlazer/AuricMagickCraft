package joazlazer.mods.amc.client.gui;

import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.api.order.OrderBase;
import joazlazer.mods.amc.api.order.OrderRegistry;
import joazlazer.mods.amc.client.gui.component.GuiButton;
import joazlazer.mods.amc.client.gui.component.GuiRectangle;
import joazlazer.mods.amc.container.ContainerAwakeningTable;
import joazlazer.mods.amc.reference.Textures;
import joazlazer.mods.amc.tileentity.TileEntityAwakeningTable;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class GuiAwakeningTable extends GuiContainerAMC {

    public ArrayList<GuiButton> buttons = new ArrayList<GuiButton>();

    @Override
    public void onButtonClicked(GuiButton button, int mouseX, int mouseY, int mouseButton) {
        if(button == orderLeft) {

        }
        else if(button == orderRight) {

        }
    }

    public static class CONSTANTS {
        public static class GLOBAL {
            public static final int X_SIZE = 124;
            public static final int Y_SIZE = 134;
        }
        public static class TEXT {
            public static final int MAIN_COLOR = 0x10526880;
            public static final String UNLOC_ORDER = "container.amc:orders";
            public static final String UNLOC_SPELLS = "container.amc:spells";
            public static final int ORDER_X = 0;
            public static final int ORDER_Y = 4;
            public static final int SPELLS_X = 82;
            public static final int SPELLS_Y = 4;
        }
        public static class ORDERS {
            public static final int SELECT_PANEL_ORDER_MAX_W = 3;
            public static final int SELECT_PANEL_ORDER_MAX_H = 5;
            public static final int SELECT_PANEL_START_X = 0;
            public static final int SELECT_PANEL_START_Y = 0;
            public static final int SELECT_PANEL_ITEM_W = 16;
            public static final int SELECT_PANEL_ITEM_H = 16;

            public static final int SCROLL_BUTTON_W = 0;
            public static final int SCROLL_BUTTON_H = 0;
            public static final int LEFT_X = 0;
            public static final int LEFT_Y = 0;
            public static final int RIGHT_X = 0;
            public static final int RIGHT_Y = 0;
            public static final int LEFT_U = 0;
            public static final int LEFT_V = 0;
            public static final int LEFT_PRESSED_U = 0;
            public static final int LEFT_PRESSED_V = 0;
            public static final int LEFT_HOVER_U = 0;
            public static final int LEFT_HOVER_V = 0;
            public static final int LEFT_DISABLED_U = 0;
            public static final int LEFT_DISABLED_V = 0;
            public static final int RIGHT_U = 0;
            public static final int RIGHT_V = 0;
            public static final int RIGHT_PRESSED_U = 0;
            public static final int RIGHT_PRESSED_V = 0;
            public static final int RIGHT_HOVER_U = 0;
            public static final int RIGHT_HOVER_V = 0;
            public static final int RIGHT_DISABLED_U = 0;
            public static final int RIGHT_DISABLED_V = 0;
        }
    }

    public GuiButton orderLeft;
    public GuiButton orderRight;
    public OrderBase[] orderObjs;
    public GuiRectangle[] orders;

    public GuiAwakeningTable(InventoryPlayer inventory, TileEntityAwakeningTable te) {
        super(new ContainerAwakeningTable(inventory, te));
        xSize = CONSTANTS.GLOBAL.X_SIZE;
        ySize = CONSTANTS.GLOBAL.Y_SIZE;
        orderLeft = new GuiButton(CONSTANTS.ORDERS.LEFT_X, CONSTANTS.ORDERS.LEFT_Y, CONSTANTS.ORDERS.LEFT_U, CONSTANTS.ORDERS.LEFT_V, CONSTANTS.ORDERS.SCROLL_BUTTON_W, CONSTANTS.ORDERS.SCROLL_BUTTON_H, CONSTANTS.ORDERS.LEFT_HOVER_U, CONSTANTS.ORDERS.LEFT_HOVER_V, CONSTANTS.ORDERS.LEFT_PRESSED_U, CONSTANTS.ORDERS.LEFT_PRESSED_V, CONSTANTS.ORDERS.LEFT_DISABLED_U, CONSTANTS.ORDERS.LEFT_DISABLED_V);
        orderRight = new GuiButton(CONSTANTS.ORDERS.RIGHT_X, CONSTANTS.ORDERS.RIGHT_Y, CONSTANTS.ORDERS.RIGHT_U, CONSTANTS.ORDERS.RIGHT_V, CONSTANTS.ORDERS.SCROLL_BUTTON_W, CONSTANTS.ORDERS.SCROLL_BUTTON_H, CONSTANTS.ORDERS.RIGHT_HOVER_U, CONSTANTS.ORDERS.RIGHT_HOVER_V, CONSTANTS.ORDERS.RIGHT_PRESSED_U, CONSTANTS.ORDERS.RIGHT_PRESSED_V, CONSTANTS.ORDERS.RIGHT_DISABLED_U, CONSTANTS.ORDERS.RIGHT_DISABLED_V);
        buttons.add(orderRight);
        buttons.add(orderLeft);

        // TODO: TEST
        AuricMagickCraft.PlayerTracker.awaken(inventory.player, new OrderBase() { @Override public String getUnlocName() { return "mahnameisbahb"; } });

        /* TODO orderObjs = new OrderBase[OrderRegistry.getOrders().size()];
        orders = new GuiRectangle[Math.min(orders.length, CONSTANTS.ORDERS.SELECT_PANEL_ORDER_MAX_W * CONSTANTS.ORDERS.SELECT_PANEL_ORDER_MAX_H)];
        for(int i = 0; i < orders.length && i < CONSTANTS.ORDERS.SELECT_PANEL_ORDER_MAX_W * CONSTANTS.ORDERS.SELECT_PANEL_ORDER_MAX_H; i++) {
            int xItemPos = i % CONSTANTS.ORDERS.SELECT_PANEL_ORDER_MAX_W;
            int yItemPos = i / CONSTANTS.ORDERS.SELECT_PANEL_ORDER_MAX_W;
            int xPos = CONSTANTS.ORDERS.SELECT_PANEL_START_X + (xItemPos * CONSTANTS.ORDERS.SELECT_PANEL_ITEM_W);
            int yPos = CONSTANTS.ORDERS.SELECT_PANEL_START_Y + (yItemPos * CONSTANTS.ORDERS.SELECT_PANEL_ITEM_H);
            orders[i] = new GuiRectangle(xPos, yPos, CONSTANTS.ORDERS.SCROLL_BUTTON_W, CONSTANTS.ORDERS.SELECT_PANEL_ITEM_H);
        } */
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f0, int mouseX, int mouseY) {
        // Reset the OpenGL colors to prepare for rendering.
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        // Bind the gui texture.
        this.mc.renderEngine.bindTexture(Textures.Gui.AWAKENING_TABLE);

        // Calculate the x and y of the top corner of the gui.
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        // Enable transparency.
        GL11.glEnable(GL11.GL_BLEND);

        // Draw the background.
        this.drawTexturedModalRect(x - 24, y - 6, 0, 0, xSize, ySize);

        // Update the buttons.
        for (GuiButton button : buttons) button.update(this, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i0, int i1) {
        this.fontRendererObj.drawString(StatCollector.translateToLocal(CONSTANTS.TEXT.UNLOC_ORDER), CONSTANTS.TEXT.ORDER_X, CONSTANTS.TEXT.ORDER_Y, CONSTANTS.TEXT.MAIN_COLOR, false);
        this.fontRendererObj.drawString(StatCollector.translateToLocal(CONSTANTS.TEXT.UNLOC_SPELLS), CONSTANTS.TEXT.SPELLS_X, CONSTANTS.TEXT.SPELLS_Y, CONSTANTS.TEXT.MAIN_COLOR, false);
    }

    @Override public void mouseClicked(int mouseX, int mouseY, int button) {
        // Update all of the buttons.
        for (GuiButton guiButton : buttons) guiButton.onClicked((IGuiAccess) this, mouseX, mouseY, button);
    }
}
