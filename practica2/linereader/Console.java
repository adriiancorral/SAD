package practica2.linereader;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class Console implements PropertyChangeListener {

    private int maxCols;

    public Console() {
        maxCols = 0;
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        Object line = e.getSource();
        Object action = e.getNewValue();

        int moves = 0;
        int col = ((Line) line).getActualColum();
        int buffSize = ((Line) line).getBuff().size();

        switch ((int)action) {
            case Constants.UP:
                // En desarrollo
                System.out.print(Constants.ESC_UP);
                ((Line) line).setActualColum(col - maxCols);
                break;
            
            case Constants.DOWN:
                // En desarrollo
                System.out.print(Constants.ESC_DOWN);
                ((Line) line).setActualColum(col + maxCols);
                break;
            
            case Constants.RIGHT:
                System.out.print(Constants.ESC_RIGHT);
                break;
            
            case Constants.LEFT:
                System.out.print(Constants.ESC_LEFT);
                break;
            
            case Constants.DEL:
                // Update terminal (characters that are at the right)
                for (int i = 0; i + col < buffSize; i++) {
                    System.out.print(((Line) line).getBuff().get(i + col));
                    moves++;
                }
                // Put one space at the last character
                System.out.print(" ");
                moves++;
                // Put the cursor to the original position
                for (int i = 0; i < moves; i++) {
                    System.out.print(Constants.ESC_LEFT);
                }
                break;
            
            case Constants.BACKSPACE:
                // First go one position left
                System.out.print(Constants.ESC_LEFT);
                // Update terminal (characters that are at the right)
                for (int i = 0; i + col < buffSize; i++) {
                    char character = ((Line) line).getBuff().get(i + col);
                    System.out.print(character);
                    moves++;
                }
                // Put one space at the last character
                System.out.print(" ");
                moves++;
                // Put the cursor to the original position
                for (int i = 0; i < moves; i++) {
                    System.out.print(Constants.ESC_LEFT);
                }
                break;
            
            default:    // Default is addChar()
                // Write the character on the cursor position
                System.out.print(((Line) line).getBuff().get((int) action));
                // Update terminal (characters that are at the right)
                for (int i = 0; i + col < buffSize; i++) {
                    char character = ((Line) line).getBuff().get(i + col);
                    System.out.print(character);
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
