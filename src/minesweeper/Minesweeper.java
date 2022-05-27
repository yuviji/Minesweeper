package minesweeper;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import java.util.*;

public class Minesweeper extends Application 
{ 
  
  @Override
  public void start(Stage primaryStage) {
    
    Label label; 
    TextField tf;
    Button button;
    VBox vbox;
    Scene scene;
    
    tf = new TextField("Text Field!");
    tf.setMaxWidth(200);
    
    label = new Label("Type text and click the button");
    button = new Button("Click"); 
    
    button.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent e) {
        label.setText(tf.getText());
      }
    });
    vbox = new VBox(label, tf, button);
    vbox.setSpacing(20);
    vbox.setAlignment(Pos.CENTER);
    scene = new Scene(vbox, 300, 200);
    
    primaryStage.setTitle("A Simple Scene!");
    primaryStage.setScene(scene);
    primaryStage.show();
  } 
    
  public static void main(String[] args) {
    int len = 300, wid = 200;
    Controller control = new Controller(len, wid);
    control.setup(1);
    System.out.println("sniff");
    launch(args);
  }
} 
