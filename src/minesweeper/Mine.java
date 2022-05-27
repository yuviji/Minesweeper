package minesweeper;
import javafx.scene.shape.*;

public class Mine extends Tile{
  
  
  public Mine(int x, int y, Board board){
    this.x = x;
    this.y = y;
    this.board = board;
    this.pxW = (board.getWpx() - 50.0) / board.getX();
    this.pxL = (board.getLpx() - 50.0) / board.getY();
  }

  public void rightClick() {
    board.clickFlag(x, y).draw();
  }

  public void leftClick() {
    draw();
    board.loseGame();
  }

  public Rectangle draw() {
    //draws mine object for when revealed
    revealed = true;
    return super.draw();
  }
}