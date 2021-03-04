

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
        if (character == 27) {
            character = read();
            character = read();
            switch ((char) character) {
                case 'D':
                    character = 256;
                    break;
                default:
                    character = 257;
                    break;
            }
        }
        return character;
    }

    @Override
    public String readLine() throws IOException {

        setRaw();

        StringBuilder sb = new StringBuilder();
        int character = 0;
        do {
            character = read();
            sb.append((char) character);
        } while(character != 13);

        unsetRaw();

        return sb.toString();
    }

}
