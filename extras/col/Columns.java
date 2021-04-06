import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Columns
 */
public class Columns {

    public static void main(String[] args) {
        int c = 0;
        try {
            Process p = null;
            try {
                p = Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c", "tput cols"});
                p.waitFor();
                c = Integer.parseInt(new BufferedReader(new InputStreamReader(p.getInputStream())).readLine());
            } catch (Exception e) {
                //TODO: handle exception
            }
        } finally {
            System.out.println("COLUMS = " + c);
        }
    }
}