package ru.unn.agile.treesort.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.unn.agile.treesort.viewmodel.ViewModel;

public class Controller {
    @FXML
    private Label statusLabel;
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField sourceTextField;
    @FXML
    private TextField resultTextField;
    @FXML
    private Button calculateButton;

    @FXML
    void initialize() {
        calculateButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent event) {
                        viewModel.sort();
                    }
                }
        );

        sourceTextField.textProperty()
                .bindBidirectional(viewModel.sourceTextProperty());
        resultTextField.textProperty()
                .bind(viewModel.resultTextProperty());
    }
}
