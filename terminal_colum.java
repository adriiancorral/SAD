import java.io.*;

class terminal_colum {
    public static void main(String[] args) {
        String cols = "";
        try {
            Process colsProcess = new ProcessBuilder("bash", "-c", "tput cols 2> /dev/tty").start();
            BufferedReader colsReader = new BufferedReader(
                new InputStreamReader(colsProcess.getInputStream()));
            cols = colsReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("El numero de columnas de esta terminal es: " + cols);
    }
}
