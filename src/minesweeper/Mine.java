package minesweeper;

import javafx.scene.shape.*;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.util.*;

public class Mine extends Tile {

    private Circle circle;

    public Mine(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.board = board;
        this.side = board.getSide();
        this.dangers = 9;
        tile = new Group();
        back = new Rectangle(x * side, y * side + board.getHeaderHeight(), side, side);
        back.setFill(Color.LIGHTGREEN);
        back.setStrokeWidth(3);
        back.setStroke(Color.SEAGREEN);
        tile.getChildren().add(back);
        interactions();
    }

    private void interactions() {
        back.setOnMousePressed((MouseEvent me) -> {
            // click detected
            if (me.isPrimaryButtonDown()) {
                if (!board.set) {
                    board.set = true;
                    board.setBoard(x, y);
                } else {
                    leftClick();
                }
            } else if (me.isSecondaryButtonDown()) {
                rightClick();
            }
        });
    }

    @Override
    public void rightClick() {
        // add flag if not revealed
        if (!revealed) {
            board.clickFlag(x, y);
        }
    }

    @Override
    public void leftClick() {
        // blow up mine
        revealed = true;
        draw();
        board.loseGame();
    }

    @Override
    public Group draw() {
        if (revealed && circle == null) {
            // mine exploded
            Random rand = new Random();
            int r = rand.nextInt(255), b = rand.nextInt(255), g = rand.nextInt(255);
            Color color = Color.rgb(r, g, b);
            back.setFill(color);
            back.setStroke(color.darker());
            circle = new Circle(x * side + side / 2, y * side + board.getHeaderHeight() + side / 2, side / 4, color.brighter());
            tile.getChildren().add(circle);
        }
        return tile;
    }

}
