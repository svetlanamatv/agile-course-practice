package ru.unn.agile.PositionalNotation.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.unn.agile.PositioanalNotation.infrastructure.PositionalNotationLogger;
import ru.unn.agile.PositionalNotation.viewmodel.Notation;
import ru.unn.agile.PositionalNotation.viewmodel.ViewModel;

public class Converter {
    @FXML
    private Label status;
    @FXML
    private TextArea taLog;
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField number;
    @FXML
    private TextField result;
    @FXML
    private ComboBox<Notation> fromNotation;
    @FXML
    private ComboBox<Notation> toNotation;
    @FXML
    private Button btnConv;

    @FXML
    void initialize() {

        viewModel = new ViewModel(new PositionalNotationLogger("./PositionalNotationLog.log"));
        final ChangeListener<Boolean> focusListenerChange = new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable,
                                final Boolean oldValue, final Boolean newValue) {
                viewModel.onFocusChanged(oldValue, newValue);
            }
        };

        number.focusedProperty().addListener(focusListenerChange);

        // Two-way binding hasn't supported by FXML yet, so place it in code-behind
        number.textProperty().bindBidirectional(viewModel.numberProperty());
        result.textProperty().bindBidirectional(viewModel.resultProperty());

        fromNotation.valueProperty().bindBidirectional(viewModel.fromNotationProperty());
        toNotation.valueProperty().bindBidirectional(viewModel.toNotationProperty());

        fromNotation.valueProperty().addListener(new ChangeListener<Notation>() {
            @Override
            public void changed(final ObservableValue<? extends Notation> observable,
                                final Notation oldValue, final Notation newValue) {
                viewModel.fromNotationChanged(oldValue, newValue);
            }
        });

        toNotation.valueProperty().addListener(new ChangeListener<Notation>() {
            @Override
            public void changed(final ObservableValue<? extends Notation> observable,
                                final Notation oldValue, final Notation newValue) {
                viewModel.toNotationChanged(oldValue, newValue);
            }
        });

        taLog.textProperty().bindBidirectional(viewModel.logsProperty());

        btnConv.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.convert();
            }
        });

        btnConv.disableProperty().bindBidirectional(viewModel.converterDisabledProperty());
        status.textProperty().bindBidirectional(viewModel.statusProperty());
    }
}
