package minesweeper;

public class Controller{
  private int length, width;
  private long startTime;
  private Board board;
  
  public Controller(int length, int width){
    this.length = length;
    this.width = width;
    startTime = System.currentTimeMillis(); // maintaining time at top of screen
  }

  public void setup(int mode){
    // mode: 1 = easy, 2 = medium, 3 = hard
    int x = 0, y = 0;
    switch (mode){
      case 1: x = 10; y = 8; break;
      case 2: x = 18; y = 14; break;
      case 3: x = 24; y = 20; break;
    }
    
    int bombs = (int)Math.ceil((0.15 * (x * y)));

    // create board
    board = new Board(x, y, bombs);
    
    // draw board and shit too much work
  }
  
  public void play(){
    // reading clicks and doing stuff 
    // GOO-E gui
  }
}