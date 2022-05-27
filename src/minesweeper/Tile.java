package minesweeper;
import javafx.scene.shape.*;

public abstract class Tile{
  protected int x, y, dangers;
  protected double pxW, pxL;
  protected Board board;
  protected Rectangle back;
  protected boolean revealed;
  
  abstract void rightClick();
  abstract void leftClick();
  public Rectangle draw() {
      back = new Rectangle(0, 0, 10, 10);
      return back;
  }
}