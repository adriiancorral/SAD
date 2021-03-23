package mvc_plus;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class Line {

    private List<Character> buff;
    private int actualColum;
    private boolean insert;
    private PropertyChangeSupport pcs;

    public Line() {
        actualColum = 0;
        buff = new ArrayList<>();
        insert = false;
        pcs = new PropertyChangeSupport(this);
        pcs.addPropertyChangeListener(new Console());
    }

    public int getActualColum() {
        return actualColum;
    }

    public void setActualColum(int actCol) {
        actualColum = actCol;
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
        pcs.firePropertyChange("char", Constants.SPECIAL, actualColum - 1);
    }

    public void left() {
        if (actualColum > 0) {
            actualColum--;
            pcs.firePropertyChange("left", Constants.SPECIAL, Constants.LEFT);
        }
    }

    public void right() {
        if (actualColum < buff.size()) {
            actualColum++;
            pcs.firePropertyChange("right", Constants.SPECIAL, Constants.RIGHT);
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
            pcs.firePropertyChange("delete", Constants.SPECIAL, Constants.DEL);
        }
    }

    public void backspace() {
        if (actualColum > 0) {
            actualColum--;
            buff.remove(actualColum);
            pcs.firePropertyChange("backspace", Constants.SPECIAL, Constants.BACKSPACE);
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
