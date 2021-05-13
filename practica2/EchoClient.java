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
        
        // Input thread (from keyboard to socket)
        new Thread() {
            public void run() {
                try {
                    cs.getWriter().println(cs.getUser());
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
        
        // Output thread (from server to screen)
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