package ru.unn.agile.treesort.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.unn.agile.treesort.viewmodel.ViewModel;

public class Controller {
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
        calculateButton.setOnAction(event -> viewModel.sort());

        if (viewModel.isSourceTextFocused()) {
            sourceTextField.requestFocus();
        }

        viewModel.sourceTextFocusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                sourceTextField.requestFocus();
            }
        });
        viewModel.sourceTextFocusedProperty().bind(sourceTextField.focusedProperty());

        sourceTextField.textProperty()
                .bindBidirectional(viewModel.sourceTextProperty());
        resultTextField.textProperty()
                .bind(viewModel.resultTextProperty());
    }
}
