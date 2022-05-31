package minesweeper;

import javafx.scene.Group;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import java.util.*;

public class Board {

    private Tile[][] board;
    private Tile[][] copy;
    private final int xSize, ySize;
    private int flagCount;
    private final double tileSize, bWidth, bHeight, headerHeight;
    private Group fun;
    private Rectangle header;
    private Text title, flags;

    public Board(int xSize, int ySize, int numBombs) {
        this.xSize = xSize;
        this.ySize = ySize;
        board = new Tile[xSize][ySize];
        copy = new Tile[xSize][ySize];
        tileSize = Math.min(650.0 / ySize, 900.0 / xSize);
        bWidth = tileSize * xSize;
        bHeight = tileSize * ySize;
        headerHeight = 700.0 - bHeight;
        fun = new Group();
        header = new Rectangle(bWidth, headerHeight);
        header.setFill(Color.DARKGREEN);
        title = new Text(bWidth / 3, headerHeight / 1.5, "MINESWEEPER");
        title.setFill(Color.BEIGE);
        title.setFont(Font.loadFont(getClass().getResource("Fonts/ARCADECLASSIC.TTF").toString(), headerHeight * 0.75));
        flagCount = numBombs;
        flags = new Text(bWidth * 0.75, headerHeight / 1.5, Integer.toString(flagCount));
        flags.setFill(Color.BEIGE);
        flags.setFont(Font.loadFont(getClass().getResource("Fonts/PressStart2P-Regular.ttf").toString(), headerHeight * 0.4));
        setMines(numBombs);
        setSafes();
    }

    private void setMines(int bombs) {
        Random rand = new Random();
        int x, y;
        for (int i = 0; i < bombs; i++) {
            do {
                x = rand.nextInt(xSize);
                y = rand.nextInt(ySize);
            } while (board[x][y] != null);
            board[x][y] = new Mine(x, y, this);
        }
    }

    private void setSafes() {
        // scan the board and make the Safes
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                if (board[i][j] != null) {
                    continue;
                }
                int count = 0;
                for (int a = -1; a <= 1; a++) {
                    for (int b = -1; b <= 1; b++) {
                        int checkX = i + a, checkY = j + b;
                        if ((checkX == i && checkY == j)
                                || checkX < 0 || checkX >= xSize
                                || checkY < 0 || checkY >= ySize) {
                            continue;
                        }
                        if (board[checkX][checkY] instanceof Mine) {
                            count++;
                        }
                    }
                }
                board[i][j] = new Safe(i, j, this, count);
            }
        }
    }

    public Tile clickFlag(int x, int y) {
        // remove flag from board, replacing it with what we put in copy
        // as long as copy value is not null
        fun.getChildren().remove(board[x][y].draw());
        if (board[x][y] instanceof Flag) {
            board[x][y] = copy[x][y];
            flagCount++;
        } // add flag to board if the tile is not already a flag
        else {
            copy[x][y] = board[x][y];
            board[x][y] = new Flag(x, y, this);
            flagCount--;
        }
        fun.getChildren().add(board[x][y].draw());
        flags.setText(Integer.toString(flagCount));
        return board[x][y];
    }

    public void loseGame() {
        //finds all bombs, check if unrevealed
        //call draw on every mine
        //don't forget that some mines might not be on the board, might be in copy array
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                if (copy[i][j] instanceof Mine) {
                    board[i][j] = copy[i][j];
                }
                if (board[i][j] instanceof Mine) {
                    board[i][j].revealed = true;
                    board[i][j].draw();
                }
            }
        }
        title.setText("YOU LOSE!");
    }

    public boolean wonwon() {
        //returns false if game has not been won
        //calls winGame and returns true if game has been won
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                if (board[i][j] instanceof Safe && !board[i][j].revealed) {
                    return false;
                }
            }
        }
        this.winGame();
        return true;
    }

    public void winGame() {
        //animated game winning?
        title.setText("YOU WIN!");
    }

    public void revealSurroundings(int r, int c) {
        // revealing nearby tiles if a blank tile is clicked on
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> y = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int checkX = r + i, checkY = c + j;
                if ((checkX == r && checkY == c)
                        || checkX < 0 || checkX >= xSize
                        || checkY < 0 || checkY >= ySize) {
                    continue;
                }
                if (board[r + i][j + c] instanceof Safe
                        && !board[r + i][j + c].revealed) {
                    if (board[r + i][j + c].dangers == 0) {
                        x.add(r + i);
                        y.add(c + j);
                    }
                    board[r + i][j + c].leftClick();
                }
            }
        }
        for (int i = 0; i < x.size(); i++) {
            revealSurroundings(x.get(i), y.get(i));
        }
    }

    public Group draw() {
        fun.getChildren().addAll(header, title, flags);
        for (Tile[] row : board) {
            for (Tile t : row) {
                fun.getChildren().add(t.draw());
            }
        }
        return fun;
    }

    public int getX() {
        return this.xSize;
    }

    public int getY() {
        return this.ySize;
    }

    public double getHeight() {
        return this.bHeight;
    }

    public double getWidth() {
        return this.bWidth;
    }

    public double getSide() {
        return this.tileSize;
    }

    public double getHeaderHeight() {
        return this.headerHeight;
    }
}
