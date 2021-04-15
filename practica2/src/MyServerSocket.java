package src;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;

public class MyServerSocket extends ServerSocket {

    private ConcurrentHashMap<String, MySocket> clients;

    public MyServerSocket(int port) throws IOException {
        super(port);
        clients = new ConcurrentHashMap<String, MySocket>();
    }

    public MySocket accept() {
        try {
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
