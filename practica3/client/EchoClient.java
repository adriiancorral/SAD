import java.net.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.DefaultListModel;
import javax.swing.JTextArea;
import java.io.*;

public class EchoClient {

    public static ConcurrentHashMap<String, String> writen = new ConcurrentHashMap<>();

    private JTextArea messages;
    private DefaultListModel<String> listModel;

    public EchoClient(JTextArea msg, DefaultListModel<String> lm) {
        this.messages = msg;
        this.listModel = lm;
    }
 
    public void mainClient(String hostname, int port, String username) {
 
        try (MySocket socket = new MySocket(hostname, port, username)) {
 
            // Creamos un hilo que se dedica a leer lo que llega del servidor
            Thread writeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // Leemos lo que se escribe en la consola para mandarlo
                    String consoleText;
                    try {
                        while(true) {
                            if (!writen.isEmpty()) {
                                consoleText = writen.get("key");
                                writen.remove("key");
                                // Mandamos lo escrito en la consola
                                socket.writeOutput(consoleText);
                            }
                        }
                    } catch(Exception ie) {
                        ie.printStackTrace();
                    }   
                }
            });
            writeThread.start();

            // Recibimos los usuarios
            String users = socket.readInput();
            String[] usersSplit = users.split(" ");
            for (int i=0; i < usersSplit.length-1; i++) {
                listModel.addElement(usersSplit[i]);
            }
            
            // Si tenemos texto del cliente lo imprimimos en la consola
            String clientText;
            while((clientText = socket.readInput()) != null) {
                String[] str = clientText.split(" ");
                if(str[0].equals("Client_disconnected:")) {
                    listModel.removeElement(str[1]);
                } else if(str[0].equals("Client_connected:")) {
                    listModel.addElement(str[1]);
                } else {
                    messages.append(clientText + "\n");
                }
            }
 
        } catch (UnknownHostException ex) {
 
            System.out.println("Server not found: " + ex.getMessage());
 
        } catch (IOException ex) {
 
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
