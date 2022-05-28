package minesweeper;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Minesweeper extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        
        
        int mode = 1, x, y;
        
        switch (mode){
            case 2:
                x = 18;
                y = 14;
                break;
            case 3:
                x = 24;
                y = 20;
                break;
            default:
                x = 10;
                y = 8;
        }

        int bombs = (int) Math.ceil((0.15 * (x * y)));
        Board board = new Board(x, y, bombs);
        Scene scene = new Scene(board.draw(), 900.0, 700.0);
        
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        
        launch(args);
    }
}
