import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

import java.io.InputStreamReader;

public class EchoServer {
    public static void main(String[] args) throws NumberFormatException, IOException {
        MyServerSocket ss = new MyServerSocket(Integer.parseInt(args[0]));
        while (true) {
            Socket cs = ss.accept();
            BufferedReader buff = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            new Thread() {
                public void run() {
                    try {
                        String name = buff.readLine();
                        ss.newUser(name, cs);

                        String line;
                        while ((line = buff.readLine()) != null) {
                            ss.println(line);
                        }

                        ss.oldUser(name);

                        cs.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}