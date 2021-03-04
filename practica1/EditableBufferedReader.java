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
            character = super.read();
            character = super.read();
            if (character == 2) { int aux = super.read();}
            switch ((char) character) {
                case 'D':       // Left
                    System.out.print((char)27 + "[D");
                    break;
                case 'C':       // Right
                    System.out.print((char)27 + "[C");
                    break;
                case 'A':       // Up
                    System.out.print((char)27 + "[A");
                    break;
                case 'B':       // Down
                    System.out.print((char)27 + "[B");
                    break;
                case 'H':       // Home
                    System.out.print((char)27 + "[H");
                    break;
                case 'F':       // End
                    System.out.print((char)27 + "[F");
                    break;
                case '2':       // Insert
                    System.out.print((char)27 + "[2~");
                    break;
                case '3':       // Del (suprimir)
                    System.out.print((char)27 + "[3~");
                    break;
                case '8':       // Backspace 
                    System.out.print((char)27 + "[8~");
                    break;
                default:
                    System.out.print((char)27 + "[D");
                    break;
            }
            character = 300;
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
            if (character != 300) {
                System.out.print((char)character);
                sb.append((char) character);
            }
        } while(character != 13);

        unsetRaw();

        return sb.toString();
    }

}
