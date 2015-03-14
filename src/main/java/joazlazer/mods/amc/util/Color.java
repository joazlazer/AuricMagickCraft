package joazlazer.mods.amc.util;

public class Color {

    int r, g, b, a;

    public Color() {
        this.r = 0;
        this.g = 0;
        this.b = 0;
        this.a = 255;
    }

    public Color(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 255;
    }

    public Color(int rgba) {
        this.a = (int) ((float) (rgba >> 24 & 255) / 255.0F);
        this.r = (int) ((float) (rgba >> 16 & 255) / 255.0F);
        this.g = (int) ((float) (rgba >> 8 & 255) / 255.0F);
        this.b = (int) ((float) (rgba & 255) / 255.0F);
    }

    public int toRGBA() {
        return ((a << 24) + (r << 16) + (g << 8) + b);
    }

    public int toRGB() {
        return ((r << 16) + (g << 8) + b);
    }

    public Color setRGB(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.g = g;
        return this;
    }

    public Color setRGB(int rgb) {
        this.r = (int) ((float) (rgb >> 16 & 255) / 255.0F);
        this.g = (int) ((float) (rgb >> 8 & 255) / 255.0F);
        this.b = (int) ((float) (rgb & 255) / 255.0F);
        return this;
    }

    public Color setRGBA(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        return this;
    }

    public Color setRGBA(int rgba) {
        this.a = (int) ((float) (rgba >> 24 & 255) / 255.0F);
        this.r = (int) ((float) (rgba >> 16 & 255) / 255.0F);
        this.g = (int) ((float) (rgba >> 8 & 255) / 255.0F);
        this.b = (int) ((float) (rgba & 255) / 255.0F);
        return this;
    }

    public int getRed() {
        return r;
    }

    public Color setRed(int r) {
        this.r = r;
        return this;
    }

    public int getBlue() {
        return b;
    }

    public Color setBlue(int b) {
        this.b = b;
        return this;
    }

    public int getGreen() {
        return g;
    }

    public Color setGreen(int g) {
        this.g = g;
        return this;
    }

    public int getAlpha() {
        return a;
    }

    public Color setAlpha(int a) {
        this.a = a;
        return this;
    }

    public Color setOpacityPercent(double percent) {
        this.a = (byte) (255.0D * (percent / 100.0D));
        return this;
    }
}
