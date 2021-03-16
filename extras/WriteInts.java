import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * read ints from stdin and write to stdout in binary form
 */

public class WriteInts {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        DataOutputStream out = new DataOutputStream(System.out);
        int i = 0;
        while (sc.hasNextInt()) {
            i = sc.nextInt();
            out.writeInt(i);
        }
        out.flush();
    }
}
