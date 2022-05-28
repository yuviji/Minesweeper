package minesweeper;

import javafx.scene.shape.*;
import javafx.scene.Group;
import javafx.scene.paint.*;
import java.util.*;
import javafx.scene.input.MouseEvent;

public class Board {

    private Tile[][] board;
    private Tile[][] copy;
    private int xSize, ySize;
    private double tileSize, bWidth, bHeight;
    private Rectangle header;

    public Board(int xSize, int ySize, int numBombs) {
        this.xSize = xSize;
        this.ySize = ySize;
        board = new Tile[xSize][ySize];
        copy = new Tile[xSize][ySize];
        tileSize = Math.min(650.0 / ySize, 900.0 / xSize);
        bWidth = tileSize * xSize;
        bHeight = tileSize * ySize;
        header = new Rectangle(bWidth, 700.0 - bHeight);
        header.setFill(Color.DARKGREEN);
        setMines(numBombs);
        setSafes();
        printBoard();
        interactions();
    }
    
    private void printBoard(){
        for (Tile[] row : board){
            for (Tile t : row){
                if (t instanceof Mine)
                    System.out.println("@ ");
                else
                    System.out.println("* ");
            }
            System.out.println();
        }
    }
    
    private void interactions() {
        header.setOnMousePressed((MouseEvent me) -> {
            System.out.println("MOUSE DETECTED");
        });
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
                int count = 0;
                for (int a = -1; a <= 1; a++) {
                    for (int b = -1; b <= 1; b++) {
                        int checkX = i + a, checkY = j + b;
                        if ((checkX == i && checkY == j)
                                || checkX < 0 || checkX >= xSize
                                || checkY < 0 || checkY >= ySize) {
                            continue;
                        }
                        if (board[checkX][checkY] != null) {
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
        // removal returns false
        if (board[x][y] instanceof Flag) {
            board[x][y] = copy[x][y];
            //return false;
        } // add flag to board if the tile is not already a flag
        // addition returns true
        else {
            copy[x][y] = board[x][y];
            board[x][y] = new Flag(x, y, this);
            //return true;
        }

        return board[x][y];
    }

    public void loseGame() {
        //finds all bombs, check if unrevealed
        //call draw on every mine
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
        //animated game winning
        System.out.println("you win");
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
        Group fun = new Group();
        fun.getChildren().add(header);
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

}
