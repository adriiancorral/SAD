import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.concurrent.ConcurrentHashMap;

public class MyServerSocket extends ServerSocket {

    private ConcurrentHashMap<String, MySocket> clients = new ConcurrentHashMap<>();

    public MyServerSocket(int port) throws IOException {
        super(port);
    }

    public MySocket accept() {
        try {
            if (super.isClosed())
                throw new SocketException("Socket is closed");
            if (!super.isBound())
                throw new SocketException("Socket is not bound yet");
            MySocket s = new MySocket();
            super.implAccept(s);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void println(String line) {
        for (String key : clients.keySet()) {
            clients.get(key).getWriter().println(line);
        }
        System.out.println(line);
    }

    public void newUser(MySocket cs) {
        for (String key : clients.keySet()) {
            clients.get(key).getWriter().println("[User: " + cs.getUser() + "] : IS ONLINE");
            cs.getWriter().println("[User: " + clients.get(key).getUser() + "] : IS ONLINE");
        }
        clients.put(cs.getUser(), cs);
        System.out.println("[User: " + cs.getUser() + "] : IS ONLINE");
    }

    public void oldUser(MySocket cs) {
        for (String key : clients.keySet()) {
            clients.get(key).getWriter().println("[User: " + cs.getUser() + "] : IS OFLINE");
        }
        clients.remove(cs.getUser());
        System.out.println("[User: " + cs.getUser() + "] : IS OFLINE");
    }

}
