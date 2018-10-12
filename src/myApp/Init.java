package myApp;
/*
 * Created by Jaro on 2017-08-30.
 */

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Init implements Initializable {
    @FXML
    Button    start;
    @FXML
    TextField rows, columns;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        start.setOnMouseClicked(event -> {
            try {
                int rows, columns;
                try {
                    rows = Math.min(100, Integer.parseInt(String.valueOf(this.rows.getCharacters())));
                    columns = Math.min(100, Integer.parseInt(String.valueOf(this.columns.getCharacters())));
                }
                catch (NumberFormatException e) { throw new MyException(); }
                if (rows <= 0 || columns <= 0) throw new MyException();

                Main.begin(rows, columns);
            }
            catch (MyException ignored) {}
            catch (IOException e) { e.printStackTrace(); }
        });
    }
}
