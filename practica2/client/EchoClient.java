import java.net.*;
import java.io.*;
 
/**
 * This program demonstrates a simple TCP/IP socket client.
 *
 * @author www.codejava.net
 */
public class EchoClient {
 
    public static void main(String[] args) {
        if (args.length < 2) return;
 
        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        String username = args[2];
 
        try (MySocket socket = new MySocket(hostname, port, username)) {
 
            // Creamos un hilo que se dedica a leer lo que llega del servidor
            Thread writeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // Leemos lo que se escribe en la consola para mandarlo
                    BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
                    String consoleText;
                    try {
                        while((consoleText = consoleReader.readLine()) != null) {
                            // Mandamos lo escrito en la consola
                            socket.writeOutput(consoleText);
                        };
                    } catch(IOException ie) {
                        ie.printStackTrace();
                    }   
                }
            });
            writeThread.start();
            
            // Si tenemos texto del cliente lo imprimimos en la consola
            String clientText;
            while((clientText = socket.readInput()) != null) {
                System.out.println(clientText);
            }
 
        } catch (UnknownHostException ex) {
 
            System.out.println("Server not found: " + ex.getMessage());
 
        } catch (IOException ex) {
 
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
