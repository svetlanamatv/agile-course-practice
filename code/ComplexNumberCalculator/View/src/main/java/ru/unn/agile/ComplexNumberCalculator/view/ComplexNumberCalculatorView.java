package ru.unn.agile.ComplexNumberCalculator.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ru.unn.agile.ComplexNumberCalculator.viewmodel.ComplexNumberCalculatorViewModel;
import ru.unn.agile.ComplexNumberCalculator.viewmodel.Operations;
import ru.unn.agile.ComplexNumberCalculator.infrastructure.TxtLogger;

/**
 * Created by Alexander on 28.11.2016.
 */
public class ComplexNumberCalculatorView {
    @FXML
    private ComplexNumberCalculatorViewModel viewModel;
    @FXML
    private TextField firstArgReal;
    @FXML
    private TextField firstArgIm;
    @FXML
    private TextField secondArgReal;
    @FXML
    private TextField secondArgIm;
    @FXML
    private ComboBox<Operations> cbOperations;

    @FXML
    private Button computeBtn;

    @FXML
    void initialize() {
        viewModel.setLogger(new TxtLogger("./TxtLogger-lab3.log"));

        final ChangeListener<Boolean> focusChangeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable,
                                final Boolean oldValue, final Boolean newValue) {
                viewModel.onFocusChanged(oldValue, newValue);
            }
        };

        // Two-way binding hasn't supported by FXML yet, so place it in code-behind
        firstArgReal.textProperty().bindBidirectional(viewModel.firstArgRealProperty());
        firstArgReal.focusedProperty().addListener(focusChangeListener);

        firstArgIm.textProperty().bindBidirectional(viewModel.firstArgImProperty());
        firstArgIm.focusedProperty().addListener(focusChangeListener);

        secondArgReal.textProperty().bindBidirectional(viewModel.secondArgRealProperty());
        secondArgReal.focusedProperty().addListener(focusChangeListener);

        secondArgIm.textProperty().bindBidirectional(viewModel.secondArgImProperty());
        secondArgIm.focusedProperty().addListener(focusChangeListener);

        cbOperations.valueProperty().bindBidirectional(viewModel.selectedOperationProperty());
        cbOperations.valueProperty().addListener(new ChangeListener<Operations>() {
            @Override
            public void changed(final ObservableValue<? extends Operations> observable,
                                final Operations oldValue,
                                final Operations newValue) {
                viewModel.onOperationChanged(oldValue, newValue);
            }
        });

        computeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.calculate();
            }
        });
    }
}
