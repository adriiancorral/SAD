import java.io.IOException;

/**
 * EchoServer
 */

public class EchoServer {
    public static void main(String[] args) throws NumberFormatException, IOException {
        MyServerSocket ss = new MyServerSocket(Integer.parseInt(args[0]));
        while (true) {
            MySocket cs = ss.accept();
            new Thread() {
                public void run() {
                    String line;
                    while ((line = cs.readLine()) != null) {
                        cs.println(line);
                    }
                }
            }.start();
        }
    }
}