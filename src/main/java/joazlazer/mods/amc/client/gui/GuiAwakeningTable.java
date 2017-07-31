package joazlazer.mods.amc.client.gui;

import joazlazer.mods.amc.api.order.OrderBase;
import joazlazer.mods.amc.client.gui.component.GuiRectangle;
import joazlazer.mods.amc.common.container.ContainerAwakeningTable;
import joazlazer.mods.amc.common.reference.Reference;
import joazlazer.mods.amc.common.tileentity.TileEntityAwakeningTable;
import joazlazer.mods.amc.utility.GuiColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class GuiAwakeningTable extends GuiContainerAMC {
    public static final int WIDTH = 398;
    public static final int HEIGHT = 222;
    public static final int TEXTURE_SIZE = 512;

    public static final int AWAKEN_BUTTON_X = 87;
    public static final int AWAKEN_BUTTON_Y = 195;
    public static final int AWAKEN_BUTTON_W = 98;
    public static final int AWAKEN_BUTTON_H = 14;

    private final TileEntityAwakeningTable te;
    private static final ResourceLocation background = new ResourceLocation(Reference.MOD_ID, "textures/gui/awakening_table.png");
    private GuiOrderList orderList;
    private GuiOrderInfoList orderInfoList;
    private int selectedOrderPanelIndex = -1;
    private OrderBase[] orderObjects;
    GuiButton awakenButton;

    public GuiAwakeningTable(TileEntityAwakeningTable tileEntity, ContainerAwakeningTable container) {
        super(container, WIDTH, HEIGHT);
        te = tileEntity;

        // Call to order registry
        List<OrderBase> orders = OrderBase.registry.getValues();
        orderObjects = new OrderBase[orders.size()];
        orders.toArray(orderObjects);
    }

    @Override
    public void initGui() {
        super.initGui();
        orderList = new GuiOrderList(this);
        orderInfoList = new GuiOrderInfoList(this);
        buttonList.clear();
        buttonList.add(awakenButton = new GuiButton(0, this.getGuiLeft() + AWAKEN_BUTTON_X, this.getGuiTop() + AWAKEN_BUTTON_Y, AWAKEN_BUTTON_W, AWAKEN_BUTTON_H, I18n.format("container.amc:awakening_table.awaken")));
        updateScreen();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if(selectedOrderPanelIndex == -1) {
            awakenButton.enabled = false;
        } else awakenButton.enabled = true;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.drawDefaultBackground();

        GlStateManager.pushMatrix(); {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            this.mc.getTextureManager().bindTexture(background);
            drawModalRectWithCustomSizedTexture(guiLeft, guiTop, 0f, 0f, WIDTH, HEIGHT, TEXTURE_SIZE, TEXTURE_SIZE);
        }
        GlStateManager.popMatrix();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        orderList.drawScreen(mouseX, mouseY, partialTicks);
        orderInfoList.drawScreen(mouseX, mouseY, partialTicks);
        orderList.drawForeground(mouseX, mouseY);
        orderInfoList.drawForeground(mouseX, mouseY);
    }

    private void selectOrder() {
        orderInfoList = new GuiOrderInfoList(this);
    }

    public OrderBase getSelectedOrder() {
        if(selectedOrderPanelIndex == -1) return null;
        else return orderObjects[selectedOrderPanelIndex];
    }

    @Override
    protected void actionPerformed(net.minecraft.client.gui.GuiButton button) {
        if(button.id == 0) {
            te.awakeningTicks = 0;
            Minecraft.getMinecraft().displayGuiScreen(new GuiAwakeningScreen(te));
        }
    }

    abstract static class GuiAwakeningScrollingList extends GuiScrollingList {
        protected GuiAwakeningTable parent;

        public GuiAwakeningScrollingList(GuiAwakeningTable parentGui, int left, int top, int width, int height) {
            super(Minecraft.getMinecraft(), width, height,
                    parentGui.getGuiTop() + top, parentGui.getGuiTop() + top + height,parentGui.getGuiLeft() + left,
                    1, parentGui.width, parentGui.height);
            parent = parentGui;
            this.setHeaderInfo(true, getHeaderHeight());
            this.scrollBarShadingColor = new Color(0x3E2C1E);
            this.scrollBarFillColor = new Color(0x523828);
            this.scrollBarBackground = new Color(0,0,0, 36);
        }

        @Override protected int getSize() { return 0; }
        @Override protected void elementClicked(int index, boolean doubleClick) { }
        @Override protected boolean isSelected(int index) { return false; }
        @Override protected void drawBackground() {}
        @Override protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotBuffer, Tessellator tess) { }

        abstract protected int getHeaderHeight();
    }

    private static class GuiOrderList extends GuiAwakeningScrollingList {
        private static final int SLOT_SIZE = 20;
        private static final int SLOTS_X = 2;
        private static final int SLOTS_Y = 5;
        private static final int SLOT_ORDER_OFFSET = 2;
        private static final int SLOT_SPRITE_U = 0;
        private static final int SLOT_SPRITE_V = 224;
        private static final int SLOT_HOVERED_SPRITE_U = 20;
        private static final int SLOT_HOVERED_SPRITE_V = 224;
        private static final int SLOT_CLICKED_SPRITE_U = 40;
        private static final int SLOT_CLICKED_SPRITE_V = 224;

        private static final int LIST_WIDTH = 46;
        private static final int LIST_HEIGHT = 120;
        private static final int LIST_LEFT = 16;
        private static final int LIST_TOP = 16;

        private int tooltipIndex = -1;

        public GuiOrderList(GuiAwakeningTable parentGui) {
            super(parentGui, LIST_LEFT, LIST_TOP, LIST_WIDTH, LIST_HEIGHT);
        }

        @Override
        protected int getHeaderHeight() {
            int hHeight = ((parent.orderObjects.length - 1) / 2 + 1) * SLOT_SIZE;
            return hHeight;
        }

        @Override
        protected void drawHeader(int entryRight, int relativeY, Tessellator tess) {
            tooltipIndex = -1;

            // Loop through each order
            for(int i = 0; i < parent.orderObjects.length; ++i) {
                // Relative X position created by an offset from guiLeft plus the x position as defined by the order grid
                int x = LIST_LEFT + ((i % SLOTS_X) * SLOT_SIZE);
                // Relative Y position created by the current scrolled position (which previously included guiTop) plus
                // the y position as defined by the order grid. Finally, subtract an arbitrary vertical offset depending
                // on whether the grid is full or not
                int y = relativeY - parent.getGuiTop() + ((i / SLOTS_X) * SLOT_SIZE) + getArbitraryYOffset() ;

                // Retrieve the current OrderBase representing the order being rendered
                OrderBase order = parent.orderObjects[i];

                // Start by using the normal slot texture
                int texU = SLOT_SPRITE_U;
                int texV = SLOT_SPRITE_V;

                // Is this order slot selected?
                boolean isSelected = parent.selectedOrderPanelIndex != -1 && parent.selectedOrderPanelIndex == i;

                // Is the mouse button over this scroll panel?
                boolean isHovering = mouseX >= this.left && mouseX <= this.left + this.listWidth &&
                        mouseY >= this.top && mouseY <= this.bottom;

                // Is the mouse button over this order slot (doesn't account for scissor hiding)
                boolean isMouseOverSlot = GuiRectangle.inRect(parent, this.mouseX ,this.mouseY, x, y, SLOT_SIZE, SLOT_SIZE);

                // If the mouse is hovering over the panel and inside the scroll panel?
                if(isMouseOverSlot && isHovering) {
                    // Mark this order to render a tooltip for it
                    tooltipIndex = i;
                    if(!isSelected) {
                        // If the user is just hovering, use the hovering slot texture
                        texU = SLOT_HOVERED_SPRITE_U;
                        texV = SLOT_HOVERED_SPRITE_V;
                    }
                }

                if(isSelected) {
                    // If this order is selected, use the selected slot texture
                    texU = SLOT_CLICKED_SPRITE_U;
                    texV = SLOT_CLICKED_SPRITE_V;
                }

                // Finally, draw the slot using the selected texture
                Minecraft.getMinecraft().getTextureManager().bindTexture(GuiAwakeningTable.background);
                GuiAwakeningTable.drawModalRectWithCustomSizedTexture(parent.guiLeft + x, parent.guiTop + y, texU, texV, SLOT_SIZE, SLOT_SIZE, TEXTURE_SIZE, TEXTURE_SIZE);

                // Draw order icon
                joazlazer.mods.amc.client.render.RenderHelper.drawOrderIcon(parent.guiLeft + x + SLOT_ORDER_OFFSET, parent.guiTop + y + SLOT_ORDER_OFFSET, order);
            }
        }

        public void drawForeground(int mouseX, int mouseY) {
            if(tooltipIndex != -1) {
                parent.drawHoverString(parent.orderObjects[tooltipIndex].getTooltip(), mouseX, mouseY);
            }
        }

        @Override
        protected void clickHeader(int x, int y) {
            // Handle clicking on order
            for(int i = 0; i < parent.orderObjects.length; ++i) {
                int left = (i % SLOTS_X) * SLOT_SIZE;
                int top = (i / SLOTS_X) * SLOT_SIZE;
                if(x >= left && x <= left + SLOT_SIZE && y >= top + getArbitraryYOffset() && y <= top + SLOT_SIZE + getArbitraryYOffset()) {
                    parent.selectedOrderPanelIndex = i;
                    parent.selectOrder();
                }
            }
        }
        private int getArbitraryYOffset() {
            int offset = -(parent.orderObjects.length <= SLOTS_X * SLOTS_Y ? 12 : 4);
            if(parent.orderObjects.length <= 8) {
                offset -= (int)(SLOT_SIZE * (2f - 0.5f * (float)((parent.orderObjects.length - 1) / 2)));
            }
            return offset;
        }
    }

    private static class GuiOrderInfoList extends GuiAwakeningScrollingList {
        private static final int LARGE_SLOT_U = 104;
        private static final int LARGE_SLOT_V = 224;
        private static final int LARGE_SLOT_SIZE = 40;
        private static final int LARGE_SLOT_ICON_OFFSET = 4;
        private static final int LARGE_SLOT_OFFSET = 8;

        private static final int ORDER_NAME_X = 48;
        private static final int ORDER_NAME_Y = 3;
        private static final int ORDER_INFO_X = 50;
        private static final int ORDER_INFO_Y = 39;

        private static final int SELECT_MESSAGE_OFFSET = 0;

        private static final int LIST_WIDTH = 292;
        private static final int LIST_HEIGHT = 155;
        private static final int LIST_LEFT = 90;
        private static final int LIST_TOP = 16;

        private List<ITextComponent> lines = null;

        public GuiOrderInfoList(GuiAwakeningTable parentGui) {
            super(parentGui, LIST_LEFT, LIST_TOP, LIST_WIDTH, LIST_HEIGHT);
            if(parent.selectedOrderPanelIndex != -1) {
                List<String> stringList = parent.orderObjects[parent.selectedOrderPanelIndex].getInfo();
                lines = new ArrayList<>();
                for (String string : stringList) {
                    if (string == null) {
                        lines.add(null);
                        continue;
                    }

                    ITextComponent chat = new TextComponentString(string);
                    int maxTextLength = LIST_WIDTH - ORDER_INFO_X - 6 - 12;
                    if (maxTextLength >= 0) {
                        lines.addAll(GuiUtilRenderComponents.splitText(chat, maxTextLength, Minecraft.getMinecraft().fontRenderer, false, true));
                    }
                }
            }
            this.setHeaderInfo(true, getHeaderHeight());
        }

        @Override
        protected int getHeaderHeight() {
            if(lines == null) return 10;
            int spellPanelHeight = 0; // TODO implement
            int orderInfoHeight = ORDER_INFO_Y + (lines.size() * 10) + 12;
            return Math.max(spellPanelHeight, orderInfoHeight);
        }

        @Override
        protected void drawHeader(int entryRight, int relativeY, Tessellator tess) {
            int x = LIST_LEFT + LARGE_SLOT_OFFSET;
            int y = relativeY + getArbitraryYOffset() - parent.getGuiTop() + LARGE_SLOT_OFFSET;
            Color whiteText = new Color(230, 230, 230);

            if(parent.getSelectedOrder() != null) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(GuiAwakeningTable.background);
                GuiAwakeningTable.drawModalRectWithCustomSizedTexture(parent.guiLeft + x, parent.guiTop + y, LARGE_SLOT_U, LARGE_SLOT_V, LARGE_SLOT_SIZE, LARGE_SLOT_SIZE, TEXTURE_SIZE, TEXTURE_SIZE);

                // Draw order icon
                joazlazer.mods.amc.client.render.RenderHelper.drawLargeOrderIcon(parent.guiLeft + x + LARGE_SLOT_ICON_OFFSET, parent.guiTop + y + LARGE_SLOT_ICON_OFFSET, parent.getSelectedOrder());

                // Draw order name text
                GlStateManager.pushMatrix(); {
                    GlStateManager.scale(2.0f, 2.0f, 1.0f);
                    parent.fontRenderer.drawStringWithShadow(parent.getSelectedOrder().getName(), (float)(parent.guiLeft + x + ORDER_NAME_X) / 2f, (float)(parent.guiTop + y + ORDER_NAME_Y) / 2f, new Color(254, 242, 76).getRGB());
                    GlStateManager.scale(0.5f, 0.5f, 1.0f);
                }
                GlStateManager.popMatrix();

                // Draw description text
                Color grayText = new Color(130, 130, 130);
                parent.drawString(parent.fontRenderer, GuiColor.ITALIC + parent.getSelectedOrder().getDescription(), parent.guiLeft + x + ORDER_NAME_X, parent.guiTop + y + ORDER_NAME_Y + 20, grayText.getRGB());

                // Draw info text
                Color lightGrayText = new Color(180, 180, 180);
                int top = ORDER_INFO_Y + parent.guiTop + y;
                boolean renderWhite = true;
                for (ITextComponent line : lines) {
                    if (line != null) {
                        GlStateManager.enableBlend();
                        GlStateManager.enableAlpha();
                        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(line.getFormattedText(), parent.guiLeft + x + ORDER_INFO_X, top, renderWhite ? whiteText.getRGB() : lightGrayText.getRGB());
                        GlStateManager.disableAlpha();
                        GlStateManager.disableBlend();
                    } else renderWhite = !renderWhite;
                    top += 10;
                }
            } else {
                parent.drawString(parent.fontRenderer, GuiColor.ITALIC + I18n.format("container." + Reference.MOD_ID + ":awakening_table.select_message"), x + parent.guiLeft + SELECT_MESSAGE_OFFSET, y + parent.guiTop + SELECT_MESSAGE_OFFSET, whiteText.getRGB());
            }
        }

        public void drawForeground(int mouseX, int mouseY) {
            // TODO implement if needed
        }

        @Override
        protected void clickHeader(int x, int y) {
            // TODO implement
        }

        private int getArbitraryYOffset() {
            int offset = -4;
            if(getHeaderHeight() < LIST_HEIGHT) {
                int difference = LIST_HEIGHT - getHeaderHeight();
                offset -= difference / 2;
                offset += 2;
            }
            return offset;
        }
    }
}
