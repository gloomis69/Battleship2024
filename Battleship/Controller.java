import java.util.ArrayList;

public class Controller {
    private View view;
    private Board model;
    private Board enemy;
    private Ship[] playerShips;
    private Ship[] enemyShips;
    private ArrayList<Ship> enemyFocus;
    public static boolean gameInProgress = false;
    public static boolean clickable = true;

    public Controller(){
        model = new Board();
        enemy = new Board();
        enemyShips = new Ship[5];
        playerShips = new Ship[5];
        view = new View(this);  
        enemyFocus = new ArrayList<Ship>(); 
        setRandomEnemy();             
    }

    public boolean allShipsPlaced(){
        for(Ship s: playerShips){
            if(s==null) return false;
        }
        return true;
    }
    
    public void addShip(Ship s, int index){
        if(index >=0 && index < playerShips.length) playerShips[index] = s;
    }
    
    public boolean gameInProgress(){
        return gameInProgress;
    }

    public void beginGame(){
        gameInProgress = true;
    }
    
    private boolean checkForWinner(Board board){
        return board.gameOver();
    }

    private void setRandomEnemy(){
        enemyShips[0] = enemy.randomShips(ShipType.CARRIER);
        enemyShips[1] = enemy.randomShips(ShipType.BATTLESHIP);
        enemyShips[2] = enemy.randomShips(ShipType.SUBMARINE);
        enemyShips[3] = enemy.randomShips(ShipType.DESTROYER);
        enemyShips[4] = enemy.randomShips(ShipType.CRUISER);
        view.setEnemyShips(enemyShips);
    }

    public void setRandomPlayer(){
        playerShips[0] = model.randomShips(ShipType.CARRIER);
        playerShips[1] = model.randomShips(ShipType.BATTLESHIP);        
        playerShips[2] = model.randomShips(ShipType.SUBMARINE);
        playerShips[3] = model.randomShips(ShipType.DESTROYER);        
        playerShips[4] = model.randomShips(ShipType.CRUISER);
        
        view.placeShips(playerShips);
    }
    
    public boolean setShip(int startRow, int startCol, int shipLength, boolean selected) {
        boolean wasSet =  model.addShip(startRow, startCol, shipLength, selected);
        
        return wasSet;
    }

    public void randomEnemyAttack(){
        if(enemyFocus.size()==0){
            int randRow = (int)(Math.random()*10);
            int randCol = (int)(Math.random()*10);
            while(!validAttackCoord(randRow, randCol)){
                randRow = (int)(Math.random()*10);
                randCol = (int)(Math.random()*10);
            }            
            attack(false, randRow, randCol);
        }else{
            Coordinate coord = enemyFocus.get(0).getRandomCoordinate();
            double randChance = Math.random();
            int r = coord.getRow();
            int c = coord.getCol();
            if(randChance>=0.66){
                attack(false, r, c);
            }else if(enemyFocus.get(0).getHits()>1){
                if(enemyFocus.get(0).isHorizontal()){
                    if(c>0 && validAttackCoord(r, c-1)){
                        attack(false, r, c-1);
                    }else if(c<9 && validAttackCoord(r, c+1)){
                        attack(false, r, c+1);
                    }else{
                        attack(false, r, c);
                    }
                }else{
                    if(r>0 && validAttackCoord(r-1, c)){
                        attack(false, r-1, c);
                    }else if(r<9 && validAttackCoord(r+1, c)){
                        attack(false, r+1, c);
                    }else{
                        attack(false, r, c);
                    }
                }
                
            }else{
                if(r>0 && validAttackCoord(r-1, c)){
                    attack(false, r-1, c);
                }else if(r<9 && validAttackCoord(r+1, c)){
                    attack(false, r+1, c);
                }else if(c>0 && validAttackCoord(r, c-1)){
                    attack(false, r, c-1);
                }else if(c<9 && validAttackCoord(r, c+1)){
                    attack(false, r, c+1);
                }else{
                    attack(false, r, c);
                }
            }
            
        }
        
    }

    private boolean validAttackCoord(int row, int col){
        String loc = model.getLocation(row, col);
        return loc.equals("-") || loc.equals("b");
    }

    public void attack(boolean attackEnemy, int row, int col) {
        if(gameInProgress){            
            if(attackEnemy) {
                int result = enemy.shoot(row, col);
                if(result==0) view.markMiss(true, row, col);
                if(result==1) {
                    Ship ship = view.markHit(true, row, col);                
                    if(ship.wasSunk()){ 
                        view.postMessage("Enemy ship was sunk!");
                        view.markAsSunk(ship, attackEnemy);
                    }
                }
                clickable = false;
                view.setCursor();
                EnemyAttack thread = new EnemyAttack();
                thread.start();
            }else{
                int result = model.shoot(row, col); 
                if(result==0) view.markMiss(false, row, col);
                if(result==1) {
                    Ship s = view.markHit(false, row, col);
                    if(enemyFocus.size()==0 || !s.equals(enemyFocus.get(0))) enemyFocus.add(s);
                    if(enemyFocus.get(0).wasSunk()){
                        view.postMessage("Your ship was sunk!");
                        view.markAsSunk(s, attackEnemy); 
                        enemyFocus.remove(0);
                    } 
                }
            }    
            if(checkForWinner(enemy)){
                gameInProgress = false;
                view.postMessage("You Win!");
                view.reveal(enemyShips);
            }else if(checkForWinner(model)){
                gameInProgress = false;
                view.postMessage("The Computer Wins!");
                view.reveal(enemyShips);
            }
        }
    }

    private class EnemyAttack extends Thread{        
        public void run(){
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            randomEnemyAttack();
            clickable = true;
            view.revertCursor();
        }
    }

    public void resetPlayerBoard() {
        playerShips = new Ship[5];
        model.resetBoard();
    }

    public void resetEnemyBoard() {
        enemyShips = new Ship[5];
        enemy.resetBoard();
        setRandomEnemy();
    }
}
