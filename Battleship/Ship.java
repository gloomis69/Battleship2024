
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Ship{  
    private ShipType type;  
    private int row;
    private int col;
    private int len;
    private boolean horizontal;
    private ArrayList<Coordinate> coordinates;
    private ArrayList<Coordinate> hits;
    private int numberOfHits = 0;

    public Ship(ShipType type, int row, int col, boolean horizontal){   
        this.type = type;     
        this.row = row;
        this.col = col;
        this.len = type.length();
        this.horizontal = horizontal;    
        coordinates = new ArrayList<Coordinate>();  
        hits = new ArrayList<Coordinate>();
        if(horizontal){
            for(int c=col; c<col+len; c++){
                coordinates.add(new Coordinate(row, c));
            }
        } else {
            for(int r=row; r<row+len; r++){
                coordinates.add(new Coordinate(r, col));
            }
        }     
        //System.out.println("Ship created at "+row+", "+col+", length: "+len);    
    }
    public ShipType getType(){
        return type;
    }
    public ImageIcon getIcon(int row, int col){
        int segment = 0;
        if(horizontal){
            segment = col-this.col;
        }else{
            segment = row-this.row;
        }
        return type.img(segment, horizontal);
    }
    public Coordinate getRandomCoordinate(){        
        int i = (int)(Math.random()*coordinates.size());
        return coordinates.get(i);
    }
    public ArrayList<Coordinate> getCoordinates(){
        return coordinates;
    }
    
    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }
    public int getLength(){
        return len;
    }
    public boolean isHorizontal(){
        return horizontal;
    }
    public void addHit(Coordinate rc){        
        for(int i=coordinates.size()-1; i>=0; i--){
            if(coordinates.get(i).equals(rc)) {
                hits.add(coordinates.remove(i));
            }
        }
        numberOfHits++;
    }
    public int getHits(){
        return numberOfHits;
    }
    public boolean wasSunk(){
        return coordinates.size()==0;        
    }
    public ArrayList<Coordinate> getHitCoordinates(){
        return hits;
    }
}
