package minesweeper;

public class Flag extends Tile{
  public Flag(int x, int y, Board board){
    this.x = x;
    this.y = y;
    this.board = board;
  }

  public void rightClick(){  
    board.clickFlag(x, y);
    super.draw();
  }

  public void leftClick() {}

  public void draw() {
    //draws flag object when needed
  }
}