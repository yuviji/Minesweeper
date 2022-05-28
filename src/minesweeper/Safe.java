package minesweeper;

import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;

public class Safe extends Tile {

    public Safe(int x, int y, Board board, int dangers) {
        this.x = x;
        this.y = y;
        this.dangers = dangers;
        this.board = board;
        this.side = board.getSide();
        revealed = false;
        tile = new Group();
        back = new Rectangle(x * side, y * side + 700 - board.getHeight(), side, side);
        back.setFill(Color.LIGHTGREEN);
        back.setStrokeWidth(3);
        back.setStroke(Color.SEAGREEN);
        tile.getChildren().add(back);
        interactions();
    }

    private void interactions() {
        back.setOnMousePressed((MouseEvent me) -> {
            System.out.println("MOUSE DETECTED");
            if (me.isPrimaryButtonDown()) {
                leftClick();
            } else if (me.isSecondaryButtonDown()) {
                rightClick();
            }
        });
    }

    @Override
    public void rightClick() {
        board.clickFlag(x, y).draw();
    }

    @Override
    public void leftClick() {
        revealed = true;
        draw();
        board.wonwon();
        if (dangers == 0) {
            board.revealSurroundings(x, y);
        }
    }

    @Override
    public Group draw() {
        if (revealed) {
            back.setFill(Color.BEIGE);
            back.setStroke(Color.KHAKI);
        }
        return tile;
    }

}
