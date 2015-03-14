package joazlazer.mods.amc.client.gui;

import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.api.order.ModOrders;
import joazlazer.mods.amc.api.order.OrderBase;
import joazlazer.mods.amc.api.order.OrderRegistry;
import joazlazer.mods.amc.client.gui.component.GuiButton;
import joazlazer.mods.amc.client.gui.component.GuiRectangle;
import joazlazer.mods.amc.client.render.RenderHelper;
import joazlazer.mods.amc.container.ContainerAwakeningTable;
import joazlazer.mods.amc.reference.Textures;
import joazlazer.mods.amc.tileentity.TileEntityAwakeningTable;
import joazlazer.mods.amc.util.Color;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class GuiAwakeningTable extends GuiContainerAMC {

    public ArrayList<GuiButton> buttons = new ArrayList<GuiButton>();
    public int page = 0;
    public int maxPage = 1;
    public OrderBase selectedOrder;

    @Override
    public void onButtonClicked(GuiButton button, int mouseX, int mouseY, int mouseButton) {
        if(button == orderLeft) {

        }
        else if(button == orderRight) {

        }
    }

    public static class CONSTANTS {
        public static class GLOBAL {
            public static final int X_SIZE = 150;
            public static final int Y_SIZE = 175;
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
            public static final int SELECT_PANEL_START_X = 16;
            public static final int SELECT_PANEL_START_Y = 50;
            public static final int SELECT_PANEL_ITEM_W = 16;
            public static final int SELECT_PANEL_ITEM_H = 16;

            public static final int SELECTION_CIRCLE_ICON_START_X = 99;
            public static final int SELECTION_CIRCLE_ICON_START_Y = 24;

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

        orderObjs = new OrderBase[OrderRegistry.getOrders().size()];
        for(int i = 0; i < OrderRegistry.getOrders().size(); i++) {
            orderObjs[i] = (OrderBase) OrderRegistry.getOrders().get(i);
        }
        orders = new GuiRectangle[Math.min(orderObjs.length, CONSTANTS.ORDERS.SELECT_PANEL_ORDER_MAX_W * CONSTANTS.ORDERS.SELECT_PANEL_ORDER_MAX_H)];
        for(int i = 0; i < orders.length && i < CONSTANTS.ORDERS.SELECT_PANEL_ORDER_MAX_W * CONSTANTS.ORDERS.SELECT_PANEL_ORDER_MAX_H; i++) {
            int xItemPos = i / CONSTANTS.ORDERS.SELECT_PANEL_ORDER_MAX_W;
            int yItemPos = i % CONSTANTS.ORDERS.SELECT_PANEL_ORDER_MAX_W;
            int xPos = CONSTANTS.ORDERS.SELECT_PANEL_START_X + (xItemPos * CONSTANTS.ORDERS.SELECT_PANEL_ITEM_W);
            int yPos = CONSTANTS.ORDERS.SELECT_PANEL_START_Y + (yItemPos * CONSTANTS.ORDERS.SELECT_PANEL_ITEM_H);
            orders[i] = new GuiRectangle(xPos, yPos, CONSTANTS.ORDERS.SELECT_PANEL_ITEM_W, CONSTANTS.ORDERS.SELECT_PANEL_ITEM_H);
            System.out.println("Initializing 1 order rectangle.");
        }
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
        this.guiLeft = x - 96;
        this.guiTop = y - 32;

        // Enable transparency.
        GL11.glEnable(GL11.GL_BLEND);

        // Draw the background.
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        // Update the buttons.
        for (GuiButton button : buttons) button.update(this, mouseX, mouseY);

        for(int i = 0; i < orders.length; i++) {
            GuiRectangle rect = orders[i];
            if(isOrderPresentAtScrolledPosition(i)) {
                RenderHelper.drawOrderIcon(this.getGuiLeft() + rect.getX(), this.getGuiTop() + rect.getY(), orderObjs[CONSTANTS.ORDERS.SELECT_PANEL_ITEM_H * page + i]);
            }
        }

        if(selectedOrder != null) {
            RenderHelper.drawLargeOrderIcon(this.getGuiLeft() + CONSTANTS.ORDERS.SELECTION_CIRCLE_ICON_START_X, this.getGuiTop() + CONSTANTS.ORDERS.SELECTION_CIRCLE_ICON_START_Y, selectedOrder);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i0, int i1) {
        if(selectedOrder != null) {
            GL11.glScalef(2.0f, 2.0f, 1.0f);
            this.drawString(fontRendererObj, StatCollector.translateToLocal("order." + selectedOrder.getUnlocName() + ".name"), 160 / 2, 24 / 2, new Color(248, 228, 48).toRGB());
            System.out.println("Trying to render order name " + StatCollector.translateToLocal("order." + selectedOrder.getUnlocName() + ".name"));
            GL11.glScalef(0.5f, 0.5f, 1.0f);
        }

        for(int i = 0; i < orders.length; i++) {
            GuiRectangle rect = orders[i];
            if(isOrderPresentAtScrolledPosition(i) && orders[i].inRect(this, i0, i1)) {
                OrderBase order = orderObjs[(CONSTANTS.ORDERS.SELECT_PANEL_ITEM_H * page) + i];
                this.drawHoveringText(order.getTooltip(), i0 - getGuiLeft(), i1 - getGuiTop(), this.fontRendererObj);
            }
        }

        GL11.glColor4f(128f, 128f, 128f, 255f);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        // Update all of the buttons.
        for (GuiButton guiButton : buttons) guiButton.onClicked(this, mouseX, mouseY, button);

        for(int i = 0; i < orders.length; i++) {
            GuiRectangle rect = orders[i];
            if(rect.inRect(this, mouseX, mouseY) && isOrderPresentAtScrolledPosition(i)) {
                OrderBase order = orderObjs[CONSTANTS.ORDERS.SELECT_PANEL_ITEM_H * page + i];
                if(selectedOrder == order) selectedOrder = null;
                else selectedOrder = order;
            }
        }
    }

    public boolean isOrderPresentAtScrolledPosition(int i) {
        if(orderObjs.length > (page * CONSTANTS.ORDERS.SELECT_PANEL_ITEM_H) + i) return true;
        else return false;
    }
}
