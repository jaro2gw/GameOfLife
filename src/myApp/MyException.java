package myApp;
/*
 * Created by Jaro on 2017-08-30.
 */

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

class MyException extends Exception {
    MyException() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("exception.fxml"));

        Stage stage = new Stage(StageStyle.UTILITY);
        stage.setTitle("Error");
        stage.setScene(new Scene(root, 400, 100));
        stage.setResizable(false);
//        stage.setOnCloseRequest(event -> stage.close());
        stage.show();
    }
}
