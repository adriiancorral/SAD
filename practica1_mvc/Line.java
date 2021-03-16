import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Line extends Observable {

    public static final char ESC = (char)27;
    public static final String ESC_LEFT = ESC + "[D";
    public static final String ESC_RIGHT = ESC + "[C";
    public static final String ESC_UP = ESC + "[A";
    public static final String ESC_DOWN = ESC + "[B";
    public static final String ESC_DEL = ESC + "[3~";
    public static final String ESC_INSERT = ESC + "[2~";

    private List<Character> buff;
    private int actualColum, actualRow;
    private int maxCols;
    private boolean insert;
    private final Console console;

    public Line() {
        actualColum = 0;
        actualRow = 0;
        buff = new ArrayList<>();
        insert = false;
        console = new Console();
        addObserver(console);
    }

    public int getActualColum() {
        return actualColum;
    }

    public List<Character> getBuff() {
        return buff;
    }

    public void specialAction(int character) {
        switch (character) {
            case EditableBufferedReader.UP:
                up();
                break;
            case EditableBufferedReader.DOWN:
                down();
                break;
            case EditableBufferedReader.RIGHT:
                right();
                break;
            case EditableBufferedReader.LEFT:
                left();
                break;
            case EditableBufferedReader.HOME:
                home();
                break;
            case EditableBufferedReader.INSERT:
                insert();
                break;
            case EditableBufferedReader.DEL:
                delete();
                break;
            case EditableBufferedReader.END:
                end();
                break;
            case EditableBufferedReader.BACKSPACE:
                backspace();
                break;
        }
    }

    public void addChar(char c) {
        if (insert && actualColum != buff.size()) {   // Insert ON
            buff.set(actualColum, c);
        } else {        // Insert OFF
            buff.add(actualColum, c);
        }
        System.out.print(c);
        notifyObservers();
        actualColum++;
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
    }

    public void down() {
        // Por implementar
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
        if (actualColum > 0) {
            actualColum--;
            buff.remove(actualColum);
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
