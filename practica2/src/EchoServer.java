package src;

import java.io.IOException;

public class EchoServer {
    public static void main(String[] args) throws NumberFormatException, IOException {
        MyServerSocket ss = new MyServerSocket(Integer.parseInt(args[0]));
        while (true) {
            MySocket cs = ss.accept();
            if (cs == null) {
                break;
            }
            new Thread() {
                public void run() {
                    try {
                        ss.newUser(cs);

                        String line;
                        while ((line = cs.readLine()) != null) {
                            ss.println(line);
                        }

                        ss.oldUser(cs);

                        cs.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}