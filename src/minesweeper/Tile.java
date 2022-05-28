package minesweeper;

import javafx.scene.shape.*;
import javafx.scene.Group;

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
}
