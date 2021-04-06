/**
 * MyServerSocket
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ConcurrentHashMap;

public class MyServerSocket extends ServerSocket {

    private ConcurrentHashMap<String, EchoClient> clients = new ConcurrentHashMap<>();

    public MyServerSocket(int port) throws IOException {
        super(port);
    }

    public MySocket accept() {
        try {
            return (MySocket) super.accept();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
