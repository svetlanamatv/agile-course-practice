package ru.unn.agile.triangle.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.unn.agile.triangle.viewmodel.ViewModel;

public class Triangle {
    @FXML
    private ViewModelProvider viewModelProvider;
    @FXML
    private TextField txtAx;
    @FXML
    private TextField txtAy;
    @FXML
    private TextField txtBx;
    @FXML
    private TextField txtBy;
    @FXML
    private TextField txtCx;
    @FXML
    private TextField txtCy;
    @FXML
    private Button btnCalculate;

    @FXML
    void initialize() {
        ViewModel viewModel = viewModelProvider.getViewModel();

        txtAx.textProperty().bindBidirectional(viewModel.axProperty());
        txtAy.textProperty().bindBidirectional(viewModel.ayProperty());
        txtBx.textProperty().bindBidirectional(viewModel.bxProperty());
        txtBy.textProperty().bindBidirectional(viewModel.byProperty());
        txtCx.textProperty().bindBidirectional(viewModel.cxProperty());
        txtCy.textProperty().bindBidirectional(viewModel.cyProperty());

        btnCalculate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.calculate();
            }
        });
    }
}
