package joazlazer.mods.amc.common.utility;

public enum GuiColor {
    BLACK(0),
    BLUE(1),
    GREEN(2),
    CYAN(3),
    RED(4),
    PURPLE(5),
    ORANGE(6),
    LIGHTGRAY(7),
    GRAY(8),
    LIGHTBLUE(9),
    LIME(10),
    TURQUISE(11),
    PINK(12),
    MAGENTA(13),
    YELLOW(14),
    WHITE(15),
    OBFUSCATED('k'),
    BOLD('l'),
    STRIKETHROUGH('m'),
    UNDERLINE('n'),
    ITALIC('o'),
    RESET('r');

    private int number = -1;
    private char character;

    GuiColor(int number) {
        this.number = number;
    }
    GuiColor(char character) {
        this.character = character;
    }

    @Override
    public String toString() {
        if (number != -1) return "\u00a7" + Integer.toHexString(number);
        else return "\u00a7" + character;
    }
}