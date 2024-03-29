package minesweeper;

import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.text.*;
import javafx.scene.input.MouseEvent;

public class Safe extends Tile {

    private Text number;

    public Safe(int x, int y, Board board, int dangers) {
        this.x = x;
        this.y = y;
        this.dangers = dangers;
        this.board = board;
        this.side = board.getSide();
        revealed = false;
        tile = new Group();
        back = new Rectangle(x * side, y * side + board.getHeaderHeight(), side, side);
        back.setFill(Color.LIGHTGREEN);
        back.setStrokeWidth(3);
        back.setStroke(Color.SEAGREEN);
        tile.getChildren().add(back);
        interactions();
    }

    private void interactions() {
        // click detected
        back.setOnMousePressed((MouseEvent me) -> {
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
        // flag tiles that have not been revealed
        if (!revealed) {
            board.clickFlag(x, y);
        }
    }

    @Override
    public void leftClick() {
        // reveal tile
        revealed = true;
        draw();
        board.isWon();
        if (dangers == 0) {
            board.revealSurroundings(x, y);
        }
    }

    @Override
    public Group draw() {
        if (revealed) {
            // draw with number revealed
            back.setFill(Color.BEIGE);
            back.setStroke(Color.KHAKI);
            if (dangers > 0) {
                Color fill;
                switch (dangers) {
                    case 1:
                        fill = Color.BLUE;
                        break;
                    case 2:
                        fill = Color.GREEN;
                        break;
                    case 3:
                        fill = Color.RED;
                        break;
                    case 4:
                        fill = Color.PURPLE;
                        break;
                    case 5:
                        fill = Color.MAROON;
                        break;
                    case 6:
                        fill = Color.TURQUOISE;
                        break;
                    case 7:
                        fill = Color.BLACK;
                        break;
                    case 8:
                        fill = Color.GRAY;
                        break;
                    default:
                        fill = Color.BEIGE;
                }
                number = new Text(x * side + side / 4, y * side + side / 1.5 + board.getHeaderHeight(), Integer.toString(dangers));
                number.setFill(fill);
                number.setFont(Font.loadFont(getClass().getResource("Fonts/ARCADECLASSIC.TTF").toString(), side * 0.75));
                tile.getChildren().add(number);
            }
        }
        return tile;
    }

}
