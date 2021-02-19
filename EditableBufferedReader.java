import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class EditableBufferedReader extends BufferedReader {

    public EditableBufferedReader(Reader in) {
        super(in);
    }

    public void setRaw() throws InterruptedException, IOException {
        String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
        Runtime.getRuntime().exec(cmd).waitFor();
    }

    public void unsetRaw() throws InterruptedException, IOException {
        String[] cmd = {"/bin/sh", "-c", "stty cooked </dev/tty"};
        Runtime.getRuntime().exec(cmd).waitFor();
    }

    @Override
    public int read() throws IOException {
        return super.read();
    }

    @Override
    public String readLine() throws IOException {
        return super.readLine();
    }

}
