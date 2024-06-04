import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EnemySquare extends Square {      
    private View view;

    public EnemySquare(int row, int col, View view){
        super(row, col);
        this.view = view;         
        addMouseListener(new GridHover());
    }

    public void placeShip(Ship s){
        occupied = false;
        occupyingShip = s;
    }

    private class GridHover implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            if(Controller.gameInProgress) {
                view.postMessage("");
                view.attack(true, getRow(), getCol());
            }
            
        }

        @Override
        public void mousePressed(MouseEvent e) {
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if(Controller.gameInProgress) markAsSelected();            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(Controller.gameInProgress) markAsUnselected();
        }

    }
}




