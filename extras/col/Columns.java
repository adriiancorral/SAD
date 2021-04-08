import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Columns
 */
public class Columns {

    public static void main(String[] args) throws IOException {
        int c = 0;
        try {
            Kbd.setRaw();
            System.out.print("\033[18t");

            // 1º alternativa
            /* Process p = null;
            try {
                p = Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c", "tput cols"});
                p.waitFor();
                c = Integer.parseInt(new BufferedReader(new InputStreamReader(p.getInputStream())).readLine());
            } catch (Exception e) {
                //TODO: handle exception
            } */

            // 2º alternativa
            // Retorna: ESC [ 8 ; filas ; columnas t
            /* Scanner s1 = new Scanner(System.in);
            s1.skip("\033\\[8;\\d+;(\\d+)t");
            c = Integer.parseInt(s1.match().group(1)); */

            // 3º alternativa (nextInt)
            /* Scanner s2 = new Scanner(System.in);
            s2.skip("\033\\[8;").useDelimiter(";");
            s2.nextInt();   // skip rows
            s2.skip(";").useDelimiter("t");
            c = s2.nextInt();
            s2.skip("t"); */

            // 4º alternativa
            /* Pattern p = Pattern.compile("\033\\[8;\\d+;(\\d+)t");
            Matcher m = p.matcher(readChars(new BufferedReader(new InputStreamReader(System.in))));
            m.matches();
            c = Integer.parseInt(m.group(1)); */

            // 5º alternativa
            // Retorna: ESC [ 8 ; filas ; columnas t
            String str = readChars(new BufferedReader(new InputStreamReader(System.in)));
            String cmax = str.substring(
                str.indexOf(";", str.indexOf(";") + 1) + 1, str.length() - 1)
            ;
            c = Integer.parseInt(cmax);

            // 6º alternativa (una sola linea)
            // Al ejecutar el programa le pasas la variable COLUMNS
            /* c = Integer.parseInt(System.getenv("COLUMNS")); */
        } finally {
            Kbd.unsetRaw();
        }
        System.out.println("COLUMS = " + c);
    }

    private static String readChars(Reader r) throws IOException {
        StringBuilder str = new StringBuilder();
        do{
            str.append((char)r.read());
        } while (r.ready());
        return str.toString();
    }

}