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

public class Minesweeper extends Application 
{ 
  
  @Override
  public void start(Stage primaryStage) {
    
    Scene scene;
    Board board = new Board(10, 8, 300, 200, 12);
    Safe safe = new Safe(3, 3, board, 3);
    Group g = new Group();
    g.getChildren().add(safe.draw() );
    scene = new Scene(g, 300, 200);
    
    primaryStage.setTitle("A Simple Scene!");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
    
  public static void main(String[] args) {
    System.out.println("Hello World");
    launch(args);
  }
} 
