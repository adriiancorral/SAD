import java.util.Observable;
import java.util.Observer;

public class Console implements Observer{
    
    private int actualColum, totalColum;
    private int actualRow;
    private boolean insert;

    public Console() {
        actualColum = 0;
        actualRow = 0;
        insert = false;
    }

    public void left() {
        if (actualColum > 0) {
            System.out.print(Line.ESC_LEFT);
            actualColum--;
        }
    }

    public void right() {
        if (actualColum < totalColum) {
            System.out.print(Line.ESC_RIGHT);
            actualColum++;
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
        while(actualColum < totalColum){
            right();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        // TODO Auto-generated method stub
        
    }

}
