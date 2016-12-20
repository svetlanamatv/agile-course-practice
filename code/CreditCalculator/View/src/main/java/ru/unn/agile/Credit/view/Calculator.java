package ru.unn.agile.Credit.view;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.unn.agile.Credit.viewmodel.ViewModel;

public class Calculator {
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField sumTextField;
    @FXML
    private TextField monthsTextField;
    @FXML
    private TextField percentTextField;
    @FXML
    private TextField paymentTextField;
    @FXML
    private TextField overpaymentTextField;
    @FXML
    private TextField totalSumTextField;
    @FXML
    private Button btnCalc;
    @FXML
    private Button btnClear;

    @FXML
    void initialize() {

        // Two-way binding hasn't supported by FXML yet, so place it in code-behind
        sumTextField.textProperty().bindBidirectional(viewModel.sumProperty());
        monthsTextField.textProperty().bindBidirectional(viewModel.monthsProperty());
        percentTextField.textProperty().bindBidirectional(viewModel.percentProperty());
        totalSumTextField.textProperty().bindBidirectional(viewModel.totalSumProperty());
        paymentTextField.textProperty().bindBidirectional(viewModel.paymentProperty());
        overpaymentTextField.textProperty().bindBidirectional(viewModel.overpaymentProperty());

        //Clear button handler
        btnClear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.clear();
            }
        });

        //Calculate button handler
        btnCalc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.calculate();
            }
        });
    }
}
