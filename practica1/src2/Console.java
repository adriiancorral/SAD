package src2;

import java.util.Observable;
import java.util.Observer;

public class Console implements Observer {

    private int maxCols;

    public Console() {
        maxCols = 0;
    }

    @Override
    public void update(Observable o, Object arg) {
        int moves = 0;
        switch ((int)arg) {
            case Constants.UP:
                // En desarrollo
                System.out.print(Constants.ESC_UP);
                ((Line) o).setActualColum(((Line) o).getActualColum() - maxCols);
                break;
            
            case Constants.DOWN:
                // En desarrollo
                System.out.print(Constants.ESC_DOWN);
                ((Line) o).setActualColum(((Line) o).getActualColum() + maxCols);
                break;
            
            case Constants.RIGHT:
                System.out.print(Constants.ESC_RIGHT);
                break;
            
            case Constants.LEFT:
                System.out.print(Constants.ESC_LEFT);
                break;
            
            case Constants.DEL:
                // Update terminal
                for (int i = 0; i + ((Line) o).getActualColum() < ((Line) o).getBuff().size(); i++) {
                    System.out.print(((Line) o).getBuff().get(i + ((Line) o).getActualColum()));
                    moves++;
                }
                System.out.print(" ");
                moves++;
                // Put the cursor to the original position
                for (int i = 0; i < moves; i++) {
                    System.out.print(Constants.ESC_LEFT);
                }
                break;
            
            case Constants.BACKSPACE:
                System.out.print(Constants.ESC_LEFT);
                // Update terminal
                for (int i = 0; i + ((Line) o).getActualColum() < ((Line) o).getBuff().size(); i++) {
                    System.out.print(((Line) o).getBuff().get(i + ((Line) o).getActualColum()));
                    moves++;
                }
                System.out.print(" ");
                moves++;
                // Put the cursor to the original position
                for (int i = 0; i < moves; i++) {
                    System.out.print(Constants.ESC_LEFT);
                }
                break;
            
            default:    // Default is addChar()
                System.out.print(((Line) o).getBuff().get((int) arg));
                // Update terminal
                for (int i = 0; i + ((Line) o).getActualColum() < ((Line) o).getBuff().size(); i++) {
                    System.out.print(((Line) o).getBuff().get(i + ((Line) o).getActualColum()));
                    moves++;
                }
                // Put the cursor to the original position
                for (int i = 0; i < moves; i++) {
                    System.out.print(Constants.ESC_LEFT);
                }
                break;
        }
    }

}
