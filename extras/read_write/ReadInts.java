import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
//import org.apache.poi.util.*;

/**
 * read bynary ints from System.in and 
 * write them in textual form to System.out
 */

public class ReadInts {

    static class MyDataInputStream extends DataInputStream {
        public MyDataInputStream(InputStream in) {
            super(in);
        }
        short readShortLittle() throws IOException {
            short r;
            r = (short) in.read();
            if (r == -1) throw new EOFException();
            r |= in.read()<<8;
            return r;
        }
        int readIntLittle() throws IOException {
            int r;
            r = readShortLittle();
            r |= readShortLittle()<<16;
            return r;
            /*int r = 0;
            try {
                r = LittleEndian.readInt(in);
            } catch (LittleEndian.BuferUnderrunException e) {
                throw new EOFException();
            }*/
        }
        long readLongLittle() throws IOException {
            long r;
            r = readIntLittle();
            r |= readIntLittle()<<32;
            return r;
        }
    }

    public static void main(String[] args) throws IOException {
        int i;
        DataInputStream in = new MyDataInputStream(System.in);
        while (true) {
            try {
                i = in.readIntLittle();
            } catch (EOFException e) {
                break;
            }
            System.out.println(i);
        }
    }
}
