package ru.unn.agile.Huffman.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.unn.agile.Huffman.infrastructure.FileTextLogger;
import ru.unn.agile.Huffman.viewmodel.HuffmanViewModel;

public class Huffman {
    @FXML
    private HuffmanViewModel viewModel;
    @FXML
    private TextField textString;
    @FXML
    private TextField textEncodeResult;
    @FXML
    private TextField textDecodeResult;
    @FXML
    private Label lbStatus;
    @FXML
    private Button btnDecode;
    @FXML
    private Button btnEncode;
    @FXML
    private TextArea logs;

    @FXML
    void initialize() {
        viewModel.setLogCreator(new FileTextLogger("./huffman.log"));
        logs.textProperty().bindBidirectional(viewModel.getLogsProperty());
        textString.textProperty().bindBidirectional(viewModel.enterStringProperty());
        textEncodeResult.textProperty().bindBidirectional(viewModel.encodeResultProperty());
        textDecodeResult.textProperty().bindBidirectional(viewModel.decodeResultProperty());
        lbStatus.textProperty().bindBidirectional(viewModel.statusProperty());

        btnDecode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.decodeString();
            }
        });

        btnEncode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.encodeString();
            }
        });
    }
}
