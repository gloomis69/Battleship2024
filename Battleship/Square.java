import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Square extends JLabel {    
    public static int SIZE = View.GRID_SIZE/10; 
    
    protected int col;
    protected int row;
    
    private int xPos;
    private int yPos;

    protected boolean occupied = false;
    protected Ship occupyingShip = null;
    public static final Color EMPTY_COLOR = new Color(0, 204, 255);
    public static final Color SHIP_COLOR = new Color(0, 0, 0, 0);
    public static final Color HIT_COLOR = new Color(255, 0, 0);
    public static final Color MISS_COLOR = new Color(150, 150, 150);
    public static final Color SUNK_COLOR = new Color(10, 100, 35);
    public static final Color SELECTION_COLOR = Color.CYAN;
    public static final Color ERROR_COLOR = new Color(180, 0, 0);

    public Square(int row, int col){ 
        super("", JLabel.CENTER);
        this.col = col;
        this.row = row;
        xPos = col*Square.SIZE;
        yPos = row*Square.SIZE;
        
        setPreferredSize(new Dimension(SIZE, SIZE));
        setBounds(col*SIZE, row*SIZE, SIZE-1, SIZE-1);
        setOpaque(true);
        setBackground(EMPTY_COLOR);
        setForeground(Color.RED);
        setFont(new Font("Tahoma", Font.BOLD, 36));
        setHorizontalTextPosition(JLabel.CENTER);
        setVerticalTextPosition(JLabel.CENTER);
        //setText("X");
    }

    public boolean wasHit(){
        return this.getBackground().equals(HIT_COLOR);
    }

    public boolean isOccupied(){
        return occupied;
    }
    public int[] getXandY(){
        int[] rowAndCol = {xPos, yPos};
        return rowAndCol;
    }

    public int getXpos(){
        return xPos;
    }

    public int getYpos(){
        return yPos;
    }

    

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public void setShip(Ship s){
        occupyingShip = s;
        occupied = true;
        setBackground(SHIP_COLOR);
    }

    public void reset(){
        occupied = false;
        occupyingShip = null;
        setText("");
        setBackground(EMPTY_COLOR);
    }

    public Ship markAsHit(){        
        occupied = true;
        //setBackground(HIT_COLOR);
        //setIcon(occupyingShip.getIcon(row, col));
        setText("X");
        occupyingShip.addHit(new Coordinate(row, col));
        return occupyingShip;
    }

    public void markAsSunk(){
        //setText("");
        //setBackground(SUNK_COLOR);
    }
    public void markAsMiss(){
        occupied = true;
        setText("");
        setBackground(MISS_COLOR);
    }

    public void markAsSelected(){
        if(!occupied){ 
            setText("");           
            setBackground(SELECTION_COLOR);
        }
    }

    public void markAsError(){
        if(!occupied){    
            setText("!");        
            setBackground(ERROR_COLOR);
        }
    }

    public void markAsUnselected(){
        if(!occupied){
            setText("");
            setBackground(EMPTY_COLOR);
        }
    }
    
}


