package calc;

import java.io.*;

class Calculator {
  public static void main(String[] args) {
    BufferedReader in = new EditableBufferedReader(new InputStreamReader(System.in));
    String str = null;
    try {
      // define widgets

      Form f = new Form().add(new Widget[] { 
        new Field("first", "first operand: "), 
        new Field("second", "second operand: "),
        new Field("operator", "operator: "), 
        new Label("result", "result: ") });

      // event loop
      while (f.read()) { // true: form ready, false: EOF
        // Read first operand

        // Read operator

        // Read second operand

        // Evaluate result
        result = calcula(operator, first, second);

        // Write result

      }

      // close form
      f.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("\nline is: " + str);
  }
}
