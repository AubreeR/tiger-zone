package tiger_zone;

import java.util.Stack;
import java.util.ArrayList;

// need to ditch tiger from boardCell class
// need to ditch player... potentially
// tile(setXCoord(X), setYCoord(Y));
class BoardCell{
  public int x;
  public int y;
  public Tile tile;
  public Tiger tiger; //will omit
  public Player player; // will omit
  boolean isOccupied;

  public BoardCell(int x, int y){
    this.x = x;
    this.y = y;
    this.tile = null;
    this.tiger = null;
    this.player = null;
    this.isOccupied = false;
  }
  public void setTiger(Tiger tiger){
    this.tiger = tiger;
  }
  public void setTile(Tile tile){
    this.tile = tile;
  }
  public void setPlayer(Player player){
    this.player = player;
  }

  public Tiger getTiger(){
    return this.tiger;
  }
  public Tile getTile(){
    return this.tile;
  }
  public Player getPlayer(){
    return this.player;
  }
  public int getXCoord(){
    return this.x;
  }
  public int getYCoord(){
    return this.y;
  }

}

public class GameBoard{
  private final BoardCell[][] gameGrid;

  public GameBoard(int n,int m){
    gameGrid = new BoardCell[n*2][m*2];
  }

  public void initializeBoard(){
    for(int i = 0; i < gameGrid.length; ++i){
      for(int j = 0; j <  gameGrid.length; ++j){
        gameGrid[i][j] = new BoardCell(i,j);
      }
    }
  }
  public void addTileToBoard(Tile tile){
    for(int i = 0; i < gameGrid.length; ++i){
      for(int j = 0; j < gameGrid.length; ++j){
        if(tile.x == i && tile.y == j){
          gameGrid[i][j].setTile(tile);
        }
      }
    }
  }
  public Tile getTileFromBoard(int x, int y){
    return gameGrid[x][y].tile;
  }




  public static void main(String[] args){
    int size = 4; // taken from "Stack size"


    GameBoard b = new GameBoard(size, size);
    b.initializeBoard();
    System.out.println("The size of the Board is: " + b.gameGrid.length);
    System.out.println("Testing GameBoard add");

    Tile myTile = new Tile(1,1,"Dave");
    b.addTileToBoard(myTile);
    Tile result = b.getTileFromBoard(1,1);

    String s1 = result.name;
    String s2 = b.getTileFromBoard(1,1).name;

    UnitTest myUnit = new UnitTest();
    String unit = myUnit.ModTest(s1, s2);
    System.out.println(unit); 



  }



}













class UnitTest{
  UnitTest(){

  }
  public  String ModTest(String s1, String s2){
    if(s1 == s2){
      return "TEST PASSED";
    }
    else
      return "TEST FAILED";
  }

}




class Tiger{
  String name;
  public Tiger(String name){
    this.name = name;
  }
}
class Player{}
class Tile{
  int x;
  int y;
  String name;
  public Tile(int x, int y, String name){
    this. x = x;
    this.y = y;
    this.name = name;
  }
}
