package src2;

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

    public void addChar(char c) {
        if (insert && actualColum != buff.size()) {   // Insert ON
            buff.set(actualColum, c);
        } else {        // Insert OFF
            buff.add(actualColum, c);
        }
        actualColum++;
        setChanged();
        notifyObservers(actualColum - 1);
    }

    public void left() {
        if (actualColum > 0) {
            actualColum--;
            setChanged();
            notifyObservers(Constants.LEFT);
        }
    }

    public void right() {
        if (actualColum < buff.size()) {
            actualColum++;
            setChanged();
            notifyObservers(Constants.RIGHT);
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
            setChanged();
            notifyObservers(Constants.DEL);
        }
    }

    public void backspace() {
        if (actualColum > 0) {
            actualColum--;
            buff.remove(actualColum);
            setChanged();
            notifyObservers(Constants.BACKSPACE);
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
