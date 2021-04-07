/**
 * MySocket
 */
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MySocket extends Socket {

    private String user;

    public MySocket(String host, int port, String user) throws IOException {
        super(host, port);
        this.user = user;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user =user;
    }

    public void println(String line) {
        // En desarrollo
        
    }

    public String readLine() {
        // En desarrollo
        return null;
    }
    
}
