package minesweeper;

import javafx.scene.shape.*;
import javafx.scene.input.*;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public abstract class Tile {

    protected int x, y, dangers;
    protected double side;
    protected Board board;
    protected Group tile;
    protected Rectangle back;
    protected boolean revealed;

    abstract void rightClick();

    abstract void leftClick();

    abstract Group draw();

//    public Group draw() {
//        back = new Rectangle(x * side, y * side + 700 - board.getHeight(), side, side);
//        back.setFill(Color.LIGHTGREEN);
//        back.setStrokeWidth(3);
//        back.setStroke(Color.SEAGREEN);
//        tile.getChildren().add(back);
//        return tile;
//    }
}
