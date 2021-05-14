import java.io.IOException;
import java.net.ServerSocket;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class MyServerSocket extends ServerSocket {

    private Map<String, Socket> usersList;

    public MyServerSocket(int port) throws IOException {
        super(port);

        // Mapa donde guardamos los usernames con sus sockets
        usersList = new HashMap<String, Socket>();
    }

    // Método para leer a un cliente especifico
    public String readClient(String username) throws IOException {
        // Con el username obtenemos el socket de la lista de usersList
        Socket userSocket = usersList.get(username);

        // Con este Socket creamos un reader para leerle
        InputStream input = userSocket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String clientText = reader.readLine();

        return clientText;
    }

    // Método para escribir a un cliente en especifico
    public void writeClient(String username, String text) throws IOException {
        // Con el username obtenemos el socket de la lista de usersList
        Socket userSocket = usersList.get(username);

        // Con este Socker creamos un writer para escribirle
        OutputStream output = userSocket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);

        // Le escribimos el texto utilizando el writer
        writer.println(text);
    }

    // Esta función se utiliza para saber el nombre del cliente, el nombre es el primer mensaje
    // que nos manda el cliente
    public String readName(Socket socket) throws IOException {
        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String clientName = reader.readLine();

        return clientName;
    }

    // Método para escribir a todos los clientes
    public void writeAllClients(String text) throws IOException {
        // Recorremos la usersList para obtener todos los sockets
        for (Map.Entry<String, Socket> entry : usersList.entrySet()) {
            writeClient(entry.getKey(), text);
        }
    }

    // Método para escribir a todos los clientes menos al indicado
    public void writeAllClientsLessOne(String clientName, String text) throws IOException {
        // Recorremos la usersList para obtener todos los sockets
        for (Map.Entry<String, Socket> entry : usersList.entrySet()) {
            if(clientName != entry.getKey()){
                writeClient(entry.getKey(), text);
            }
        }
    }

    // Método para leer a todos los clientes
    public String readAllClients() throws IOException {
        for (Map.Entry<String, Socket> entry : usersList.entrySet()) {
            String clientText;
            if((clientText = readClient(entry.getKey())) != null) {
                return clientText;
            }
        }
        return null;
    }

    public boolean addUser(String username, Socket userSocket) {
        // Recorremos la lista de usuarios, si el nombre del nuevo ya esta dentro del chat no dejamos
        // entrar a este nuevo usuario
        for (Map.Entry<String, Socket> entry : usersList.entrySet()) {
            if(username.equals(entry.getKey())) {
                return false;
            }
        }
        usersList.put(username, userSocket);
        return true;
    }
    
}
