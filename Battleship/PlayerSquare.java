import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

public class PlayerSquare extends Square {      
    private View view;

    public PlayerSquare(int row, int col, View view){
        super(row, col);
        this.view = view;         
        addMouseListener(new GridHover());
    }

    
    private class GridHover implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            if(!Controller.gameInProgress){
                if(SwingUtilities.isLeftMouseButton(e)) view.setShip(getRow(), getCol());
                if(SwingUtilities.isRightMouseButton(e)) {                    
                    view.toggleHorizontal(row, col);
                }
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
            if(!Controller.gameInProgress) view.previewShip(getRow(), getCol());            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(!Controller.gameInProgress) view.revertPreview(getRow(), getCol());
        }

    }
}



