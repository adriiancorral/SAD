import java.io.*;

class TerminalColum {

    public static int getCols() {
        String cols = "";
        try {
            Process colsProcess = new ProcessBuilder("bash", "-c", "tput cols 2> /dev/tty").start();
            BufferedReader colsReader = new BufferedReader(
                new InputStreamReader(colsProcess.getInputStream()));
            cols = colsReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.parseInt(cols);
    }
    public static void main(String[] args) {
        System.out.println(getCols());
    }
}
