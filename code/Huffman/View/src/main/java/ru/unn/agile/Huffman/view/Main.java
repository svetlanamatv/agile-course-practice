package ru.unn.agile.Huffman.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Huffman.fxml"));
        primaryStage.setTitle("Huffman");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
