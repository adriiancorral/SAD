package practica1;

public class Line {

    private char[] buff;
    private int buffLength;
    private int actualColum, actualRow;
    private int maxCols;

    public Line() {
        actualColum = 0;
        actualRow = 0;
        buffLength = 0;
    }

    public int getActualColum() {
        return actualColum;
    }

    public void addChar(char c) {
        for (int i=actualColum; i <= buffLength; i++) {
            char aux = buff[actualColum];
            buff[actualColum] = c;
            buff[actualColum + 1] = aux;
        }
        actualColum++;
        buffLength++;
    }

    public void left() {
        System.out.print((char)27 + "[D");
        actualColum--;
        if (actualColum < 0) {
            actualColum = maxCols - 1;
            up();
            end();
        }
    }

    public void right() {
        System.out.print((char)27 + "[C");
        actualColum++;
        if (actualColum >= maxCols) {
            actualColum = 0;
            down();
            home();
        }
    }

    public void up() {
        if (buffLength >= maxCols) {

        }
    }

    public void down() {
        // Falta rellenar
    }

    public void delete() {
        // Falta rellenar
    }

    public void backspace() {
        // Falta rellenar
    }

    public void insert() {
        // Falta rellenar
    }

    public void home() {
        // Falta rellenar
    }

    public void end() {
        // Falta rellenar
    }
    
}
