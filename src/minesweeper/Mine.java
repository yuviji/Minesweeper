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
        board.loseGame();
    }

    @Override
    public Group draw() {
        if (revealed && circle == null) {
            Random rand = new Random();
            int r = rand.nextInt(255), b = rand.nextInt(255), g = rand.nextInt(255);
            Color color = Color.rgb(r, g, b);
            back.setFill(color);
            back.setStroke(color.darker());
            circle = new Circle(x * side + side / 2, y * side + 700 - board.getHeight() + side / 2, side / 4, color.brighter());
            tile.getChildren().add(circle);
        }
        return tile;
    }

}
