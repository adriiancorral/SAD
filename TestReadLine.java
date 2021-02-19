import java.io.*;

class TestReadLine {
  public static void main(String[] args) {
    BufferedReader in = new EditableBufferedReader(
      new InputStreamReader(System.in));
    String str = null;
    try {
      //str = in.readLine();
      ((EditableBufferedReader) in).setRaw();
    //} catch (IOException e) { e.printStackTrace(); }
    } catch (Exception e) { e.printStackTrace(); }
    //System.out.println("\nline is: " + str);
    System.out.println("\n This is a test");
  }
}
