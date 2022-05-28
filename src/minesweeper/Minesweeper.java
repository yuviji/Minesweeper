package minesweeper;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import java.util.*;
import javafx.scene.Group;

public class Minesweeper extends Application {

    @Override
    public void start(Stage primaryStage) {

        Scene scene;
        int x = 10, y = 8;
        double len = 900.0, wid = 700.0;
        int bombs = (int) Math.ceil((0.15 * (x * y)));
        Board board = new Board(x, y, bombs);
        Group g = new Group();
        g.getChildren().add(board.draw());
        scene = new Scene(g, len, wid);
        
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        System.out.println("Hello World");
        launch(args);
    }
}
