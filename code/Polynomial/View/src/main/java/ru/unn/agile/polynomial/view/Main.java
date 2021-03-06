package ru.unn.agile.polynomial.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Markup.fxml"));
        primaryStage.setTitle("Polynomials Calculator");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        double height = primaryStage.getHeight();
        primaryStage.setMaxHeight(height);
        primaryStage.setMinHeight(height);
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
