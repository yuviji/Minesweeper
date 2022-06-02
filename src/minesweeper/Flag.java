package minesweeper;

import javafx.scene.shape.*;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Flag extends Tile {

    private Polygon triangle;
    private Line pole;

    public Flag(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.board = board;
        this.side = board.getSide();
        revealed = true;
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
                leftClick();
            } else if (me.isSecondaryButtonDown()) {
                rightClick();
            }
        });
    }

    @Override
    public void rightClick() {
        // place flag
        board.clickFlag(x, y);
    }

    @Override
    public void leftClick() {
    }

    @Override
    public Group draw() {
        // draw pole and flag
        triangle = new Polygon();
        pole = new Line(x * side + 0.25 * side, y * side + board.getHeaderHeight() + 0.15 * side, x * side + 0.25 * side, y * side + board.getHeaderHeight() + 0.85 * side);
        pole.setStrokeWidth(0.10 * side);
        pole.setFill(Color.RED);
        triangle.getPoints().addAll(new Double[]{
            x * side + 0.25 * side, y * side + board.getHeaderHeight() + 0.1 * side,
            x * side + 0.25 * side, y * side + board.getHeaderHeight() + 0.5 * side,
            x * side + 0.8 * side, y * side + board.getHeaderHeight() + 0.3 * side});
        triangle.setFill(Color.RED);
        tile.getChildren().addAll(pole, triangle);
        return tile;
    }
}
