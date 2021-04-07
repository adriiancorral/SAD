import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MySocket extends Socket {

    private String user;
    private BufferedReader buff;
    private PrintWriter writer;

    public MySocket(String host, int port, String user) throws IOException {
        super(host, port);
        this.user = user;
        writer = new PrintWriter(this.getOutputStream());
        buff = new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public MySocket() {
        super();
    }

    public String getUser() {
        return this.user;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public BufferedReader getBuff() {
        return buff;
    }

    public void println(String line) {
        // En desarrollo
        writer.println("[User: " + user + "] : " + line);
    }

    public String readLine() {
        // En desarrollo
        try {
            return buff.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
