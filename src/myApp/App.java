package myApp;
/*
 * Created by Jaro on 2017-08-30.
 */

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class App implements Initializable {
//    private static final double STANDARD_SIZE = 50.0;

    private static int rows, columns;

    private GraphicsContext gc;
    private Life life;

    private boolean isEvolving = false;

    @FXML
    Canvas canvas;
    @FXML
    Button pause, reset, expand;
    @FXML
    ColorPicker background, cell, border;
    @FXML
    ChoiceBox<Double> speed;

    private final Thread evolve = new Thread(() -> {
        while (!Main.isClosing()) {
            if (isEvolving) Platform.runLater(life::nextGeneration);
            try { Thread.sleep((long) (1000.0 / speed.getValue())); }
            catch (InterruptedException ignored) {}
        }
    });

    static void setRows(int rows) { App.rows = rows; }

    static void setColumns(int columns) { App.columns = columns; }

    private void refresh() {
        boolean[][] gen = life.getGeneration();

        canvas.setWidth(canvas.getWidth() + widthDiff(gen[0].length) * 30);
        canvas.setHeight(canvas.getHeight() + heightDiff(gen.length) * 30);

        double width = canvas.getWidth(), height = canvas.getHeight(),
                x = width / gen[0].length, y = height / gen.length;

        //clearing canvas
        gc.setFill(background.getValue());
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //drawing cells
        gc.setFill(cell.getValue());
        for (int col = 0; col < gen.length; col++)
            for (int row = 0; row < gen[0].length; row++)
                if (gen[col][row]) gc.fillRect(row * x, col * y, x, y);

        //drawing borders
        gc.setFill(border.getValue());
        for (int cols = 0; cols < gen[0].length; cols++) gc.fillRect(cols * x, 0, 1, height);
        for (int rows = 0; rows < gen.length; rows++) gc.fillRect(0, rows * y, width, 1);
        gc.fillRect(canvas.getWidth() - 1, 0, 1, canvas.getHeight());
        gc.fillRect(0, canvas.getHeight() - 1, canvas.getWidth(), 1);
    }

    private int heightDiff(int length) {
        int diff = length - rows;
        rows = length;
        return diff;
    }

    private int widthDiff(int length) {
        int diff = length - columns;
        columns = length;
        return diff;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        canvas.setWidth(Math.max(canvas.getWidth(), columns * STANDARD_SIZE));
//        canvas.setHeight(Math.max(canvas.getHeight(), rows * STANDARD_SIZE));

        gc = canvas.getGraphicsContext2D();

        life = new Life(rows, columns);

        //inserting some values to choice-box
        speed.getItems().addAll(0.5, 1.0, 2.0, 5.0, 10.0);
        speed.setValue(1.0);

        //inserting default values to color-pickers
        background.setValue(Color.WHITE);
        cell.setValue(Color.valueOf("rgb(0, 170, 255)"));
        border.setValue(Color.BLACK);

        //initializing option to click on pane to set cell alive or dead
        canvas.setOnMouseClicked(event -> {
            if (pause.getText().equals("pause")) updatePauseStatus("resume");
            MouseButton mb = event.getButton();
            if (mb == MouseButton.PRIMARY || mb == MouseButton.SECONDARY) {
                double x = canvas.getWidth() / life.getGeneration()[0].length, y =
                        canvas.getHeight() / life.getGeneration().length;
                life.set((int) (event.getY() / y), (int) (event.getX() / x), mb == MouseButton.PRIMARY);
            }
        });

        //initializing 'pause' feature
        pause.setOnMouseClicked(event -> {
            if (pause.getText().equals("pause")) updatePauseStatus("resume");
            else updatePauseStatus("pause");
        });

        //initializing 'reset' feature
        reset.setOnMouseClicked(event -> {
            life.reset();
            updatePauseStatus("start");
        });

        //initializing 'expand' feature
        expand.setOnMouseClicked(event -> life.expand());

        //initializing refresher
        new Thread(() -> {
            while (!Main.isClosing()) {
                Platform.runLater(this::refresh);
                try { Thread.sleep(100); }
                catch (InterruptedException ignored) {}
            }
        }).start();
    }

    private void updatePauseStatus(String s) {
        pause.setText(s);
        isEvolving = s.equals("pause");
        if (!evolve.isAlive()) evolve.start();
    }
}
