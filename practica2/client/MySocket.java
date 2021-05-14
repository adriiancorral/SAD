import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MySocket extends Socket{

    private InputStream input;
    private BufferedReader reader;
    private OutputStream output;
    private PrintWriter writer;
    private String username;

    public MySocket(String hostname, int port, String username) throws UnknownHostException, IOException{
        super(hostname, port);
        this.username = username;

        // Clases encargadas de leer lo que el servidor nos envia
        input = this.getInputStream();
        reader = new BufferedReader(new InputStreamReader(input));

        // Clases encargadas de escribir al servidor
        output = this.getOutputStream();
        writer = new PrintWriter(output, true);

        // Cuando nos conectamos le enviamos nuestro nombre al servidor
        sendName();
    }

    // Método encargado de leer lo que nos llega del servidor
    public String readInput() throws IOException{
        String serverResponse = reader.readLine();
        return serverResponse;
    }

    // Método encargado de escribirle al servidor
    public void writeOutput(String output) {
        writer.println(output);
    }

    // Método encargado de enviarle el nombre del usuario al servidor
    public void sendName() {
        writeOutput(this.username);
    }
}
