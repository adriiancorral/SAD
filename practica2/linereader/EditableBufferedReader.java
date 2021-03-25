package practica2.linereader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class EditableBufferedReader extends BufferedReader {

    public EditableBufferedReader(Reader in) {
        super(in);
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
        if (character == Constants.ESC) {
            character = super.read();   // Avoid '['
            character = super.read();
            switch (character) {
                case 'A':
                    return Constants.UP;
                case 'B':
                    return Constants.DOWN;
                case 'C':
                    return Constants.RIGHT;
                case 'D':
                    return Constants.LEFT;
                case 'H':
                    return Constants.HOME;
                case 'F':
                    return Constants.END;
                case '1':
                    character = super.read();   // Avoid '~'
                    return Constants.HOME;
                case '2':
                    character = super.read();   // Avoid '~'
                    return Constants.INSERT;
                case '3':
                    character = super.read();   // Avoid '~'
                    return Constants.DEL;
                case '4':
                    character = super.read();   // Avoid '~'
                    return Constants.END;
            }

        } else if (character == 127) return Constants.BACKSPACE;
        return character;
    }

    @Override
    public String readLine() throws IOException {

        setRaw();

        int character = 0;
        Line line = new Line();

        do {
            character = read();
            if (character < Constants.SPECIAL && character != Constants.ENTER) {
                line.addChar((char)character);
            } else {
                switch (character) {
                    case Constants.UP:
                        line.up();
                        break;
                    case Constants.DOWN:
                        line.down();
                        break;
                    case Constants.RIGHT:
                        line.right();
                        break;
                    case Constants.LEFT:
                        line.left();
                        break;
                    case Constants.HOME:
                        line.home();
                        break;
                    case Constants.INSERT:
                        line.insert();
                        break;
                    case Constants.DEL:
                        line.delete();
                        break;
                    case Constants.END:
                        line.end();
                        break;
                    case Constants.BACKSPACE:
                        line.backspace();
                        break;
                }
            }
        } while(character != Constants.ENTER);

        unsetRaw();

        return line.toString();
    }

}
