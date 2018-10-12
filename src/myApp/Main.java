package myApp;
/*
 * Created by Jaro on 2017-08-30.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage stage;
    private static int closing = 1;

    static boolean isClosing() { return closing == 0; }

    public static Stage getStage() { return stage; }

    static void setStage(Stage stage) { Main.stage = stage; }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("init.fxml"));
        stage.setTitle("Game Of Life");
        stage.setScene(new Scene(root));
        stage.setOnCloseRequest(event -> System.exit(closing = 0));
        stage.show();
    }

    static void begin(int rows, int columns) throws IOException {
        App.setRows(rows);
        App.setColumns(columns);
        Parent root = FXMLLoader.load(Main.class.getResource("app.fxml"));
        stage.setScene(new Scene(root));
    }
}
