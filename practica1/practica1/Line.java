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
        System.out.print((char)27 + "[A");
    }

    public void down() {
        System.out.print((char)27 + "[B");
    }

    public void delete() {
        System.out.print((char)27 + "[3~");
    }

    public void backspace() {
        System.out.print((char)27 + "[8~");
    }

    public void insert() {
        System.out.print((char)27 + "[2~");
    }

    public void home() {
        System.out.print((char)27 + "[H");
    }

    public void end() {
        System.out.print((char)27 + "[F");
    }
    
}
