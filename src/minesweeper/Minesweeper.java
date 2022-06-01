package minesweeper;

import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class Minesweeper extends Application {

    public Stage stage;
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(opening(primaryStage));
        primaryStage.show();
    }

    public Scene opening(Stage s) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setMinSize(900.0, 700.0);
        grid.setVgap(100.0);
        grid.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(0.0), new Insets(0.0))));

        // game mode button choice bar
        Button[] butt = {new Button("Easy"), new Button("Medium"), new Button("Hard")};
        HBox choice = new HBox(900.0 * 0.2);
        for (Button b : butt) {
            b.setFont(Font.loadFont(getClass().getResource("Fonts/ARCADECLASSIC.TTF").toString(), 700.0 * 0.75 * 0.05));
            b.setBackground(new Background(new BackgroundFill(Color.BEIGE, new CornerRadii(50.0), new Insets(0.0))));
            b.setTextFill(Color.GREEN);
        }
        butt[0].setOnAction((ActionEvent e) -> play(s, 10, 8));
        butt[1].setOnAction((ActionEvent e) -> play(s, 18, 14));
        butt[2].setOnAction((ActionEvent e) -> play(s, 24, 20));
        choice.getChildren().addAll(butt);
        grid.add(choice, 1, 2);

        // title text
        Text title = new Text("MINESWEEPER");
        title.setFill(Color.BEIGE);
        title.setFont(Font.loadFont(getClass().getResource("Fonts/ARCADECLASSIC.TTF").toString(), 700 * 0.15));
        Text credits = new Text("By Yuvraj Lakhotia & Suhaani Gupta");
        credits.setFill(Color.BEIGE);
        credits.setFont(Font.loadFont(getClass().getResource("Fonts/PressStart2P-Regular.ttf").toString(), 700 * 0.03));
        grid.add(title, 1, 0);
        grid.add(credits, 1, 1);
        GridPane.setValignment(title, VPos.TOP);
        GridPane.setHalignment(title, HPos.CENTER);
        GridPane.setValignment(credits, VPos.BOTTOM);
        GridPane.setHalignment(credits, HPos.CENTER);

        Scene front = new Scene(grid, 900.0, 700.0);
        front.setFill(Color.GREEN);
        return front;
    }

    public void play(Stage s, int x, int y) {
        int bombs = (int) Math.ceil((0.15 * (x * y)));
        s.setScene(new Scene(new Board(x, y, bombs, this).draw(), 900.0, 700.0));
    }

    public void end(Stage s, boolean win) {
        // text stating the state (win or lose)
        // button play again -> set scene to opening

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setMinSize(900.0, 700.0);
        grid.setVgap(100.0);
        grid.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(0.0), new Insets(0.0))));

        // win/lose text
        Text title = new Text("YOU " + (win ? "WIN" : "LOSE") + "!");
        title.setFill(Color.BEIGE);
        title.setFont(Font.loadFont(getClass().getResource("Fonts/ARCADECLASSIC.TTF").toString(), 700 * 0.15));
        grid.add(title, 1, 0);
        GridPane.setValignment(title, VPos.CENTER);
        GridPane.setHalignment(title, HPos.CENTER);

        // buttons
        HBox choice = new HBox(900.0 * 0.2);
        Button playAgain = new Button("Play Again");
        Button quit = new Button("Quit");
        playAgain.setFont(Font.loadFont(getClass().getResource("Fonts/ARCADECLASSIC.TTF").toString(), 700.0 * 0.75 * 0.05));
        quit.setFont(Font.loadFont(getClass().getResource("Fonts/ARCADECLASSIC.TTF").toString(), 700.0 * 0.75 * 0.05));
        playAgain.setBackground(new Background(new BackgroundFill(Color.BEIGE, new CornerRadii(50.0), new Insets(0.0))));
        quit.setBackground(new Background(new BackgroundFill(Color.BEIGE, new CornerRadii(50.0), new Insets(0.0))));
        playAgain.setTextFill(Color.GREEN);
        quit.setTextFill(Color.GREEN);
        playAgain.setOnAction((ActionEvent e) -> s.setScene(opening(s)));
        quit.setOnAction((ActionEvent e) -> Platform.exit());
        choice.getChildren().addAll(playAgain, quit);
        grid.add(choice, 1, 2);

        Scene end = new Scene(grid, 900.0, 700.0);
        end.setFill(Color.GREEN);
        s.setScene(end);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
