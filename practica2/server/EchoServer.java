import java.io.*;
import java.net.*;
 
public class EchoServer {
 
    public static void main(String[] args) {
        if (args.length < 1) return;
 
        int port = Integer.parseInt(args[0]);
 
        try (MyServerSocket serverSocket = new MyServerSocket(port)) {
 
            System.out.println("Server is listening on port " + port);

            // Creamos un hilo que le envia mensasajes del cliente al servidor
            Thread writeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // Leemos lo que se escribe en la consola para mandarlo
                    BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
                    String consoleText;
                    try {
                        while((consoleText = consoleReader.readLine()) != null) {
                            // Mandamos lo escrito en la consola
                            serverSocket.writeAllClients("ADMIN: " + consoleText);
                        };
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
                    System.out.println("New client connected: " + clientName);
                    // Le decimos a todos los clientes que ha entrado un usuario nuevo
                    serverSocket.writeAllClientsLessOne(clientName, "New client connected: " + clientName); 

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
                                    System.out.println(clientName + ": " + clientText);
                                    // Tambien se lo enviamos a los demás clientes
                                    serverSocket.writeAllClientsLessOne(clientName, clientName + ": " + clientText);
                                };
                                serverSocket.delUser(clientName);
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
