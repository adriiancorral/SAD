import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class EchoClient {
    public static void main(String[] args) throws NumberFormatException, IOException{
        /*
         * args[0] : host
         * args[1] : port
         * args[2] : user
         */
        MySocket cs = new MySocket(args[0], Integer.parseInt(args[1]), args[2]);
        
        // Input thread from the keyboard
        new Thread() {
            public void run() {
                try {
                    String line;
                    BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
                    while ((line = buff.readLine()) != null) {
                        cs.println(line);
                    }
                    cs.shutdownOutput();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        
        // Input thread from the server
        new Thread() {
            public void run() {
                String line;
                while ((line = cs.readLine()) != null) {
                    System.out.println(line);
                }
            }
        }.start();
    }
}
