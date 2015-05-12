package joazlazer.mods.amc.client.gui;

import joazlazer.mods.amc.AuricMagickCraft;
import joazlazer.mods.amc.api.order.OrderBase;
import joazlazer.mods.amc.api.order.OrderRegistry;
import joazlazer.mods.amc.api.spell.SpellBase;
import joazlazer.mods.amc.client.gui.component.GuiButton;
import joazlazer.mods.amc.client.gui.component.GuiRectangle;
import joazlazer.mods.amc.client.render.RenderHelper;
import joazlazer.mods.amc.container.ContainerAwakeningTable;
import joazlazer.mods.amc.handlers.NetworkHandler;
import joazlazer.mods.amc.network.MessagePlayerRespawn;
import joazlazer.mods.amc.reference.Textures;
import joazlazer.mods.amc.tileentity.TileEntityAwakeningTable;
import joazlazer.mods.amc.util.Color;
import joazlazer.mods.amc.util.GuiColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GuiAwakeningTable extends GuiContainerAMC {

    private float progress;
    public int page = 0;
    public int maxPage = 1;
    public OrderBase selectedOrder;
    net.minecraft.client.gui.GuiButton awakenButton;
    private boolean isAwakening;
    public boolean closeSoon;

    @Override
    public void onButtonClicked(GuiButton button, int mouseX, int mouseY, int mouseButton) {

    }

    public static class CONSTANTS {
        public static class AWAKEN {
            public static final int BUTTON_X = 139;
            public static final int BUTTON_Y = 191;
            public static final int BUTTON_W = 70;
            public static final int BUTTON_H = 16;
            public static final float AWAKEN_OPACITY_FINISH = 180.0f;
            public static final float AWAKEN_SPEED = 3.0f;

        }

        public static class SPELLS {
            public static final int INFO_PANEL_X = 89;
            public static final int INFO_PANEL_Y = 97;
            public static final int INFO_PANEL_W = 112;
            public static final int INFO_PANEL_H = 80;
            public static final int INFO_PANEL_MAX_W = 7;
            public static final int INFO_PANEL_MAX_H = 5;
            public static final int INFO_PANEL_ITEM_W = 16;
            public static final int INFO_PANEL_ITEM_H = 16;
        }

        public static class GLOBAL {
            public static final int X_SIZE = 215;
            public static final int Y_SIZE = 192;
        }

        public static class TEXT {
            public static final String UNLOC_SPELLS = "container.amc:spells";
            public static final String UNLOC_ORDERS = "container.amc:orders";
            public static final int SPELLS_X = 85;
            public static final int SPELLS_Y = 78;
            public static final int ORDERS_X = 13;
            public static final int ORDERS_Y = 78;
            public static final int ORDER_NAME_X = 39;
            public static final int ORDER_NAME_Y = 18;
        }

        public static class ORDERS {
            public static final int SELECT_PANEL_ORDER_MAX_W = 3;
            public static final int SELECT_PANEL_ORDER_MAX_H = 5;
            public static final int SELECT_PANEL_START_X = 16;
            public static final int SELECT_PANEL_START_Y = 97;
            public static final int SELECT_PANEL_ITEM_W = 16;
            public static final int SELECT_PANEL_ITEM_H = 16;

            public static final int SELECTION_CIRCLE_ICON_START_X = 25;
            public static final int SELECTION_CIRCLE_ICON_START_Y = 26;
        }
    }

    @Override
    public void initGui() {
        // Calculate the x and y of the top corner of the gui.
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.guiLeft = x - 4;
        this.guiTop = y - 24;

        buttonList.clear();
        buttonList.add(awakenButton = new net.minecraft.client.gui.GuiButton(0, this.getGuiLeft() + CONSTANTS.AWAKEN.BUTTON_X, this.getGuiTop() + CONSTANTS.AWAKEN.BUTTON_Y, CONSTANTS.AWAKEN.BUTTON_W, CONSTANTS.AWAKEN.BUTTON_H, StatCollector.translateToLocal("container.amc:awaken")));
        super.initGui();
    }

    public OrderBase[] orderObjs;
    public SpellBase[][] spellObjs;
    public GuiRectangle[] orders;
    public GuiRectangle[][] spells;

    public GuiAwakeningTable(InventoryPlayer inventory, TileEntityAwakeningTable te) {
        super(new ContainerAwakeningTable(inventory, te));
        xSize = CONSTANTS.GLOBAL.X_SIZE;
        ySize = CONSTANTS.GLOBAL.Y_SIZE;

        // TODO: TEST
        AuricMagickCraft.PlayerTracker.awaken(inventory.player, new OrderBase() {
            @Override
            public String getUnlocName() {
                return "mahnameisbahb";
            }
        });

        orderObjs = new OrderBase[OrderRegistry.getOrders().size()];
        spellObjs = new SpellBase[OrderRegistry.getOrders().size()][];
        for (int i = 0; i < OrderRegistry.getOrders().size(); i++) {
            OrderBase order = (OrderBase) OrderRegistry.getOrders().get(i);
            orderObjs[i] = order;
            for (int j = 0; j < order.getSpells().size(); j++) {
                spellObjs[i] = new SpellBase[order.getSpells().size()];
                spellObjs[i][j] = order.getSpells().get(j);
            }
        }
        System.out.println(OrderRegistry.getOrders().size());
        System.out.println(OrderRegistry.getOrder(0).getSpells().size());

        orders = new GuiRectangle[Math.min(orderObjs.length, CONSTANTS.ORDERS.SELECT_PANEL_ORDER_MAX_W * CONSTANTS.ORDERS.SELECT_PANEL_ORDER_MAX_H)];
        spells = new GuiRectangle[OrderRegistry.getOrders().size()][];

        for (int i = 0; i < orders.length && i < CONSTANTS.ORDERS.SELECT_PANEL_ORDER_MAX_W * CONSTANTS.ORDERS.SELECT_PANEL_ORDER_MAX_H; i++) {
            int xItemPos = i / CONSTANTS.ORDERS.SELECT_PANEL_ORDER_MAX_W;
            int yItemPos = i % CONSTANTS.ORDERS.SELECT_PANEL_ORDER_MAX_W;
            int xPos = CONSTANTS.ORDERS.SELECT_PANEL_START_X + (xItemPos * CONSTANTS.ORDERS.SELECT_PANEL_ITEM_W);
            int yPos = CONSTANTS.ORDERS.SELECT_PANEL_START_Y + (yItemPos * CONSTANTS.ORDERS.SELECT_PANEL_ITEM_H);
            orders[i] = new GuiRectangle(xPos, yPos, CONSTANTS.ORDERS.SELECT_PANEL_ITEM_W, CONSTANTS.ORDERS.SELECT_PANEL_ITEM_H);
        }

        for (int i = 0; i < OrderRegistry.getOrders().size(); i++) {
            spells[i] = new GuiRectangle[Math.min(spellObjs[i].length, CONSTANTS.SPELLS.INFO_PANEL_MAX_W * CONSTANTS.SPELLS.INFO_PANEL_MAX_H)];

            for (int j = 0; j < spells[i].length && j < CONSTANTS.SPELLS.INFO_PANEL_MAX_W * CONSTANTS.SPELLS.INFO_PANEL_MAX_H; j++) {
                int xItemPos = j / CONSTANTS.SPELLS.INFO_PANEL_MAX_W;
                int yItemPos = j % CONSTANTS.SPELLS.INFO_PANEL_MAX_W;
                int xPos = CONSTANTS.SPELLS.INFO_PANEL_X + (xItemPos * CONSTANTS.SPELLS.INFO_PANEL_W);
                int yPos = CONSTANTS.SPELLS.INFO_PANEL_Y + (yItemPos * CONSTANTS.SPELLS.INFO_PANEL_H);
                System.out.println(spells[i].length);
                System.out.println(j);
                spells[i][j] = new GuiRectangle(xPos, yPos, CONSTANTS.SPELLS.INFO_PANEL_ITEM_W, CONSTANTS.SPELLS.INFO_PANEL_ITEM_H);
            }
        }

        // Reset the awakening variable.
        isAwakening = false;

        // Reset the progress of the awakening.
        progress = 0.0f;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f0, int mouseX, int mouseY) {
        if (!isAwakening) {
            // Reset the OpenGL colors to prepare for rendering.
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            // Bind the gui texture.
            this.mc.renderEngine.bindTexture(Textures.Gui.AWAKENING_TABLE);

            // Calculate the x and y of the top corner of the gui.
            int x = (width - xSize) / 2;
            int y = (height - ySize) / 2;
            this.guiLeft = x - 4;
            this.guiTop = y - 24;

            // Enable transparency.
            GL11.glEnable(GL11.GL_BLEND);

            // Draw the background.
            this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

            for (int i = 0; i < orders.length; i++) {
                GuiRectangle rect = orders[i];
                if (isOrderPresentAtScrolledPosition(i)) {
                    RenderHelper.drawOrderIcon(this.getGuiLeft() + rect.getX(), this.getGuiTop() + rect.getY(), orderObjs[CONSTANTS.ORDERS.SELECT_PANEL_ITEM_H * page + i]);
                }
            }

            if (selectedOrder != null) {
                RenderHelper.drawLargeOrderIcon(this.getGuiLeft() + CONSTANTS.ORDERS.SELECTION_CIRCLE_ICON_START_X, this.getGuiTop() + CONSTANTS.ORDERS.SELECTION_CIRCLE_ICON_START_Y, selectedOrder);

                int index = OrderRegistry.getOrders().indexOf(selectedOrder);
                for (int i = 0; i < spells[index].length; i++) {
                    GuiRectangle rect = spells[index][i];
                    if (spellObjs[index][i] != null) {
                        RenderHelper.drawSpellIcon(this.getGuiLeft() + rect.getX(), this.getGuiTop() + rect.getY(), spellObjs[index][i]);
                    }
                }
            }
        }
    }

    @Override
    public void updateScreen() {
        if (isAwakening) {
            progress += CONSTANTS.AWAKEN.AWAKEN_SPEED;

            if (progress > CONSTANTS.AWAKEN.AWAKEN_OPACITY_FINISH - (CONSTANTS.AWAKEN.AWAKEN_OPACITY_FINISH * 0.1f)) {
                closeSoon = true;
            }

            if (progress >= CONSTANTS.AWAKEN.AWAKEN_OPACITY_FINISH) {
                // Teleport the player to the respawn location.
                NetworkHandler.Network.sendToServer(new MessagePlayerRespawn(Minecraft.getMinecraft().thePlayer.getDisplayName()));

                // Send a message to the player.
                this.mc.thePlayer.addChatMessage(new ChatComponentText(GuiColor.ITALIC + GuiColor.CYAN.toString() + "Your awakened senses are too much for you to handle."));
                this.mc.thePlayer.addChatMessage(new ChatComponentText(GuiColor.ITALIC + GuiColor.CYAN.toString() + "You have been returned to your hearth."));

                // Send a packet of awakening.
                AuricMagickCraft.PlayerTracker.awaken(Minecraft.getMinecraft().thePlayer, selectedOrder);

                // Close the gui.
                Minecraft.getMinecraft().displayGuiScreen(null);
            }
        } else {
            if (selectedOrder != null) {
                awakenButton.enabled = true;
            } else awakenButton.enabled = false;
            super.updateScreen();
        }
    }

    /* (non-Javadoc)
	 * @see net.minecraft.client.gui.GuiScreen#drawBackground(int)
	 */
    @Override
    public void drawBackground(int par1) {
        if (isAwakening) {
            // Get the correct scaled width and height.
            ScaledResolution scaledresolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
            int width = scaledresolution.getScaledWidth();
            int height = scaledresolution.getScaledHeight();

            // Disable the unwanted OpenGL effects.
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_ALPHA_TEST);

            // Get the current progress.
            float currProgress = this.progress;

            // Calculate the percent of opacity to render;
            float percent = (float)currProgress / CONSTANTS.AWAKEN.AWAKEN_OPACITY_FINISH;

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
	 * @see net.minecraft.client.gui.GuiScreen#drawScreen(int, int, float)
	 */
    @Override
    public void drawScreen(int par1, int par2, float par3) {
        if (isAwakening) this.drawBackground(0);
        else super.drawScreen(par1, par2, par3);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i0, int i1) {
        if (!isAwakening) {
            // Calculate the x and y of the top corner of the gui.
            int x = (width - xSize) / 2;
            int y = (height - ySize) / 2;
            this.guiLeft = x - 4;
            this.guiTop = y - 24;

            this.drawString(fontRendererObj, StatCollector.translateToLocal(CONSTANTS.TEXT.UNLOC_SPELLS), CONSTANTS.TEXT.SPELLS_X, CONSTANTS.TEXT.SPELLS_Y, new Color(32, 92, 52).toRGB());
            this.drawString(fontRendererObj, StatCollector.translateToLocal(CONSTANTS.TEXT.UNLOC_ORDERS), CONSTANTS.TEXT.ORDERS_X, CONSTANTS.TEXT.ORDERS_Y, new Color(32, 92, 52).toRGB());

            if (selectedOrder != null) {
                GL11.glScalef(2.0f, 2.0f, 1.0f);
                this.drawString(fontRendererObj, StatCollector.translateToLocal("order." + selectedOrder.getUnlocName() + ".name"), CONSTANTS.TEXT.ORDER_NAME_X, CONSTANTS.TEXT.ORDER_NAME_Y, new Color(248, 228, 48).toRGB());
                GL11.glScalef(0.5f, 0.5f, 1.0f);
            }

            for (int i = 0; i < orders.length; i++) {
                GuiRectangle rect = orders[i];
                if (isOrderPresentAtScrolledPosition(i) && orders[i].inRect(this, i0, i1)) {
                    OrderBase order = orderObjs[(CONSTANTS.ORDERS.SELECT_PANEL_ITEM_H * page) + i];
                    this.drawHoveringText(order.getTooltip(), i0 - getGuiLeft(), i1 - getGuiTop(), this.fontRendererObj);
                }
            }

            if (selectedOrder != null) {
                int index = OrderRegistry.getOrders().indexOf(selectedOrder);
                for (int i = 0; i < spells[index].length; i++) {
                    GuiRectangle rect = spells[index][i];
                    if (spellObjs[index][i] != null && spells[index][i].inRect(this, i0, i1)) {
                        this.drawHoveringText(spellObjs[index][i].getTooltip(), i0 - getGuiLeft(), i1 - getGuiTop(), this.fontRendererObj);
                    }
                }
            }

            GL11.glColor4f(128f, 128f, 128f, 255f);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (!isAwakening) {
            for (int i = 0; i < orders.length; i++) {
                GuiRectangle rect = orders[i];
                if (rect.inRect(this, mouseX, mouseY) && isOrderPresentAtScrolledPosition(i)) {
                    OrderBase order = orderObjs[CONSTANTS.ORDERS.SELECT_PANEL_ITEM_H * page + i];
                    if (selectedOrder == order) selectedOrder = null;
                    else selectedOrder = order;
                }
            }

            System.out.println("mouseClicked");
            System.out.println(awakenButton.xPosition);
            System.out.println(awakenButton.yPosition);
            System.out.println(awakenButton.width);
            System.out.println(awakenButton.height);
            System.out.println(mouseX);
            System.out.println(mouseY);

            if (mouseX >= awakenButton.xPosition && mouseY >= awakenButton.yPosition && mouseX <= awakenButton.xPosition + awakenButton.width && mouseY <= awakenButton.yPosition + awakenButton.height) {
                System.out.println("InRect");
                this.actionPerformed(awakenButton);
            }
        }
    }

    public boolean isOrderPresentAtScrolledPosition(int i) {
        if (orderObjs.length > (page * CONSTANTS.ORDERS.SELECT_PANEL_ITEM_H) + i) return true;
        else return false;
    }

    @Override
    protected void actionPerformed(net.minecraft.client.gui.GuiButton button) {
        if (!isAwakening) {
            System.out.println(button.id);
            switch (button.id) {
                case 0: {
                    isAwakening = true;
                    break;
                }
            }
        }
        super.actionPerformed(button);
    }
}
