public class Board
{
  private String[][] squares;

  public Board()
  {
    // Initialize squares at correct size
    squares = new String[10][10];

    // Iterate through each element and set to "-"
    resetBoard();
  }

  public void resetBoard(){
    for (String[] r : squares)
    {
      for (int i = 0; i < r.length; i++)
      {
        r[i] = "-";
      }
    }
  }

  public Ship randomShips(ShipType type){
    int r = -1;
    int c = -1;
    boolean horizontal = false;
    int len = type.length();
    while(!addShip(r, c, len, horizontal)){
      r=(int)(Math.random()*10);
      c=(int)(Math.random()*10);
      if(Math.random()>=0.5){
        horizontal=true;
      }else{
        horizontal=false;
      }
    }
    return new Ship(type, r, c, horizontal);
  }

  public String getLocation(int row, int col){
    return squares[row][col];
  }
  
  public String toString()
  {
    String result = "";

    // Iterate through each row
    for (String[] r : squares)
    {
      // Iterate through each element of row and add to string
      for (String s : r)
      {
        result += s + " ";
      }

      // Add new line to string
      result += "\n";
    }
    return result;
  }

  public int shoot(int row, int col)
  {
    // If shot out of bounds, return -1
    if(row < 0 || col < 0 || row >= squares.length || col >= squares[0].length)
      return -1;

    // If ship hit, replace with "x" and return 1
    if(squares[row][col].equals("b"))
    {
      squares[row][col] = "x";
      return 1;
    }

    // If square already shot, return 2
    if(squares[row][col].equals("x") || squares[row][col].equals("m"))
      return 2;

    // Otherwise mark as miss, return 0
    squares[row][col] = "m";
    return 0;
  }

  public boolean addShip(int row, int col, int len, boolean horizontal)
  {
    // If start of ship is off grid return false
    if (row < 0 || col < 0 || row >= squares.length || col >= squares[0].length)
      return false;

    if(horizontal)
    {
      // If end of ship is off grid return false
      if(col + len > squares.length)
        return false;

      /* This code iterates through elements of the 2-d array squares by
       * incrementing the column index by 1, beginning with a value of col and
       * exiting at col + length, while the row index is kept the same. This
       * is done twice: the first time checks each element to see if there is
       * already a "b" present, and returns false if so. The second loop
       * replaces each element with "b" to place the ship.
       */
      for (int i = col; i < col + len; i++)
      {
        if(!squares[row][i].equals("-"))
        {
          return false;
        }
      }

      for (int i = col; i < col + len; i++)
      {
        squares[row][i] = "b";
      }
    }

    else
    {
      // If end of ship is off grid return false
      if(row + len > squares.length)
        return false;

      /* This code works in the same way as the horizontal section above but
       * iterates through the rows and keeps the column the same.
       */
      for (int i = row; i < row + len; i++)
      {
        if(!squares[i][col].equals("-"))
        {
          return false;
        }
      }

      for (int i = row; i < row + len; i++)
      {
        squares[i][col] = "b";
      }
    }

    // If this point is reached, ship has been added so return true
    return true;
  }

  public boolean gameOver()
  {
    // Iterate through each element, return false if "b" found
    for (String[] r : squares)
    {
      for (String s : r)
      {
        if(s.equals("b"))
          return false;
      }
    }

    // If no "b"s, return true
    return true;
  }


  //Not used in this version
  public boolean foundShip(int len)
  {
    // Iterates through all rows of array
    for (int r = 0; r < squares.length; r++)
    {
      /* Iterates through each element in the row, looking for
       * consecutive runs of the String character "b". This is done by using
       * an interior while loop which is entered when a "b" is found
       * and increments both the variable c and the variable foundLen as long as "b"
       * appears. If foundLen is equal to len at the end of this
       * interior loop, the value true is returned.
       */
      for (int c = 0; c < squares[r].length; c++)
      {
        int count = 0;
        while (c < squares[r].length && squares[r][c].equals("b"))
        {
         count++;
          c++;
        }
        if(count == len)
          return true;
      }
    }

    // Works as above, but checks each column for vertical runs
    for (int c = 0; c < squares[0].length; c++)
    {
    for (int r = 0; r < squares.length; r++)
    {
      int count = 0;
      while(r < squares.length && squares [r][c].equals("b"))
      {
         count++;
          r++;
        }
        if(count == len)
          return true;
      }
    }

    return false;
  }}
