public class Coordinate {
    private int row;
    private int col;

    public Coordinate(int row, int col){
        this.row = row;
        this.col = col;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public boolean equals(int row, int col){
        return (this.row==row && this.col==col);
    }

    public boolean equals(Coordinate coord){
        return (this.row==coord.getRow() && this.col==coord.getCol());
    }

    @Override
    public String toString(){
        return "row: "+row+", col: "+col;
    }
}
