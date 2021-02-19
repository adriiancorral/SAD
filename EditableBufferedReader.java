import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class EditableBufferedReader extends BufferedReader {

    public EditableBufferedReader(Reader in) {
        super(in);
    }

    public void setRaw() {

    }

    public void unsetRaw() {

    }

    public int read() {
        return 0;
    }

    @Override
    public String readLine() throws IOException {
        return super.readLine();
    }

}
