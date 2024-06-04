import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class View extends JFrame{
    public static int GRID_SIZE = 300;
    
    private Controller controller;
    private JPanel pnlAddShips;
    private JPanel pnlGrid;
    private PlayerSquare[][] grid;
    private JPanel pnlEnemyGrid;
    private JPanel pnlPreStart;
    private EnemySquare[][] enemyGrid;
    private boolean horizontal = true;
    private JLabel lblMessage;
    private int shipsAdded = 0;
    private JButton btnStart;

    public View(Controller controller){
        super("Battleship");
        this.controller = controller;

        setSize(GRID_SIZE*2+50, GRID_SIZE+100+60);
        setPreferredSize(new Dimension(GRID_SIZE*2+50, GRID_SIZE+100+60));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        this.setResizable(false);
        pnlAddShips = new JPanel();
        pnlAddShips.setBackground(new Color(5, 10, 55));
        pnlAddShips.setPreferredSize(new Dimension(GRID_SIZE*2, 70));
        pnlAddShips.setSize(new Dimension(GRID_SIZE*2, 70));
        JButton btnRand = new JButton("Randomize");   
        btnStart = new JButton("Start");
        btnStart.setEnabled(false);
        JButton btnReset = new JButton("Reset");
        btnRand.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                resetPlayerBoard();
                controller.setRandomPlayer(); 
                postMessage("Press Start to Begin!");
                shipsAdded = 5; 
                btnStart.setEnabled(true);              
            }            
        });
        btnStart.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(controller.allShipsPlaced()){
                    pnlPreStart.setVisible(false);
                    pnlEnemyGrid.setVisible(true);
                    controller.beginGame();
                    ((JButton) e.getSource()).setEnabled(false);
                    btnRand.setEnabled(false);
                    btnReset.setEnabled(false);
                    postMessage("Click a square below to attack!");
                    for(int r=0; r<10; r++){
                        for(int c=0; c<10; c++){
                            enemyGrid[r][c].setBackground(Square.EMPTY_COLOR);                        
                        }
                    }
                }                
            }            
        });
        btnReset.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                resetPlayerBoard();  
                resetEnemyBoard();       
            }            
        });
        pnlAddShips.add(btnRand);
        pnlAddShips.add(btnStart);
        pnlAddShips.add(btnReset);
        pnlGrid = new JPanel();
        pnlGrid.setBackground(Color.WHITE);
        pnlGrid.setPreferredSize(new Dimension(View.GRID_SIZE, View.GRID_SIZE));
        pnlGrid.setSize(new Dimension(View.GRID_SIZE, View.GRID_SIZE));
        pnlGrid.setLayout(null);

        grid = new PlayerSquare[10][10];
        for(int r=0; r<10; r++){
            for(int c=0; c<10; c++){
                PlayerSquare square = new PlayerSquare(r, c, this);
                
                grid[r][c] = square;
                pnlGrid.add(square);
            }
        }

        pnlPreStart = new JPanel();
        pnlPreStart.setBackground(Color.WHITE);
        pnlPreStart.setPreferredSize(new Dimension(View.GRID_SIZE, View.GRID_SIZE));
        pnlPreStart.setSize(new Dimension(View.GRID_SIZE, View.GRID_SIZE));
        JLabel lblPreStart = new JLabel("<html><p style='margin-left: 20px; margin-bottom: 5px;'>To place your ships on the grid:</p><ol style='margin-top: 0;'><li>Move your mouse over the grid.</li>"+
                                        "<li>Right click to toggle horizontal and vertical orientation.</li>"+
                                        "<li>Click to place your ship in a valid location.</li><li>Once all 5 ships have been placed, press 'Start'.</li></ol></html>");
        
        lblPreStart.setPreferredSize(new Dimension(GRID_SIZE-10, GRID_SIZE-10));
        lblPreStart.setSize(new Dimension(GRID_SIZE-10, GRID_SIZE-10));
        pnlPreStart.add(lblPreStart);
        
        pnlEnemyGrid = new JPanel();
        pnlEnemyGrid.setBackground(Color.WHITE);
        pnlEnemyGrid.setPreferredSize(new Dimension(View.GRID_SIZE, View.GRID_SIZE));
        pnlEnemyGrid.setSize(new Dimension(View.GRID_SIZE, View.GRID_SIZE));
        pnlEnemyGrid.setLayout(null);
        pnlEnemyGrid.setVisible(false);
        enemyGrid = new EnemySquare[10][10];
        for(int r=0; r<10; r++){
            for(int c=0; c<10; c++){
                EnemySquare square = new EnemySquare(r, c, this);
                square.setBackground(Square.MISS_COLOR);
                enemyGrid[r][c] = square;
                pnlEnemyGrid.add(square);
            }
        }

        JPanel separator = new JPanel();
        separator.setBackground(new Color(5, 10, 55));
        separator.setPreferredSize(new Dimension(10, GRID_SIZE));
        separator.setSize(new Dimension(10, GRID_SIZE));

        lblMessage = new JLabel("Place your ships", JLabel.CENTER);
        lblMessage.setPreferredSize(new Dimension(GRID_SIZE*2, 20));
        lblMessage.setSize(new Dimension(GRID_SIZE*2, 20));        
        lblMessage.setForeground(Color.WHITE);


        JPanel pnlInfo = new JPanel();
        pnlInfo.setPreferredSize(new Dimension(GRID_SIZE*2, 35));
        pnlInfo.setSize(new Dimension(GRID_SIZE*2, 35));        
        pnlInfo.setBackground(Color.BLACK);
        pnlInfo.setLayout(new FlowLayout());

        JLabel lblinfo = new JLabel("Your Ships", JLabel.CENTER);
        lblinfo.setPreferredSize(new Dimension(GRID_SIZE-20, 20));
        lblinfo.setSize(new Dimension(GRID_SIZE-20, 20));  
        lblinfo.setBackground(Color.WHITE);
        lblinfo.setOpaque(true);       
        pnlInfo.add(lblinfo);

        JLabel lblGap = new JLabel();
        lblGap.setPreferredSize(new Dimension(20, 25));
        lblGap.setSize(new Dimension(20, 25));                
        pnlInfo.add(lblGap);

        JLabel lblinfo2 = new JLabel("Enemy Ships", JLabel.CENTER);
        lblinfo2.setPreferredSize(new Dimension(GRID_SIZE-20, 20));
        lblinfo2.setSize(new Dimension(GRID_SIZE-20, 20));  
        lblinfo2.setBackground(Color.WHITE);
        lblinfo2.setOpaque(true); 
        pnlInfo.add(lblinfo2);



        pnlAddShips.add(lblMessage);
        Container container = getContentPane();
        container.setLayout(new FlowLayout());
        container.add(pnlAddShips);
        container.add(pnlInfo);
        container.add(pnlGrid);        
        container.add(separator);
        container.add(pnlPreStart);
        container.add(pnlEnemyGrid);
        pack();
        this.setVisible(true);
    }
    
    public void resetPlayerBoard(){
        shipsAdded = 0;
        controller.resetPlayerBoard();  
        for(PlayerSquare[] sqrs: grid){
            for(int i=0; i<sqrs.length; i++){
                sqrs[i].reset();
                sqrs[i].setIcon(null);
            }
        }
        btnStart.setEnabled(false);
        
    }

    public void resetEnemyBoard(){
        /*controller.resetEnemyBoard();  
        for(EnemySquare[] sqrs: enemyGrid){
            for(int i=0; i<sqrs.length; i++){
                sqrs[i].reset();
                sqrs[i].setIcon(null);
            }
        }*/
    }
    public void setCursor(){
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }
    public void revertCursor(){
        this.setCursor(Cursor.getDefaultCursor());
    }
    public ShipType getShipType(){
        switch (shipsAdded) {
            case 0:
                return ShipType.CARRIER;
            case 1:
                return ShipType.BATTLESHIP;
            case 2:
                return ShipType.SUBMARINE;
            case 3:
                return ShipType.DESTROYER;                
            case 4:
                return ShipType.CRUISER;
            default:
                return ShipType.BATTLESHIP;                
        }
    }
    public void setShip(int startRow, int startCol){
        ShipType type = getShipType();
        int len = type.length();
        boolean success = controller.setShip(startRow, startCol, len, horizontal);
        if(success && shipsAdded<5){
            Ship s = new Ship(type, startRow, startCol, horizontal);
            controller.addShip(s, shipsAdded);
            int imgIndex = 0;
            if(horizontal){                
                for(int c=startCol; c<startCol+len; c++){
                    if(c<grid[startRow].length) {
                        grid[startRow][c].setShip(s);
                        grid[startRow][c].setIcon(type.img(imgIndex, horizontal));
                        //grid[startRow][c].setText("X");
                    }
                    imgIndex++;

                }
            }else{
                for(int r=startRow; r<startRow+len; r++){
                    if(r<grid.length) {
                        grid[r][startCol].setShip(s);
                        grid[r][startCol].setIcon(type.img(imgIndex, horizontal));
                    }
                    imgIndex++;
                }                
            }
            shipsAdded++;
            if(shipsAdded==5){
                btnStart.setEnabled(true);
                postMessage("Press Start to Begin!");
            } 
        }        
    }
    public void placeShips(Ship[] ships){
        for(Ship s: ships){
            int startCol = s.getCol();
            int startRow = s.getRow();
            int len = s.getLength();
            boolean horizontal = s.isHorizontal();
            int imageIndex = 0;
            if(horizontal){
                for(int c=startCol; c<startCol+len; c++){
                    if(c<grid[startRow].length) {
                        grid[startRow][c].setShip(s);
                        grid[startRow][c].setIcon(s.getType().img(imageIndex, horizontal));
                        imageIndex++;
                    }
                }
            }else{
                for(int r=startRow; r<startRow+len; r++){
                    if(r<grid.length) {
                        grid[r][startCol].setShip(s);
                        grid[r][startCol].setIcon(s.getType().img(imageIndex, horizontal));
                        imageIndex++;
                    }
                }
            }
        }
    }
    public void previewShip(int startRow, int startCol){
        ShipType type = getShipType();
        int len = type.length();
        if(horizontal){
            boolean broken = false;
            if(startCol+len>grid[startRow].length) broken = true;
            for(int c=startCol; c<startCol+len; c++){
                if(c<grid[startRow].length && grid[startRow][c].isOccupied()) broken=true;
            }
            for(int c=startCol; c<startCol+len; c++){
                if(broken){
                    if(c<grid[startRow].length) grid[startRow][c].markAsError();
                }else{
                    if(c<grid[startRow].length) grid[startRow][c].markAsSelected();
                }
                
            }
        }else{
            boolean broken = false;
            if(startRow+len>grid.length) broken = true;
            for(int r=startRow; r<startRow+len; r++){
                if(r<grid.length &&grid[r][startCol].isOccupied()) broken = true;
            }
            for(int r=startRow; r<startRow+len; r++){
                if(broken){
                    if(r<grid.length) grid[r][startCol].markAsError();
                }else{
                    if(r<grid.length) grid[r][startCol].markAsSelected();
                }
                
            }
        }
    }
    public void revertPreview(int startRow, int startCol){
        ShipType type = getShipType();
        int len = type.length();
        if(horizontal){
            for(int c=startCol; c<startCol+len; c++){
                if(c<grid[startRow].length) grid[startRow][c].markAsUnselected();
            }
        }else{
            for(int r=startRow; r<startRow+len; r++){
                if(r<grid.length) grid[r][startCol].markAsUnselected();
            }
        }
    }
    public void attack(boolean isEnemy, int row, int col) {
        controller.attack(isEnemy, row, col);        
    }
    public Ship markHit(boolean isEnemy, int row, int col){  
        Ship ship = null;      
        if(isEnemy){
            ship = enemyGrid[row][col].markAsHit();
        }else{
            ship = grid[row][col].markAsHit();
        }
        return ship;
    }
    public void markMiss(boolean isEnemy, int row, int col){
        if(isEnemy){
            enemyGrid[row][col].markAsMiss();
        }else{
            grid[row][col].markAsMiss();
        }
    }
    public void postMessage(String string) {
        lblMessage.setText(string);    
    }
    public void setEnemyShips(Ship[] enemyShips) {
        for(int i=0; i<enemyShips.length; i++){
            Ship s = enemyShips[i];
            if(s.isHorizontal()){
                int row = s.getRow();
                for(int col = s.getCol(); col<s.getCol()+s.getLength(); col++){
                    enemyGrid[row][col].placeShip(s);
                }
            }else{
                int col = s.getCol();
                for(int row = s.getRow(); row<s.getRow()+s.getLength(); row++){
                    enemyGrid[row][col].placeShip(s);
                }
            }
        }
    }

    public void toggleHorizontal(int row, int col) {
        revertPreview(row, col);        
        horizontal = !horizontal;
        previewShip(row, col);
    }

    public void reveal(Ship[] enemyShips){
        for(Ship s: enemyShips){
            if(!s.wasSunk()){
                ArrayList<Coordinate> coords = s.getCoordinates();
                for(Coordinate cd: coords){
                    if(!enemyGrid[cd.getRow()][cd.getCol()].wasHit()){
                        enemyGrid[cd.getRow()][cd.getCol()].setBackground(Square.SHIP_COLOR);
                        enemyGrid[cd.getRow()][cd.getCol()].setIcon(s.getIcon(cd.getRow(), cd.getCol()));
                    }                    
                }
            }
        }
    }

    public void markAsSunk(Ship s, boolean isEnemy){        
        ArrayList<Coordinate> coordinates = s.getHitCoordinates();        
        for(Coordinate coord:coordinates){
            if(isEnemy){
                enemyGrid[coord.getRow()][coord.getCol()].markAsSunk();
                enemyGrid[coord.getRow()][coord.getCol()].setIcon(s.getIcon(coord.getRow(), coord.getCol()));
            }else{
                grid[coord.getRow()][coord.getCol()].markAsSunk();
            }
        }
    }
}
