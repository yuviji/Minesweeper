package minesweeper;
import javafx.scene.shape.*;

public class Safe extends Tile{

  public Safe(int x, int y, Board board, int dangers){
    this.x = x;
    this.y = y;
    this.dangers = dangers;
    this.board = board;
    this.pxW = (board.getWpx() - 50.0) / board.getX();
    this.pxL = (board.getLpx() - 50.0) / board.getY();
    revealed = false;
  }

  public void rightClick() {
    board.clickFlag(x, y).draw();
  }

  public void leftClick() {
    revealed = true;
    draw();
    board.wonwon();
    if (dangers == 0) board.revealSurroundings(x, y);
  }

  public Rectangle draw() {
      return super.draw();
    //draw safe with num of dangers or if 0, blank
  }
}