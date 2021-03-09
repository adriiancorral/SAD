import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class EditableBufferedReader extends BufferedReader {

    private static final int ENTER = 13;
    private static final int ESC = 27;
    private static final int SPECIAL = 300;
    private static final int LEFT = 'D';
    private static final int RIGHT = 'C';
    private static final int UP = 'A';
    private static final int DOWN = 'B';
    private static final int HOME = 'H';
    private static final int END = 'F';
    private static final int INSERT = '2';
    private static final int DEL = '3';
    private static final int BACKSPACE = 127;

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
                case LEFT:
                    line.left();
                    break;
                case RIGHT:
                    line.right();
                    break;
                case UP:
                    line.up();
                    break;
                case DOWN:
                    line.down();
                    break;
                case HOME:
                    line.home();
                    break;
                case END:
                    line.end();
                    break;
                case INSERT:
                    character = super.read();   // Avoid '~'
                    line.insert();
                    break;
                case DEL:
                    character = super.read();   // Avoid '~'
                    line.delete();
                    break;
            }
            character = SPECIAL;
        } else if (character == BACKSPACE) {
            line.backspace();
        }
        return character;
    }

    @Override
    public String readLine() throws IOException {

        setRaw();

        int character = 0;

        do {
            character = read();
            if (character != SPECIAL && character != ENTER) {
                line.addChar((char)character);
            }
        } while(character != ENTER);

        unsetRaw();

        return line.toString();
    }

}
