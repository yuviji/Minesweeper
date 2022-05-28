package minesweeper;

import javafx.scene.shape.*;
import javafx.scene.Group;

public class Flag extends Tile {

    public Flag(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.board = board;
        revealed = false;
    }

    @Override
    public void rightClick() {
        board.clickFlag(x, y);
        revealed = !revealed;
        draw();
    }

    @Override
    public void leftClick() {
    }

    @Override
    public Group draw() {
        return new Group();
    }
}
    