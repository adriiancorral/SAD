import java.io.*;

public class EditableBufferedReader extends BufferedReader {

    public EditableBufferedReader(Reader in) {
        super(in);
    }
    
    public void setRaw() {
        
    }

    public void unsetRaw() {

    }

    @Override
    public int read() {
        return 0;
    }

    @Override
    public String readLine() throws IOException {
        return super.readLine();
    }

}
