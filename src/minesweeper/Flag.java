package minesweeper;
import javafx.scene.shape.*;

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

  public Rectangle draw() {
    //draws flag object when needed
    return super.draw();
  }
}