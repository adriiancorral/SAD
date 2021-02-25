package main_package_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class EditableBufferedReader extends BufferedReader {

    public EditableBufferedReader(Reader in) {
        super(in);
    }

    public void setRaw() {
        try {
            String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void unsetRaw() {
        try {
            String[] cmd = {"/bin/sh", "-c", "stty cooked </dev/tty"};
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public int read() throws IOException {
        return super.read();
    }

    @Override
    public String readLine() throws IOException {

        setRaw();

        StringBuilder sb = new StringBuilder();
        int character = read();
        while(character != 10) {
            if (character == 27) {
                character = read();
                character = read();
            }
            sb.append((char)character);
            character = read();
        }

        unsetRaw();

        return sb.toString();
    }

}
