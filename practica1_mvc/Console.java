import java.util.Observable;
import java.util.Observer;

public class Console implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        // Update terminal
        int moves = 0;
        for (int i = 0; i + o.getActualColum() < o.getBuff().size(); i++) {
            System.out.print(o.getBuff().get(i + o.getActualColum()));
            moves++;
        }
        // Put the cursor to the original position
        for (int i = 0; i < moves; i++) {
            System.out.print(Line.ESC_LEFT);
        }
    }

}
