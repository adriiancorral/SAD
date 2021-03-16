import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class EditableBufferedReader extends BufferedReader {

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

    private Line line;

    public EditableBufferedReader(Reader in) {
        super(in);
        line = new Line();
    }

    public static void setRaw() {
        try {
            String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void unsetRaw() {
        try {
            String[] cmd = {"/bin/sh", "-c", "stty cooked </dev/tty"};
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public int read() throws IOException {
        int character = super.read();
        if (character == ESC) {
            character = super.read();   // Avoid '['
            character = super.read();
            switch (character) {
                case 'A':
                    return UP;
                case 'B':
                    return DOWN;
                case 'C':
                    return RIGHT;
                case 'D':
                    return LEFT;
                case 'H':
                    return HOME;
                case 'F':
                    return END;
                case '1':
                    character = super.read();   // Avoid '~'
                    return HOME;
                case '2':
                    character = super.read();   // Avoid '~'
                    return INSERT;
                case '3':
                    character = super.read();   // Avoid '~'
                    return DEL;
                case '4':
                    character = super.read();   // Avoid '~'
                    return END;
            }

        } else if (character == 127) return BACKSPACE;
        return character;
    }

    @Override
    public String readLine() throws IOException {

        setRaw();

        int character = 0;

        do {
            character = read();
            if (character < SPECIAL && character != ENTER) {
                line.addChar((char)character);
            } else {
                line.specialAction(character);
            }
        } while(character != ENTER);

        unsetRaw();

        return line.toString();
    }

}
