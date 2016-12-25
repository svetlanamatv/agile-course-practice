package ru.unn.agile.triangle.view;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ru.unn.agile.triangle.viewmodel.LoggerRecordViewModel;
import ru.unn.agile.triangle.viewmodel.ViewModel;

public class Triangle {
    @FXML
    private ListView logList;
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

        ReadOnlyListProperty<LoggerRecordViewModel> records =
                viewModel.getLoggerViewModel().recordsProperty();
        records.addListener(new ListChangeListener<LoggerRecordViewModel>() {
            @Override
            public void onChanged(Change<? extends LoggerRecordViewModel> change) {
                logList.scrollTo(logList.getItems().size() - 1);
            }
        });
    }
}
