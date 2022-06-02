package minesweeper;

import javafx.scene.Group;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.util.Pair;
import java.util.*;

public class Board {

    private Tile[][] board;
    private Tile[][] copy;
    private final int xSize, ySize;
    private int flagCount;
    private int numBombs;
    private final double tileSize, bWidth, bHeight, headerHeight;
    private Group fun;
    private Rectangle header;
    private Text title, flags;
    private Minesweeper minesweeper;
    protected boolean set;

    public Board(int xSize, int ySize, int numBombs, Minesweeper minesweeper) {
        this.minesweeper = minesweeper;
        this.xSize = xSize;
        this.ySize = ySize;
        this.numBombs = numBombs;
        set = false;
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
        setBoard(0, 0);
        copy = new Tile[xSize][ySize];
    }

    public void setBoard(int x, int y) {
        board = new Tile[xSize][ySize];
        ArrayList<Pair<Integer, Integer>> mines = new ArrayList<>();
        ArrayList<Pair<Integer, Integer>> safes = new ArrayList<>();
        Random rand = new Random();
        System.out.println("Start: " + x + ", " + y);

        // make first set of safes, surrounding where first click was
        safes.add(new Pair(x, y));
        board[x][y] = new Safe(x, y, this, 0);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int a = x + i, b = y + j;
                if ((a == x && b == y)
                        || a < 0 || a >= xSize
                        || b < 0 || b >= ySize) {
                    continue;
                }
                safes.add(new Pair(a, b));
            }
        }

        while (mines.size() < numBombs) {
            // create mines on random coordinates excluding safes
            int a = rand.nextInt(xSize), b = rand.nextInt(ySize);
            Pair temp = new Pair(a, b);
            if (!safes.contains(temp) && !mines.contains(temp)) {
                mines.add(temp);
            }
        }
        // fill remaining spaces with safes
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                Pair temp = new Pair(i, j);
                if (!mines.contains(temp) && !safes.contains(temp)) {
                    safes.add(temp);
                }
            }
        }
        // add mines to board
        for (Pair<Integer, Integer> p : mines) {
            int a = p.getKey(), b = p.getValue();
            board[a][b] = new Mine(a, b, this);
        }
        // add safes to board
        for (Pair<Integer, Integer> p : safes) {
            int a = p.getKey(), b = p.getValue();
            if (a == x && b == y) {
                continue;
            }
            board[a][b] = new Safe(a, b, this, dangerCount(a, b));
        }
        for (int j = 0; j < ySize; j++) {
            for (int i = 0; i < xSize; i++) {
                System.out.print(board[i][j].dangers + " ");
            }
            System.out.println("");
        }
        System.out.println("");
        if (set) {
            fun.getChildren().clear();
            draw();
            board[x][y].leftClick();
        }
    }

    private int dangerCount(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int a = x + i, b = y + j;
                if ((a == x && b == y)
                        || a < 0 || a >= xSize
                        || b < 0 || b >= ySize) {
                    continue;
                }
                if (board[a][b] instanceof Mine) {
                    count++;
                }
            }
        }
        return count;
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

    public boolean isWon() {
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
        minesweeper.end(false);
    }

    public void winGame() {
        // won
        title.setText("YOU WIN!");
        minesweeper.end(true);
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

    public void addTiles() {

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
