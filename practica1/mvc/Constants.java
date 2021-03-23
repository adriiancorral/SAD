package mvc;

public abstract class Constants {

    // General constants
    public static final int ENTER = 13;
    public static final int ESC = 27;
    public static final int SPECIAL = 30000;

    public static final int UP = 30001;
    public static final int DOWN = 30002;
    public static final int RIGHT = 30003;
    public static final int LEFT = 30004;
    public static final int HOME = 30005;
    public static final int END = 30006;
    public static final int INSERT = 30007;
    public static final int DEL = 30008;
    public static final int BACKSPACE = 30009;

    // Escape constants
    public static final char ESC_ESC = (char)27;

    public static final String ESC_LEFT = ESC_ESC + "[D";
    public static final String ESC_RIGHT = ESC_ESC + "[C";
    public static final String ESC_UP = ESC_ESC + "[A";
    public static final String ESC_DOWN = ESC_ESC + "[B";
    public static final String ESC_DEL = ESC_ESC + "[3~";
    public static final String ESC_INSERT = ESC_ESC + "[2~";
}
