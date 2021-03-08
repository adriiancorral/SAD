import java.util.ArrayList;
import java.util.List;

public class Line {

    public static final char ESC = (char)27;
    public static final String ESC_LEFT = ESC + "[D";
    public static final String ESC_RIGHT = ESC + "[C";
    public static final String ESC_UP = ESC + "[A";
    public static final String ESC_DOWN = ESC + "[B";
    public static final String ESC_DEL = ESC + "[3~";
    public static final String ESC_BACKSPACE = ESC + "[8~";
    public static final String ESC_INSERT = ESC + "[2~";
    public static final String ESC_HOME = ESC + "[H";
    public static final String ESC_END = ESC + "[F";

    private List<Character> buff;
    private int actualColum, actualRow;
    private int maxCols;
    private boolean insert;

    public Line() {
        actualColum = 0;
        actualRow = 0;
        buff = new ArrayList<>();
        insert = false;
    }

    public void addChar(char c) {
        if (actualColum == buff.size()) {   // We are at the end of buffer
            buff.add(c);
            System.out.print(c);
            actualColum++;
        } else {    // We are at the middle of buffer
            if (insert) {   // Insert ON
                buff.set(actualColum, c);
            } else {        // Insert OFF
                buff.add(actualColum, c);
            }
            System.out.print(c);
            actualColum++;
            // Update terminal
            int moves = 0;
            for (int i = 0; i + actualColum < buff.size(); i++) {
                System.out.print(buff.get(i + actualColum));
                moves++;
            }
            // Put the cursor to the original position
            for (int i = 0; i < moves; i++) {
                System.out.print(ESC_LEFT);
            }
        }
    }

    public void left() {
        if (actualColum > 0) {
            System.out.print(ESC_LEFT);
            actualColum--;
        }
    }

    public void right() {
        if (actualColum < buff.size()) {
            System.out.print(ESC_RIGHT);
            actualColum++;
        }
    }

    public void up() {
        // Por implementar
        System.out.print((char)27 + "[A");
    }

    public void down() {
        // Por implementar
        System.out.print((char)27 + "[B");
    }

    public void delete() {
        if (actualColum < buff.size()) {
            buff.remove(actualColum);
            // Update terminal
            int moves = 0;
            for (int i = 0; i + actualColum < buff.size(); i++) {
                System.out.print(buff.get(i + actualColum));
                moves++;
            }
            System.out.print(" ");
            moves++;
            // Put the cursor to the original position
            for (int i = 0; i < moves; i++) {
                System.out.print(ESC_LEFT);
            }
        }
    }

    public void backspace() {
        // En desaroyo
        if (actualColum > 0) {
            actualColum--;
            buff.remove(actualColum);
        }
        // Update terminal
        System.out.print(ESC_LEFT);
        int moves = 0;
        for (int i = 0; i + actualColum < buff.size(); i++) {
            System.out.print(buff.get(i + actualColum));
            moves++;
        }
        System.out.print(" ");
        moves++;
        // Put the cursor to the original position
        for (int i = 0; i < moves; i++) {
            System.out.print(ESC_LEFT);
        }

    }

    public void insert() {
        insert ^= true;
    }

    public void home() {
        while(actualColum > 0){
            left();
        }
    }

    public void end() {
        while(actualColum < buff.size()){
            right();
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder(buff.size());
        for(Character ch: buff) {
            sb.append(ch);
        }
        return sb.toString();
    }
    
}
