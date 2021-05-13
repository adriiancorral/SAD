import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ConcurrentHashMap;

public class MyServerSocket extends ServerSocket {

    private ConcurrentHashMap<String, PrintWriter> writers = new ConcurrentHashMap<>();

    public MyServerSocket(int port) throws IOException {
        super(port);
    }

    public Socket accept() {
        try {
            return super.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void println(String line) {
        for (String key : writers.keySet()) {
            writers.get(key).println(line);
        }
        System.out.println(line);
    }

    public void newUser(String name, Socket cs) {
        for (String key : writers.keySet()) {
            writers.get(key).println("[User: " + name + "] : IS ONLINE");
            writers.get(name).println("[User: " + key + "] : IS ONLINE");
        }
        try {
            writers.put(name, new PrintWriter(cs.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[User: " + name + "] : IS ONLINE");
    }

    public void oldUser(String name) {
        writers.remove(name);
        for (String key : writers.keySet()) {
            writers.get(key).println("[User: " + name + "] : IS OFLINE");
        }
        System.out.println("[User: " + name + "] : IS OFLINE");
    }

}
