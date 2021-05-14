import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.DefaultListModel;
import javax.swing.JTextArea;
 
public class EchoServer {

    public static ConcurrentHashMap<String, String> writen = new ConcurrentHashMap<>();

    private JTextArea messages;
    private DefaultListModel<String> listModel;

    public EchoServer(JTextArea msg, DefaultListModel<String> lm) {
        this.messages = msg;
        this.listModel = lm;
    }
 
    public void mainServer(int port) {
 
        try (MyServerSocket serverSocket = new MyServerSocket(port)) {
 
            messages.append("Server is listening on port " + port + "\n");

            // Creamos un hilo que le envia mensasajes del cliente al servidor
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
                                serverSocket.writeAllClients("ADMIN: " + consoleText);
                            }
                        }
                    } catch(IOException ie) {
                        ie.printStackTrace();
                    }   
                }
            });
            writeThread.start();

            /* 
                Creamos un bucle que al principio espera a que se conecte un nuevo cliente y cuando
                se conecta creamos un thread con la información del socket
            */
            
            while(true) {
                // Aceptamos las conexiones que nos lleguen
                Socket socket = serverSocket.accept();

                // Leemos el nombre
                String clientName = serverSocket.readName(socket);

                // Si el nombre no esta ya en uso metemos el username y socket en la lista
                if(serverSocket.addUser(clientName, socket)) {

                    // Imprimimos el nombre en consola
                    listModel.addElement(clientName);
                    serverSocket.notifyUsers(clientName);

                    // Le decimos a todos los clientes que ha entrado un usuario nuevo
                    serverSocket.writeAllClientsLessOne(clientName, "Client_connected: " + clientName); 

                    // Cuando tenemos el nombre y el socket los guardamos en un diccionario
                    serverSocket.addUser(clientName, socket);

                    // Creamos un hilo para leer los mensajes que nos llegan del cliente
                    Thread readThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String clientText;
                            try {
                                while((clientText = serverSocket.readClient(clientName)) != null) {
                                    // Escribimos el texto del cliente en la consola
                                    messages.append(clientName + ": " + clientText + "\n");
                                    // Tambien se lo enviamos a los demás clientes
                                    serverSocket.writeAllClientsLessOne(clientName, clientName + ": " + clientText);
                                };
                                serverSocket.delUser(clientName);
                                listModel.removeElement(clientName);
                            } catch(IOException ie) {
                                ie.printStackTrace();
                            }
                        }
                    });
                    readThread.start();

                } else { // Si el nombre ya estaba en uso se lo decimos al usuario y cerramos el socket
                    
                    // Con este socket que vamos a cerrar creamos un writer para escribirle
                    OutputStream output = socket.getOutputStream();
                    PrintWriter writer = new PrintWriter(output, true);

                    // Le escribimos el texto utilizando el writer
                    writer.println("ERROR: Your username is in use, please change it");

                    // Cerramos el socket
                    socket.close();
                }

            }
 
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
