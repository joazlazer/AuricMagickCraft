package joazlazer.mods.amc.client.gui.component;

import joazlazer.mods.amc.client.gui.IGuiAccess;
import java.util.ArrayList;

public class GuiButton extends GuiRectangle {
    public ArrayList<String> tooltip;
    public int ttTimer;

    public int u;
    public int v;
    public int uHover;
    public int vHover;
    public int uClicked;
    public int vClicked;
    public int uDisabled;
    public int vDisabled;
    public boolean hover;
    public boolean clicked;
    public boolean disabled;

    public GuiButton(int x, int y, int u, int v, int w, int h, int uHover, int vHover, int uClicked, int vClicked, int uDisabled, int vDisabled) {
        super(x, y, w, h);
        tooltip = new ArrayList<String>();
        this.u = u;
        this.v = v;
        this.uHover = uHover;
        this.vHover = vHover;
        this.uClicked = uClicked;
        this.vClicked = vClicked;
        this.uDisabled = uDisabled;
        this.vDisabled = vDisabled;
}

    public void update(IGuiAccess gui, int mouseX, int mouseY) {
        boolean tt = false;
        if(inRect(gui, mouseX, mouseY)) {
            ttTimer++;
            if(ttTimer >= 100) {
                tt = true;
            }
            if(!hover) {
                hover = true;
            }
        }
        else {
            ttTimer = 0;
        }
        this.draw(gui, 0, 0);
        if(tt && !disabled) drawString(gui, mouseX, mouseY, tooltip);
    }

    public void onClicked(IGuiAccess gui, int mouseX, int mouseY, int button) {
        if(inRect(gui, mouseX, mouseY)) {
            if(!disabled)
                gui.onButtonClicked(this, mouseX, mouseY, button);
        }
    }

    @Override
    public void draw(IGuiAccess gui, int u, int v) {
        gui.renderRectangle(gui.getGuiLeft() + x, gui.getGuiTop() + y, getEffectiveU(), getEffectiveV(), w, h);
    }

    public int getEffectiveU() {
        if(disabled) return uDisabled;
        else if(clicked) return uClicked;
        else if(hover) return uHover;
        else return u;
    }

    public int getEffectiveV() {
        if (disabled) return vDisabled;
        else if(clicked) return vClicked;
        else if(hover) return vHover;
        else return v;
    }
}
