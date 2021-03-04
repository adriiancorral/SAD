

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
        /*int character = super.read();
        if (character == 27) {
            character = super.read();
            character = super.read();
            if (character == 2) { int aux = super.read();}
            switch ((char) character) {
                case 'D':       // Left
                    character = 300;
                    break;
                case 'C':       // Right
                    character = 301;
                    break;
                case 'A':       // Up
                    character = 302;
                    break;
                case 'B':       // Down
                    character = 303;
                    break;
                case 'H':       // Home
                    character = 304;
                    break;
                case 'F':       // End
                    character = 305;
                    break;
                case '2':       // Insert
                    character = 306;
                    break;
                case 'd':       // Del
                    character = 307;
                    break;
                case 'f':       // BackDel
                    character = 308;
                    break;
                default:
                    character = 400;
                    break;
            }
        }
        return character;*/
        return super.read();
    }

    @Override
    public String readLine() throws IOException {

        setRaw();

        StringBuilder sb = new StringBuilder();
        int character = 0;
        do {
            character = read();
            System.out.print((char)character);
            sb.append((char) character);
        } while(character != 13);

        unsetRaw();

        return sb.toString();
    }

}
